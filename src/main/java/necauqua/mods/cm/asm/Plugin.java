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

package necauqua.mods.cm.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

import java.util.Map;

@Name("Chiseled Me ASM")
@MCVersion("1.10.2")
@SortingIndex(1001) // above 1000 so srg deobfuscation would happen EARLIER >.< (so at least descs are the same)
@TransformerExclusions("necauqua.mods.cm.asm") // ^ i feel that Lex would just annihilate me for that :D
public class Plugin implements IFMLLoadingPlugin, IClassTransformer {

    @Override
    public void injectData(Map<String, Object> data) {
        ASM.init(new Transformers(), (Boolean) data.get("runtimeDeobfuscationEnabled"));
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        return ASM.doTransform(transformedName, bytes);
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "necauqua.mods.cm.asm.Plugin" };
    }

    @Override
    public String getModContainerClass() { return null; }

    @Override
    public String getSetupClass() { return null; }

    @Override
    public String getAccessTransformerClass() { return null; }
}
