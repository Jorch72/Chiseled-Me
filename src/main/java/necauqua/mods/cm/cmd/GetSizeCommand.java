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

package necauqua.mods.cm.cmd;

import necauqua.mods.cm.EntitySizeManager;
import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class GetSizeCommand extends CommandBase {

    @Override
    @Nonnull
    public String getCommandName() {
        return "getsizeof";
    }

    @Override
    @Nonnull
    public String getCommandUsage(@Nonnull ICommandSender sender) {
        return "commands.chiseled_me:getsizeof.usage";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if(args.length == 0) {
            throw new WrongUsageException(getCommandUsage(sender));
        }
        Entity entity = getEntity(server, sender, args[0]);
        sender.addChatMessage(new TextComponentTranslation("commands.chiseled_me:getsizeof.message", entity.getDisplayName(), EntitySizeManager.getSize(entity)));
    }

    @Nonnull
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }
}
