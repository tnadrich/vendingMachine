/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.VendingMachineExitException;
import com.sg.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sg.vendingmachine.service.VendingMachineInvalidSelectionException;
import com.sg.vendingmachine.service.VendingMachineNoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author capta
 */
public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineServiceLayer service,
            VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        try {
            listAvailableItems();
            vendItems();
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void listAvailableItems() throws VendingMachinePersistenceException {
        List<Item> itemList = service.getAllItems();
        view.displayListBanner();
        view.displayItemList(itemList);
    }

    private BigDecimal getUserMoney() {
        BigDecimal userMoneyBD = view.displayMoneyPromptAndGetMoney();
        return userMoneyBD;
    }

    private void vendItems() throws VendingMachinePersistenceException {
        boolean hasErrors = false;
        BigDecimal userMoneyBD = getUserMoney();
        if (userMoneyBD.compareTo(BigDecimal.ZERO) < 0) {
            view.displayNegativeNumberError();
            return;
        }
        if (userMoneyBD.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        String key;
        do {
            key = view.getItemSelection();
            if (key.equals("0") && userMoneyBD != null) {
                Change refundChange = service.validateRefundMoney(userMoneyBD);
                view.displayRefund(userMoneyBD);
                view.displayChangeGiven(refundChange);
            }
            if (key.equals("0")) {
                return;
            }
            try {
                service.validateUserChoice(key);
                hasErrors = false;
            } catch (VendingMachineNoItemInventoryException | VendingMachineInvalidSelectionException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }

        } while (hasErrors);
        Item selectedItem = service.getItem(key);
        validateUserMoney(userMoneyBD, selectedItem);
    }

    private void validateUserMoney(BigDecimal userMoneyBD,
            Item item) throws VendingMachinePersistenceException {
        boolean hasErrors = false;
        do {
            try {
                Change change = service.validateMoney(userMoneyBD, item);
                if (change == null) {
                    view.displayReceivedItem(item.getName());
                    view.displayNoChange();
                } else {
                    view.displayReceivedItem(item.getName());
                    view.displayChangeListStartBanner();
                    view.displayChangeGiven(change);
                    view.displayChangeListEndBanner();
                }

                hasErrors = false;
            } catch (VendingMachineInsufficientFundsException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
                userMoneyBD = getUserMoney();
            } catch (VendingMachineExitException e) {
                view.displayRefundMessage(e.getMessage());
                return;
            }
        } while (hasErrors);

    }
}
