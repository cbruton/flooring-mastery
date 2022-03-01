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
public class InvalidCustomerNameException extends Exception {

    public InvalidCustomerNameException(String message) {
        super(message);
    }

    public InvalidCustomerNameException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
