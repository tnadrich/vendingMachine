/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author capta
 */
public interface VendingMachineDao {

    Item addItem(String key,
            Item item) throws VendingMachinePersistenceException;

    Item editItem(String key, Item item) throws VendingMachinePersistenceException;

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item getItem(String key) throws VendingMachinePersistenceException;

    Item removeItem(String key) throws VendingMachinePersistenceException;

}
