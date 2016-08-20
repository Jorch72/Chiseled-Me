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

import necauqua.mods.cm.Achievements;
import necauqua.mods.cm.ChiseledMe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CraftItem extends ItemMod {

    private int boundAchievementId = -1;
    private boolean isGlowing = false;

    public CraftItem(String name) {
        super(name);
        setCreativeTab(ChiseledMe.TAB);
    }

    // this method uses int id because circular static class loading results in
    // Achievements class loading before ChiseledMe class and thus
    // items (and so their icons in achievements) are null
    public CraftItem bindAchievement(int id) {
        boundAchievementId = id;
        return this;
    }

    public CraftItem setGlowing() {
        isGlowing = true;
        return this;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if(boundAchievementId != -1) {
            player.addStat(Achievements.get(boundAchievementId));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isGlowing || super.hasEffect(stack);
    }
}