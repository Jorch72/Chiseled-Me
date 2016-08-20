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

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

public final class RandomUtils {

    private RandomUtils() {}

    @CallerSensitive
    @SuppressWarnings("deprecation")
    public static <T> void forEachStaticField(Class<T> type, Consumer<T> action) {
        forEachStaticField(Reflection.getCallerClass(2), type, action);
    }

    @SuppressWarnings("unchecked")
    public static <T> void forEachStaticField(Class<?> holder, Class<T> type, Consumer<T> action) {
        try {
            for(Field field : holder.getFields()) {
                if((field.getModifiers() & Modifier.STATIC) != 0) {
                    Object obj = field.get(null);
                    if(type.isAssignableFrom(obj.getClass())) {
                        action.accept((T) obj);
                    }
                }
            }
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
