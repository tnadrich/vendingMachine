/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author capta
 */
public interface VendingMachineServiceLayer {

    public List<Item> getAllItems() throws VendingMachinePersistenceException;

    public Item getItem(String key) throws VendingMachinePersistenceException;

    public void validateUserChoice(String key) throws VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException,
            VendingMachineInvalidSelectionException;
    
    public Change validateRefundMoney(BigDecimal userMoneyBD);

    public Change validateMoney(BigDecimal userMoney,
            Item item)
            throws VendingMachinePersistenceException,
            VendingMachineInsufficientFundsException,
            VendingMachineExitException;
}
