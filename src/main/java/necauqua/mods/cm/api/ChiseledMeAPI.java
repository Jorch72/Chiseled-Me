/*
 * Copyright (c) 2016 Anton Bulakh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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