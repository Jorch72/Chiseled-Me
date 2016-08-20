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

package necauqua.mods.cm.item;

import necauqua.mods.cm.ChiseledMe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMod extends Item {

    protected final String name;

    private final ResourceLocation defaultModel;

    public ItemMod(String name) {
        this.name = name;
        setRegistryName("chiseled_me", name);
        setUnlocalizedName("chiseled_me:" + name);
        setCreativeTab(ChiseledMe.TAB);
        defaultModel = new ResourceLocation("chiseled_me", name);
    }

    public void init() {
        GameRegistry.register(this);
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            clientRegister();
        }
    }

    @SideOnly(Side.CLIENT)
    protected ResourceLocation getModelResource(ItemStack stack) {
        return defaultModel;
    }

    protected String[] getModelVariants() {
        return new String[0];
    }

    @SideOnly(Side.CLIENT)
    private void clientRegister() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, s -> new ModelResourceLocation(getModelResource(s), "inventory"));
        String[] vs = getModelVariants();
        ResourceLocation[] variants = new ResourceLocation[vs.length + 1];
        variants[0] = defaultModel;
        for(int i = 0; i < vs.length; i++) {
            variants[i + 1] = new ResourceLocation("chiseled_me", vs[i]);
        }
        ModelBakery.registerItemVariants(this, variants);
    }
}