/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author capta
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    private int inventory;
    private BigDecimal totalUserMoney = new BigDecimal(0);

    public VendingMachineServiceLayerImpl(VendingMachineDao dao,
            VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;

    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public Item getItem(String key) throws VendingMachinePersistenceException {
        return dao.getItem(key);

    }

    @Override
    public void validateUserChoice(String key) throws
            VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException,
            VendingMachineInvalidSelectionException {
        if (dao.getItem(key) == null) {

            throw new VendingMachineInvalidSelectionException ("Invalid Selection."
                    + " Please choose a valid option.");
        }
        if (dao.getItem(key).getInventory() <= 0) {

            throw new VendingMachineNoItemInventoryException("Sold out."
                    + " Please Select another item to purchase.");
        }

    }

    @Override
    public Change validateMoney(BigDecimal userMoneyBD,
            Item item) throws VendingMachinePersistenceException,
            VendingMachineInsufficientFundsException,
            VendingMachineExitException {
        if (userMoneyBD.compareTo(BigDecimal.ZERO) == 0) {
            Change refundChange = makeChange(totalUserMoney);
            throw new VendingMachineExitException("Here is your refund: "
                    + "\nQuarters: " + refundChange.getQuarters()
                    + "\nDimes: " + refundChange.getDimes()
                    + "\nNickels: " + refundChange.getNickels()
                    + "\nPennies: " + refundChange.getPennies());
        }
        Change change = new Change();
        BigDecimal userMoneyBDS = userMoneyBD.setScale(2, RoundingMode.FLOOR);
        BigDecimal itemCostBD = item.getCost();
        BigDecimal itemCostBDS = itemCostBD.setScale(2, RoundingMode.HALF_UP);

        totalUserMoney = totalUserMoney.add(userMoneyBDS);

        int result = totalUserMoney.compareTo(itemCostBDS);

        if (result == -1) {
            throw new VendingMachineInsufficientFundsException("Insufficient"
                    + " funds. You entered $ " + totalUserMoney
                    + ". Please insert more money.");
        } else if (result == 0) {
            change = null;
        }
        if (result == 1) {
            totalUserMoney = totalUserMoney.subtract(itemCostBDS);
            change = makeChange(totalUserMoney);
        }

        inventory(item);

        return change;
    }

    @Override
    public Change validateRefundMoney(BigDecimal userMoneyBD) {
        Change refundChange = makeChange(userMoneyBD);
        return refundChange;
    }

    private Change makeChange(BigDecimal totalUserMoney) {
        Change change = new Change();
        BigDecimal userChange = totalUserMoney.movePointRight(2);
        int userIntChange = userChange.intValueExact();

        int quarterValue = 25;
        int dimeValue = 10;
        int nickelValue = 5;
        int pennyValue = 1;

        int totalQuarters = (userIntChange / quarterValue);
        userIntChange %= quarterValue;
        change.setQuarters(totalQuarters);
        int totalDimes = (userIntChange / dimeValue);
        userIntChange %= dimeValue;
        change.setDimes(totalDimes);
        int totalNickels = (userIntChange / nickelValue);
        userIntChange %= nickelValue;
        change.setNickels(totalNickels);
        int totalPennies = (userIntChange / pennyValue);
        userIntChange %= pennyValue;
        change.setPennies(totalPennies);

        return change;

    }

    private void inventory(Item item) throws VendingMachinePersistenceException {
        int updatedInventory;
        inventory = (dao.getItem(item.getKey()).getInventory());
        updatedInventory = inventory - 1;
        item.setInventory(updatedInventory);
        dao.editItem(item.getKey(), item);
    }

}
