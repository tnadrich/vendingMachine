/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDaoStubImpl;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.sg.vendingmachine.dto.Change;
import java.util.List;
import junit.framework.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author capta
 */
public class VendingMachineServiceLayerTest {

    private VendingMachineServiceLayer testService;
    private VendingMachineDaoStubImpl daoStub;

    public VendingMachineServiceLayerTest() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testService = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
        daoStub = ctx.getBean("vendingMachineDaoStub", VendingMachineDaoStubImpl.class);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllItems method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetAllItems() throws Exception {
        List<Item> itemList = testService.getAllItems();
        Assert.assertEquals("ItemList should be the exact same "
                + "list from the dao", itemList, daoStub.getAllItems());
        assertEquals(2, testService.getAllItems().size());
    }

    /**
     * Test of getItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetItem() throws Exception {
        Item item = testService.getItem("1A");
        assertNotNull(item);
        item = testService.getItem("9999");
        assertNull(item);
    }

    /**
     * Test of getItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testValidateUserChoiceInvalidSelection() throws Exception {
        boolean correctExceptionThrown = false;
        try {
            // There is no Item with key of 1Z, should throw exception
            testService.validateUserChoice("1Z");
            Assert.fail("Did not throw expected exception");
        } catch (VendingMachineInvalidSelectionException e) {
            correctExceptionThrown = true;
        }

    }

    @Test
    public void testValidateUserChoiceInventory() throws Exception {
        boolean correctExceptionThrown = false;
        try {
            // Item 2 has zero items left in inventory, should throw exception
            testService.validateUserChoice("1B");
            Assert.fail("Did not throw expected exception");
        } catch (VendingMachineNoItemInventoryException e) {
            correctExceptionThrown = true;
        }
    }

    /**
     * Test of validateMoney method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testValidateMoneyMoreMoneyThanItemCost() throws Exception {
        Item item1 = new Item("1A");
        item1.setName("Candy1");
        item1.setCost(new BigDecimal(.50));
        item1.setInventory(0);

        BigDecimal userMoney1 = new BigDecimal(.51);
        Change testChange1 = testService.validateMoney(userMoney1, item1);

        assertEquals(1, testChange1.getPennies());
        assertEquals(0, testChange1.getNickels());
        assertEquals(0, testChange1.getDimes());
        assertEquals(0, testChange1.getQuarters());
        assertEquals(9, item1.getInventory());

    }

    @Test
    public void testValidateMoneyLessMoneyThanItemCost() throws Exception {
        Item item1 = new Item("1A");
        item1.setName("Candy1");
        item1.setCost(new BigDecimal(.50));
        item1.setInventory(0);

        BigDecimal userMoney1 = new BigDecimal(.49);

        boolean correctExceptionThrown = false;
        try {
            // Item1 cost more than userMoney, should throw exception
            testService.validateMoney(userMoney1, item1);
            Assert.fail("Did not throw expected exception");
        } catch (VendingMachineInsufficientFundsException e) {
            correctExceptionThrown = true;
        }

    }

    @Test
    public void testValidateMoneyEqualMoneyToItemCost() throws Exception {
        Item item1 = new Item("1A");
        item1.setName("Candy1");
        item1.setCost(new BigDecimal(.50));
        item1.setInventory(0);

        BigDecimal userMoney1 = new BigDecimal(.50);

        Assert.assertNull("Should return null since there is no "
                + "change",testService.validateMoney(userMoney1, item1));
    }
    
    @Test
    public void testValidateRefundMoney() throws Exception {
        BigDecimal userMoney1 = new BigDecimal(.50);
        
        Change refundChange = testService.validateRefundMoney(userMoney1);
        
        assertEquals(0, refundChange.getPennies());
        assertEquals(0, refundChange.getNickels());
        assertEquals(0, refundChange.getDimes());
        assertEquals(2, refundChange.getQuarters());
    }

}
