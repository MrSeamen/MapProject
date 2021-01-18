package edu.cwru.srw89.map;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Class: Logger

    author: Simon Wang
    date: December 1, 2020

    Routines to log messages
 */

class LogClass {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Logs the messages for any errors or bad data.
     * @param message Message of log
     */
    public void log(String message) {
        LOGGER.log(Level.INFO, message);
    }
}
