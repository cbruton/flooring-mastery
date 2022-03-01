
package com.breakout19.flooringmastery.service;

/**
 *
 * @author breakout19
 */
public class InvalidStateException extends Exception {
    public InvalidStateException(String message) {
        super(message);
    }
    
    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
