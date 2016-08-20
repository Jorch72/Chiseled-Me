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

public interface ChiseledMeInterface {

    /**
      * Returns an entity size that was set before with {@linkplain #setSizeOf} or 1.<br>
      * Returned float is relative to original size of the entity (meaning that default size is 1 and eg 1/16th is 0.0625).<br>
      *
      * Note that this also returns intermediate tick sizes if entity's size is changing right now
      * (interp param in {@linkplain #setSizeOf}).
      *
      * @param entity any minecraft entity which size might have been changed before
      * @return relative size of given entity
      **/
    float getSizeOf(Entity entity);

    /**
      * Sets the size of an entity to a given float.<br>
      * While its completely ok to set size at any number in given boundaries
      * it is recommended to stick with negative and positive powers of two
      * (not nessesary, but it determines smooth change speed using log2).<br>
      *
      * Lower limit exists because of float presicion, because if you go smaller minecraft
      * logic starts to freak out much more then warned in congigs.
      *
      * @param entity modified minecraft entity
      * @param size size to be set in between 0.000244140625 (1/4092 or 1/16/16/16) and 16
      * @param interp will size change be smooth or not
      **/
    void setSizeOf(Entity entity, float size, boolean interp);
}