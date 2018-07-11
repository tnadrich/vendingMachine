/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author capta
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao{
    
    Item item1;
    Item item2;
    List<Item> itemList = new ArrayList<>();
    
    public VendingMachineDaoStubImpl(){
        item1 = new Item("1A");
        item1.setName("Candy1");
        item1.setCost(new BigDecimal(20));
        item1.setInventory(10);
        
        itemList.add(item1);
        
        item2 = new Item("1B");
        item2.setName("Candy2");
        item2.setCost(new BigDecimal(50));
        item2.setInventory(0);
        
        itemList.add(item2);
        
    }

    @Override
    public Item addItem(String key,
            Item item) throws VendingMachinePersistenceException {
        return null;
    }

    @Override
    public Item editItem(String key,
            Item item) throws VendingMachinePersistenceException {
        return null;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return itemList;
    }

    @Override
    public Item getItem(String key) throws VendingMachinePersistenceException {
        if (key.equals(item1.getKey())) {
            return item1;
        } else if (key.equals(item2.getKey())){
            return item2;
        } else {
            return null;
        }
    }

    @Override
    public Item removeItem(String key) throws VendingMachinePersistenceException {
        return null;
    }
    
}
