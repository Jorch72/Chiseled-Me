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

import necauqua.mods.cm.item.ItemRecalibrator;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

import static necauqua.mods.cm.item.ItemRecalibrator.RecalibrationEffect.AMPLIFICATION;
import static necauqua.mods.cm.item.ItemRecalibrator.RecalibrationEffect.REDUCTION;
import static net.minecraft.init.Blocks.*;
import static net.minecraft.init.Items.*;
import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPED;
import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPELESS;

public final class Recipes {

    private Recipes() {}

    public static void init() {
        OreDictionary.registerOre("netherStar", ChiseledMe.BLUE_STAR);
        GameRegistry.addShapelessRecipe(new ItemStack(ChiseledMe.BLUE_STAR), NETHER_STAR, LAPIS_BLOCK);

        GameRegistry.addRecipe(new BlueStarDecraftRecipe());
        RecipeSorter.register("chiseled_me:blue_star_decraft", BlueStarDecraftRecipe.class, SHAPELESS, "after:minecraft:shapeless");

        GameRegistry.addRecipe(new OverridenBeaconRecipe());
        RecipeSorter.register("chiseled_me:overriden_beacon", OverridenBeaconRecipe.class, SHAPED, "after:minecraft:shaped before:forge:shapedore");

        GameRegistry.addRecipe(new PymContainerRecipe());
        RecipeSorter.register("chiseled_me:container", PymContainerRecipe.class, SHAPED, "after:minecraft:shaped before:minecraft:shapeless");

        GameRegistry.addShapelessRecipe(
            new ItemStack(ChiseledMe.PYM_ESSENSE),
            ChiseledMe.PYM_CONTAINER,
            REDSTONE_BLOCK, REDSTONE_BLOCK, REDSTONE_BLOCK, REDSTONE_BLOCK,
            REDSTONE_BLOCK, REDSTONE_BLOCK, REDSTONE_BLOCK, REDSTONE_BLOCK
        );
        for(int i = 1; i <= 8; i++) {
            Object[] params = new Object[i + 1];
            params[0] = DRAGON_BREATH;
            for(int j = 1; j <= i; j++) {
                params[j] = ChiseledMe.PYM_ESSENSE;
            }
            GameRegistry.addShapelessRecipe(ItemRecalibrator.create(REDUCTION, (byte) i), params);
        }
        if(Config.enableSupersmalls || Config.enableBigSizes) {
            GameRegistry.addShapedRecipe(
                new ItemStack(ChiseledMe.PYM_CONTAINER_X),
                    "xyx",
                    "yzy",
                    "xyx",
                'x', IRON_BLOCK, 'y', DIAMOND_BLOCK, 'z', ChiseledMe.PYM_CONTAINER // this is so original i cant even..
            );
            GameRegistry.addShapelessRecipe(new ItemStack(ChiseledMe.PYM_ESSENSE_X), ChiseledMe.PYM_CONTAINER_X, NETHER_STAR, REDSTONE_BLOCK);
        }
        if(Config.enableSupersmalls) {
            for(int i = 1; i <= 4; i++) {
                Object[] params = new Object[i + 1];
                params[0] = DRAGON_BREATH;
                for(int j = 1; j <= i; j++) {
                    params[j] = ChiseledMe.PYM_ESSENSE_X;
                }
                GameRegistry.addShapelessRecipe(ItemRecalibrator.create(REDUCTION, (byte) (i + 8)), params);
            }
        }
        if(Config.enableBigSizes) {
            GameRegistry.addRecipe(new BlueEssenseRecipe());
            RecipeSorter.register("chiseled_me:blue_essense", BlueEssenseRecipe.class, SHAPELESS, "after:minecraft:shapeless");
            for(int i = 1; i <= 4; i++) {
                Object[] params = new Object[i + 1];
                params[0] = DRAGON_BREATH;
                for(int j = 1; j <= i; j++) {
                    params[j] = ChiseledMe.PYM_ESSENSE_B;
                }
                GameRegistry.addShapelessRecipe(ItemRecalibrator.create(AMPLIFICATION, (byte) i), params);
            }
        }
    }

