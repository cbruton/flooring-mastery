
package com.breakout19.flooringmastery.service;

/**
 *
 * @author breakout19
 */
public class InvalidProductException extends Exception {
    public InvalidProductException(String message) {
        super(message);
    }
    
    public InvalidProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
