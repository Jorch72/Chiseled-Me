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

import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.io.IOException;

public final class Config {

    private Config() {}

    public static boolean changeBedAABB;
    public static boolean changePortalAABB;
    public static boolean enableSupersmalls;
    public static boolean enableBigSizes;

    private static void load(Configuration c) {
        changeBedAABB = c.getBoolean("bedBBox", "misc", true,
            "Override vanilla bed bounding box so if you're small enough you can walk under it");
        changePortalAABB = c.getBoolean("Override vanilla portal bounding box", "misc", true,
            "By default you are starting to use portal if you collide with blockspace it takes. This option fixes that so if you're small you could walk on obsidian but not in portal");
        enableSupersmalls = c.getBoolean("enableSupersmalls", "main", true,
            "At these sizes (most noticeable at 1/4096) Minecraft starts to break a little so beware of various (mostly visual, mob AI and colliding) glitches");
        enableBigSizes = c.getBoolean("enableBigs", "main", true,
            "Big sizes are OP and bugged even more then small.");
    }

    public static void init(File configFolder) {
        try {
            File file = new File(configFolder, "chiseled_me.cfg");
            file.createNewFile();
            Configuration c = new Configuration(file);
            c.load();
            load(c);
            c.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
