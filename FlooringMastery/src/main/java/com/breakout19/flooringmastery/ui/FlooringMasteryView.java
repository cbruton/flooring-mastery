/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.breakout19.flooringmastery.ui;

import com.breakout19.flooringmastery.dto.Order;
import com.breakout19.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cole,kevin,sarah
 */
public class FlooringMasteryView {

    private UserIO io;
    
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Please select from the above choices.", 1, 6);
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
    // ---------------------Display orders-------------------------
    public void displayOrdersBanner() {
        io.print("=== Display Orders ===");
    }
    
    public void displayOrder(List<Order> orderList) {
        io.print("OrderNumber,CustomerName,State,TaxRate,ProductType,Area"
                + ",CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        
        for (Order currentOrder : orderList) {
            String orderInfo = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                    currentOrder.getOrderNumber(),
                    currentOrder.getCustomerName(),
                    currentOrder.getTaxRate().toString(),
                    currentOrder.getState(),
                    currentOrder.getTaxRate(),
                    currentOrder.getProductType(),
                    currentOrder.getArea(),
                    currentOrder.getCostPerSquareFoot(),
                    currentOrder.getLaborCostPerSquareFoot(),
                    currentOrder.getMaterialCost(),
                    currentOrder.getLaborCost(),
                    currentOrder.getTax(),
                    currentOrder.getTotal());

            io.print(orderInfo);
        }
        io.readString("Please hit enter to continue.");
    }
    
    
    //-----------------------Add an Order------------------------
    public void displayCreateOrderBanner() {
        io.print("=== Add Order ===");
     }
    
    public Order createOrder(List<Product> productList) {
        String customerName = io.readString("What is your full name? ");
        String stateAbb = io.readString("What is the state abbreviation? ");
        displayProductsBanner(productList); 
        String productType = io.readString("What product type would you like? ");
        BigDecimal area = new BigDecimal(io.readString("What is the area to be covered? "));
        Order currentOrder = new Order();
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(stateAbb);
        currentOrder.setProductType(productType);
        currentOrder.setArea(area);
        
        return currentOrder;
    }
    
    public String askPlaceOrder(){
        return io.readString("Would you like to place this order? Y/N");
    }
    
    public void displayAddOrderSuccess(boolean success, Order newOrder) {
        if (success == true) {
            io.print("Order as successfully added!");
        } else {
            io.print("Order was not saved.");
            io.readString("Please hit enter to continue.");
        }
    }
    
    //--------------------Edit an order-------------------------
     public void displayEditOrderBanner(){
        io.print("=== Edit Order ===");
    }
     
    public int getOrderNumberEditOrder(){
        return io.readInt("Please enter an order number. ");
    }
    
    public String getCustomerNameEditOrder(){
        return io.readString("Please enter a name. ");
    }
    
    public String getStateAbbEditOrder(){
        return io.readString("Please enter a state abbreviation. ");
    }
    
    public String getProductTypeEditOrder(){
        return io.readString("Please enter a product type. ");
    }
    
    public String getAreaEditOrder(){
        return io.readString("Please enter an area. ( > 100)");
    }
    
     //-------------------remove an order-------------------------
    public void displayRemoveOrderBanner() {
        io.print("=== Remove Order ===");
    }
    public String removeConfirmation() {
        return io.readString("Are you sure you want to remove this order? (Y/N)");
    }
    public String orderRemovedConfirmation() {
        return io.readString("=== Order Removed ===\n"
                + "Please hit enter to continue.");
    }
    
    //---------------------export all data------------------------------
    public void displayExportAllDataBanner(){
        io.print("=== Export All Data ===");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }  
    
     public void displayUnknownCommandBanner() {
        io.print("Unknown command!");
    }
    
     public String displayGetDate() {
         return io.readString("Please enter a date: [MMDDYYYY]");
     }
     
     public void displayProductsBanner(List<Product> productList) {
         io.print("The following are the flooring products that we offer.\n");
         
         io.print("Product Type | Cost Per SqFt | Labor Cost Per SqFt |");
        
        for (Product item : productList) {
            String productInfo = String.format("%12s |%14s |%20s |",
                   item.getProductType(),
                   item.getCostPerSquareFoot().toString(),
                   item.getLaborCostPerSquareFoot().toString());
                    
            io.print(productInfo);
        }
        io.print("");
     }
}
