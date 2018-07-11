/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

/**
 *
 * @author capta
 */
public class VendingMachineExitException extends Exception {

    public VendingMachineExitException(String message) {
        super(message);
    }

    public VendingMachineExitException(String message,
            Throwable cause) {
        super(message, cause);
    }

}
