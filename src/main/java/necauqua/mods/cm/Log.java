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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Log {

    private Log() {}

    private static Logger logger = LogManager.getLogger("Chiseled Me");

    public static void trace(Object msg) {
        logger.trace(msg);
    }

    public static void debug(Object msg) {
        logger.debug(msg);
    }

    public static void info(Object msg) {
        logger.info(msg);
    }

    public static void warn(Object msg) {
        logger.warn(msg);
    }

    public static void warn(Object msg, Throwable cause) {
        logger.warn(msg, cause);
    }

    public static void error(Object msg, Throwable cause) {
        logger.error(msg, cause);
    }

    public static void fatal(Object msg, Throwable cause) {
        logger.fatal(msg, cause);
    }
}
