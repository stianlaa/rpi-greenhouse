package com.rpigreenhouse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor( access = AccessLevel.PRIVATE)
public class GreenhouseLogger {

    private static final Logger logger = LoggerFactory.getLogger(GreenhouseLogger.class);

    public static void debugLog(String message) {
        logger.debug(message);
    }

    public static void infoLog(String message) {
        logger.info(message);
    }

    public static void warnLog(String message) {
        logger.warn(message);
    }

    public static void errorLog(String message) {
        logger.error(message);
    }
}