    private static class OverridenBeaconRecipe extends ShapedRecipes {

        public OverridenBeaconRecipe() {
            super(3, 3, new ItemStack[] {
                new ItemStack(GLASS), new ItemStack(GLASS), new ItemStack(GLASS),
                new ItemStack(GLASS), new ItemStack(ChiseledMe.BLUE_STAR), new ItemStack(GLASS),
                new ItemStack(OBSIDIAN), new ItemStack(OBSIDIAN), new ItemStack(OBSIDIAN)
            }, createBlueBeacon());
        }

        @Override
        @Nonnull
        public ItemStack[] getRemainingItems(InventoryCrafting inv) {
            ForgeHooks.getCraftingPlayer().addStat(Achievements.WEIRD_BEACON); // meh
            return super.getRemainingItems(inv);
        }

        private static ItemStack createBlueBeacon() {
            ItemStack beacon = new ItemStack(BEACON);
            NBTTagCompound nbt = new NBTTagCompound();
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("chiseled_me:color", (byte) 3);
            nbt.setTag("BlockEntityTag", tag);
            beacon.setTagCompound(nbt);
            return beacon;
        }
    }

    private static class PymContainerRecipe extends ShapedRecipes { // wait why recipes do not work with nbt??

        private static PotionType awkward = PotionType.getPotionTypeForName("minecraft:awkward");

        private PymContainerRecipe() {
            super(3, 3, new ItemStack[]{
                new ItemStack(IRON_INGOT), new ItemStack(DIAMOND), new ItemStack(IRON_INGOT),
                new ItemStack(DIAMOND), new ItemStack(POTIONITEM), new ItemStack(DIAMOND),
                new ItemStack(IRON_INGOT), new ItemStack(DIAMOND), new ItemStack(IRON_INGOT)
            }, new ItemStack(ChiseledMe.PYM_CONTAINER));
        }

        @Override
        public boolean matches(@Nonnull InventoryCrafting inv, World world) {
            return super.matches(inv, world) && PotionUtils.getPotionFromItem(inv.getStackInSlot(4)) == awkward;
        }
    }

    private static class BlueEssenseRecipe extends ShapelessRecipes {

        private BlueEssenseRecipe() {
            super(new ItemStack(ChiseledMe.PYM_ESSENSE_B), Arrays.asList(new ItemStack(ChiseledMe.PYM_ESSENSE_X), new ItemStack(ChiseledMe.BLUE_STAR)));
        }

        @Override
        @Nonnull
        public ItemStack[] getRemainingItems(InventoryCrafting inv) {
            ItemStack[] remaining = super.getRemainingItems(inv);
            for(int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if(stack != null && stack.getItem() == ChiseledMe.BLUE_STAR) {
                    remaining[i] = new ItemStack(NETHER_STAR);
                }
            }
            return remaining;
        }
    }

    private static class BlueStarDecraftRecipe implements IRecipe {

        @Override
        public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world) {
            boolean once = false;
            for(int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if(stack != null) {
                    if(stack.getItem() == ChiseledMe.BLUE_STAR) {
                        once = true;
                    }else {
                        return false;
                    }
                }
            }
            return once;
        }

        @Nullable
        @Override
        public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
            int c = 0;
            for(int i = 0; i < inv.getSizeInventory(); i++) {
                if(inv.getStackInSlot(i) != null) {
                    ++c;
                }
            }
            return new ItemStack(LAPIS_BLOCK, c);
        }

        @Override
        public int getRecipeSize() {
            return 9;
        }

        @Nullable
        @Override
        public ItemStack getRecipeOutput() {
            return new ItemStack(LAPIS_BLOCK);
        }

        @Override
        @Nonnull
        public ItemStack[] getRemainingItems(@Nonnull InventoryCrafting inv) {
            ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
            for(int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if(stack != null && stack.getItem() == ChiseledMe.BLUE_STAR) {
                    ret[i] = new ItemStack(NETHER_STAR);
                }
            }
            ForgeHooks.getCraftingPlayer().addStat(Achievements.SURPRISE); // meh x2
            return ret;
        }
    }
}