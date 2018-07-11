/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author capta
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayItemList(List<Item> itemList) {
        for (Item currentItem : itemList) {
            io.print("\nItem Key: " + currentItem.getKey() + " | " + "Name: "
                    + currentItem.getName() + " | " + "Price: $"
                    + currentItem.getCost() + " | " + "Quantity Remaining: "
                    + currentItem.getInventory());
        }
    }

    public String getItemSelection() {
        io.print("");
        return io.readString("Please select an item key you would like to "
                + "purchase or enter 0 for refund/exit.");
    }

    public String displayEntryError() {
        return io.readString("Invalid choice. Please select an item key you "
                + "would like to purchase or enter 0 for refund/exit.");
    }

    public void displayListBanner() {
        io.print("\n======= Tim's Vending Machine =======");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("\n=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayRefundMessage(String errorMsg) {
        io.print("\n===REFUND===");
        io.print(errorMsg);
    }

    public BigDecimal displayMoneyPromptAndGetMoney() {
        double userMoney = io.readDouble("\nPlease insert bills or coins. Accepts "
                + "$1, $5 bills and coins (Pennies, Nickels, Dimes, and Quarters)"
                + "or enter 0 for refund/exit.");
        BigDecimal userMoneyBD = new BigDecimal(userMoney);
        return userMoneyBD;
    }

    public void displayNoChange() {
        io.print("\nYou paid the exact amount. No Change");
        io.print("Enjoy!");
    }

    public void displayChangeListStartBanner() {
        io.print("\n======= Here is your change: =======");
    }

    public void displayReceivedItem(String itemName) {
        io.print("\nHere is your " + itemName + ".");
    }

    public void displayChangeListEndBanner() {
        io.print("Thank you! Come again!");
        io.print("                       .-.\n"
                + "                      |_:_|\n"
                + "                     /(_Y_)\\\n"
                + ".                   ( \\/M\\/ )\n"
                + " '.               _.'-/'-'\\-'._\n"
                + "   ':           _/.--'[[[[]'--.\\_\n"
                + "     ':        /_'  : |::\"| :  '.\\\n"
                + "       ':     //   ./ |oUU| \\.'  :\\\n"
                + "         ':  _:'..' \\_|___|_/ :   :|\n"
                + "           ':.  .'  |_[___]_|  :.':\\\n"
                + "            [::\\ |  :  | |  :   ; : \\\n"
                + "             '-'   \\/'.| |.' \\  .;.' |\n"
                + "             |\\_    \\  '-'   :       |\n"
                + "             |  \\    \\ .:    :   |   |\n"
                + "             |   \\    | '.   :    \\  |\n"
                + "             /       \\   :. .;       |\n"
                + "            /     |   |  :__/     :  \\\\\n"
                + "           |  |   |    \\:   | \\   |   ||\n"
                + "          /    \\  : :  |:   /  |__|   /|\n"
                + "      snd |     : : :_/_|  /'._\\  '--|_\\\n"
                + "          /___.-/_|-'   \\  \\\n"
                + "                         '-'");
    }

    public void displayChangeGiven(Change change) {

        io.print("Quarters: " + change.getQuarters());
        io.print("Dimes: " + change.getDimes());
        io.print("Nickels: " + change.getNickels());
        io.print("Pennies: " + change.getPennies());
    }

    public void displayRefund(BigDecimal userMoneyBD) {
        io.print("\nHere is your refund.");
    }

    public void displayNegativeNumberError() {
        io.print("\nThis is not an ATM. You cannot enter a negative number -_-");
    }

}
