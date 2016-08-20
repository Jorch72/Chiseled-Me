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

package necauqua.mods.cm.api;

import net.minecraft.entity.Entity;

// oh looks like licence also restricts the necauqua.mods.cm.api package too - so learn how to use soft deps, it's a useful knowledge :)

/**
  * Example use as soft dependency (if you bundle this API with your mod then you're doing it WRONG as Searge once said):
  *
  <pre><code>
    public float getSize(Entity entity) {
        if(Loader.isModLoaded("chiseled_me")) {
            return getSize_opt(entity);
        }
        return 1.0F;
    }

   {@literal @}Optional.Method(modid = "chiseled_me")
    private float getSize_opt(Entity entity) {
        return ChiseledMeAPI.interaction.getSizeOf(entity);
    }
  </code></pre>
  *
  * Put this code somewhere where you use it.
  **/

public class ChiseledMeAPI {

    /**
      * As in any other API, nobody set this to something!
      * If Chiseled Me is in you mod list, after pre-initialization stage
      * this will be populated with proper implementation to use.
      *
      * Also this field is a mod instance.
      **/
    public static ChiseledMeInterface interaction = new ChiseledMeInterface() { // this is a stub (for those doing it the wrong way)

        @Override
        public float getSizeOf(Entity entity) {
            return 1.0F;
        }

        @Override
        public void setSizeOf(Entity entity, float size, boolean interp) {
            // NOOP
        }
    };
}