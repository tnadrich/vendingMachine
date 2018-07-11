/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author capta
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    public static final String INVENTORY_FILE = "inventory.txt";
    public static final String DELIMITER = "::";

    private Map<String, Item> items = new LinkedHashMap<>();

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadInventory();
        return new ArrayList<Item>(items.values());

    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        loadInventory();
        return items.get(name);
    }

    @Override
    public Item addItem(String key,
            Item item) throws VendingMachinePersistenceException {
        Item newItem = items.put(key, item);
        writeInventory();
        return newItem;
    }

    @Override
    public Item editItem(String key,
            Item item) throws VendingMachinePersistenceException {
        Item editedItem = items.replace(key, item);
        writeInventory();
        return editedItem;
    }

    @Override
    public Item removeItem(String key) throws VendingMachinePersistenceException {
        Item removedItem = items.remove(key);
        writeInventory();
        return removedItem;
    }

    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }
        String currentLine;
        String[] currentTokens;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Item currentItem = new Item(currentTokens[0]);
            currentItem.setName(currentTokens[1]);
            currentItem.setCost(new BigDecimal(currentTokens[2]));
            currentItem.setInventory(Integer.parseInt(currentTokens[3]));
            items.put(currentItem.getKey(), currentItem);
        }
        scanner.close();
    }

    private void writeInventory() throws VendingMachinePersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save DVD data.", e);
        }

        List<Item> itemList = this.getAllItems();
//        for (Item currentItem : itemList) {
//
//            out.println(currentItem.getKey() + DELIMITER
//                    + currentItem.getName() + DELIMITER
//                    + currentItem.getCost() + DELIMITER
//                    + currentItem.getInventory());
        itemList.stream().forEach(s -> out.println(s.getKey() + DELIMITER
                + s.getName() + DELIMITER + s.getCost() + DELIMITER
                + s.getInventory()));

        out.flush();

        out.close();
    }

}
