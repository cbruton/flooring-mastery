/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.breakout19.flooringmastery.service;

/**
 *
 * @author sarah
 */
public class DateValidationException extends Exception {

    public DateValidationException(String message) {
        super(message);
    }

    public DateValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
