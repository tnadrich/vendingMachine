/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author capta
 */
public class VendingMachineDaoTest {

    private VendingMachineDao dao = new VendingMachineDaoFileImpl();
    
    public VendingMachineDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        List<Item>itemList = dao.getAllItems();
        for (Item item : itemList)
            dao.removeItem(item.getKey());
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addItem method, of class VendingMachineDao.
     */
    @Test
    public void testAddGetItem() throws Exception {
        Item item1 = new Item("1A");
        item1.setName("Candy1");
        item1.setCost(new BigDecimal(5));
        item1.setInventory(10);
        
        dao.addItem(item1.getKey(), item1);
        
        Item fromDao = dao.getItem(item1.getKey());
        Assert.assertNotNull("Get item1 should not return null", fromDao);
        Assert.assertEquals("Item1 should match what is returned. ",item1, fromDao);
        
    }

    /**
     * Test of editItem method, of class VendingMachineDao.
     */
    @Test
    public void testEditItem() throws Exception {
        Item item1 = new Item("1A");
        item1.setName("Candy1");
        item1.setCost(new BigDecimal("5"));
        item1.setInventory(10);
        
        dao.addItem(item1.getKey(), item1);
        
        Item item2 = new Item("1A");
        item2.setName("Candy2");
        item2.setCost(new BigDecimal("10"));
        item2.setInventory(20);
        
        dao.editItem(item1.getKey(), item2);
        
        Item fromDao = dao.getItem(item2.getKey());
        
        Assert.assertEquals("getAllItems should return 1 since the "
                + "item was edited and another was not "
                + "added.",1, dao.getAllItems().size());
        Assert.assertEquals("Item2 should match what is returned "
                + "since Item1 was edited to be replaced by item2.",item2, fromDao);
        Assert.assertEquals("Candy2 should be the new name of the item returned "
                + "","Candy2", dao.getItem(item2.getKey()).getName());
        Assert.assertNotNull("There should be 1 item , not null.",fromDao);

    }

    /**
     * Test of getAllItems method, of class VendingMachineDao.
     */
    @Test
    public void testGetAllItems() throws Exception {
        Item item1 = new Item("1A");
        item1.setName("Candy1");
        BigDecimal cost = new BigDecimal("5");
        item1.setCost(cost);
        item1.setInventory(10);
        
        dao.addItem(item1.getKey(), item1);
        
        Item item2 = new Item("1B");
        item2.setName("Candy2");
        BigDecimal cost2 = new BigDecimal("10");
        item2.setCost(cost2);
        item2.setInventory(20);
        
        dao.addItem(item2.getKey(), item2);
        
        Assert.assertEquals("There should be 2 items.",2, dao.getAllItems().size());
        Assert.assertTrue("Should contain item1.",dao.getAllItems().contains(item1));
        Assert.assertTrue("Should contain item2",dao.getAllItems().contains(item2));
        Assert.assertNotNull("Should not be null. 2 items",dao.getAllItems());
        Assert.assertFalse("Should not be empty.",dao.getAllItems().isEmpty());
    }

    /**
     * Test of removeItem method, of class VendingMachineDao.
     */
    @Test
    public void testRemoveItem() throws Exception {
        Item item1 = new Item("1A");
        item1.setName("Candy1");
        BigDecimal cost = new BigDecimal("5");
        item1.setCost(cost);
        item1.setInventory(10);
        
        dao.addItem(item1.getKey(), item1);
        
        Item item2 = new Item("1B");
        item2.setName("Candy2");
        BigDecimal cost2 = new BigDecimal("10");
        item2.setCost(cost2);
        item2.setInventory(20);
        
        dao.addItem(item2.getKey(), item2);
        
        dao.removeItem(item1.getKey());
        Assert.assertEquals("Should only be 1 item since 1 was "
                + "removed.",1, dao.getAllItems().size());
        Assert.assertNull("",dao.getItem(item1.getKey()));
        
        dao.removeItem(item2.getKey());
        Assert.assertEquals("Should be zero items since both "
                + "were removed.",0, dao.getAllItems().size());
        Assert.assertNull("When trying to get an item that is not there, "
                + "should return null.",dao.getItem(item2.getKey()));
        
    }

}
