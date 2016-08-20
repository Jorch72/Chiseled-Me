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

import necauqua.mods.cm.asm.CalledFromASM;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** This class holds methods that are called from ASM'ed minecraft code **/

@CalledFromASM
public final class Hooks {

    private Hooks() {}

    @CalledFromASM
    public static float getSize(Entity entity) {
        return EntitySizeManager.getData(entity).getSize();
    }

    @CalledFromASM
    @SideOnly(Side.CLIENT)
    public static float getRenderSize(Entity entity, float partialTick) {
        return EntitySizeManager.getData(entity).getRenderSize(partialTick);
    }

    @CalledFromASM
    @SideOnly(Side.CLIENT)
    public static float getScreenInterpEyeHeight(Entity entity, float partialTick) {
        return entity.getEyeHeight() / getSize(entity) * getRenderSize(entity, partialTick); // assuming getEyeHeight is patched already
    }

    @CalledFromASM
    public static void updateSize(Entity entity) {
        EntitySizeManager.getData(entity).tick();
    }

    @CalledFromASM
    public static float getLabelHeight(Entity entity, float old) {
        float off = entity.isSneaking() ? 0.25F : 0.5F;
        return (old - off) / getSize(entity) + off;
    }

    @CalledFromASM
    public static boolean cancelRunningParticlesHook(Entity entity) {
        return getSize(entity) <= 0.25F; // 1/4
    }

    @CalledFromASM
    public static boolean cancelBlockCollision(Entity entity, IBlockState state, BlockPos pos) { // this makes nether portal more fun
        return Config.changePortalAABB && state.getBlock() == Blocks.PORTAL && getSize(entity) < 1.0F && !entity.getEntityBoundingBox().intersectsWith(state.getSelectedBoundingBox(entity.worldObj, pos));
    }
}
