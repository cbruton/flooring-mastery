
package com.breakout19.flooringmastery.service;

/**
 *
 * @author breakout19
 */
public class NoOrdersExistException extends Exception {
    public NoOrdersExistException(String message) {
        super(message);
    }
    
    public NoOrdersExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
