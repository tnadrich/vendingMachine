/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

/**
 *
 * @author capta
 */
public interface VendingMachineLoggerDao {

    public void writeLoggerEntry(String entry)
            throws VendingMachinePersistenceException;

}
