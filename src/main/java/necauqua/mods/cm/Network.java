/*
 * This mod adds a possibility for one to become much smaller then they
 * are, which is useful for example when dealing with mods such as
 * Chisel & Bits and so on.
 * Copyright (C) 2016 necauqua
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This mod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this mod.  If not, see <http://www.gnu.org/licenses/>.
 */

package necauqua.mods.cm;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Consumer;

public final class Network {

    private Network() {}

    private static FMLEventChannel chan;

    public static void init() {
        chan = NetworkRegistry.INSTANCE.newEventDrivenChannel("chiseled_me");
        chan.register(new Network());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientReceive(ClientCustomPacketEvent e) {
        PacketBuffer payload = new PacketBuffer(e.getPacket().payload());
        switch(payload.readByte()) {
            case 0: {
                World clientWorld = Minecraft.getMinecraft().theWorld;
                if(clientWorld != null) {
                    int id = payload.readInt();
                    Entity entity = clientWorld.getEntityByID(id);
                    if(entity != null) {
                        EntitySizeManager.setSize(entity, payload.readFloat(), payload.readBoolean());
                    }else {
                        Log.warn("Client entity with id " + id + " is null! This mean you're desynced somewhere =/");
                    }
                }else {
                    Log.warn("Somehow client world does not yet exist, this should never happen!");
                }
                break;
            }
            case 1: {
                EntitySizeManager.enqueueSetSize(payload.readInt(), payload.readFloat());
                break;
            }
        }
    }

    private static FMLProxyPacket packet(int id, Consumer<PacketBuffer> data) {
        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());
        payload.writeByte(id);
        data.accept(payload);
        return new FMLProxyPacket(payload, "chiseled_me");
    }

    public static void sendSetSizeToClients(Entity entity, float size, boolean interp) {
        chan.sendToDimension(packet(0, p -> {
            p.writeInt(entity.getEntityId());
            p.writeFloat(size);
            p.writeBoolean(interp);
        }), entity.dimension);
    }

    public static void sendEnqueueSetSizeToClient(EntityPlayerMP client, Entity entity, float size) {
        chan.sendTo(packet(1, p -> {
            p.writeInt(entity.getEntityId());
            p.writeFloat(size);
        }), client);
    }
}