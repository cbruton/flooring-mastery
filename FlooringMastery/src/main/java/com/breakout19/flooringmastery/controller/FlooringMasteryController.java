/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.breakout19.flooringmastery.controller;
import com.breakout19.flooringmastery.service.InvalidProductException;
import com.breakout19.flooringmastery.service.validateAreaMinException;
import com.breakout19.flooringmastery.service.InvalidCustomerNameException;
import com.breakout19.flooringmastery.service.InvalidProductException;
import com.breakout19.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.breakout19.flooringmastery.dto.Order;
import com.breakout19.flooringmastery.dto.Product;
import com.breakout19.flooringmastery.dto.Tax;
import com.breakout19.flooringmastery.service.DateValidationException;
import com.breakout19.flooringmastery.service.FlooringMasteryServiceLayer;
import com.breakout19.flooringmastery.service.InvalidStateException;
import com.breakout19.flooringmastery.service.NoOrdersExistException;
import com.breakout19.flooringmastery.ui.FlooringMasteryView;
import com.breakout19.flooringmastery.ui.UserIO;
import com.breakout19.flooringmastery.ui.UserIOConsoleImpl;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cole,sarah,kevin
 */
public class FlooringMasteryController {
    
    private UserIO io = new UserIOConsoleImpl();
    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    public void run() throws NoOrdersExistException, DateValidationException, InvalidStateException, IOException, validateAreaMinException, InvalidProductException, InvalidCustomerNameException {
        boolean keepGoing = true;
        int menuSelection = 0;
                try {
                    while (keepGoing) {
                        menuSelection = getMenuSelection();

                    switch (menuSelection) {
                        case 1:
                            displayOrders();
                            break;
                        case 2:
                            createOrder();
                            break;
                        case 3:
                            editOrder();
                            break;
                        case 4:
                            removeOrder();
                            break;
                        case 5:
                            exportData();
                            break;
                        case 6:
                            keepGoing = false;
                            break;
                        default:
                            unknownCommand();
                    }
                }
                exitMessage();
            } catch (FlooringMasteryPersistenceException  
                | NoOrdersExistException 
                | DateValidationException 
                | InvalidStateException 
                | IOException 
                | validateAreaMinException 
                | InvalidProductException 
                | InvalidCustomerNameException e) {
                view.displayErrorMessage(e.getMessage());
        }
    }
    private int getMenuSelection(){
        return view.printMenuAndGetSelection();
    }
    
    private void displayOrders() throws FlooringMasteryPersistenceException {
        String date = view.displayGetDate();
        List<Order> orderList = service.getAllOrdersForDate(date);
        view.displayOrdersBanner();
        view.displayOrder(orderList);
        
    }
    
    private void createOrder() throws FlooringMasteryPersistenceException, DateValidationException, InvalidStateException, IOException, validateAreaMinException, InvalidProductException, InvalidCustomerNameException{
        view.displayCreateOrderBanner();
        String date = view.displayGetDate();
        service.initOrderFile(date); //if file doesnt exist
        service.validateDateInput(date); //check if future date
        List<Product> productList = service.getProducts(); //get a list of products.
//        view.displayProductsBanner(productList);  //Display the list of products offered.
        Order newOrder = view.createOrder(productList);
        service.validateCustomerName(newOrder.getCustomerName());
        newOrder.setOrderNumber(service.getNewOrderNumber(date));
        
        Tax tax = service.getTax(newOrder.getState()); //get tax
        newOrder.setTaxRate(tax.getTaxRate());
        
        Product product = service.getProduct(newOrder.getProductType()); //get product type
        service.checkProductType(newOrder.getProductType());
        newOrder.setCostPerSquareFoot(product.getCostPerSquareFoot()); 
        newOrder.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        
        newOrder.setMaterialCost(service.calculateMaterialCost(newOrder.getArea(), newOrder.getCostPerSquareFoot())); // (Area * CostPerSquareFoot)
        service.validateAreaMinimum(newOrder.getArea());
        newOrder.setLaborCost(service.calculateLaborCost(newOrder.getArea(), newOrder.getLaborCostPerSquareFoot())); // (Area * LaborCostPerSquareFoot)
        newOrder.setTax(service.calculateTax(newOrder.getMaterialCost(), newOrder.getLaborCost(), newOrder.getTaxRate())); //  (MaterialCost + LaborCost) * (TaxRate/100) 
        newOrder.setTotal(service.calculateTotal(newOrder.getMaterialCost(), newOrder.getLaborCost(), newOrder.getTax())); // (MaterialCost + LaborCost + Tax)
        
        
        String userResponse = view.askPlaceOrder();
        if (userResponse.equalsIgnoreCase("Y")) {
                service.addOrder(newOrder, date);
                view.displayAddOrderSuccess(true, newOrder);
            } else if (userResponse.equalsIgnoreCase("N")) {
                view.displayAddOrderSuccess(false, newOrder);
            } else {
                unknownCommand();
            }
    }
    
    
    private void editOrder() throws FlooringMasteryPersistenceException, NoOrdersExistException, InvalidStateException, validateAreaMinException{
        view.displayEditOrderBanner();
        String date = view.displayGetDate();
        List<Order> orderList = service.checkDateGetOrder(date);
        
        int orderNumber = view.getOrderNumberEditOrder();
        service.checkIfOrderExists(orderList, orderNumber);
        Order orderToEdit = service.getOrder(orderNumber, date);
        String newCustomerName = view.getCustomerNameEditOrder();
        orderToEdit.setCustomerName(service.checkEditName(orderToEdit, newCustomerName));
        
        // Moved to service layer
//        if (newCustomerName != null) {
//            orderToEdit.setCustomerName(newCustomerName);
//        }
        
        String newStateAbb = view.getStateAbbEditOrder();
        orderToEdit.setState(service.checkEditState(orderToEdit, newStateAbb));
        
        // Moved to service layer
//        if (newStateAbb != null) {
//            orderToEdit.setState(newStateAbb);
//        }
        List<Product> productList = service.getProducts();
        view.displayProductsBanner(productList);
        String newProductType = view.getProductTypeEditOrder();
        orderToEdit.setProductType(service.checkEditProduct(orderToEdit, newProductType));
        
        // Moved to service layer
//        if (newProductType != null) {
//            orderToEdit.setProductType(newProductType);
//        }
        
        String newAreaAsString = view.getAreaEditOrder();
        orderToEdit.setArea(service.checkEditArea(orderToEdit, newAreaAsString));
        
        // Moved to service layer
//        if (newAreaAsString != null) {
//            orderToEdit.setArea(new BigDecimal(newAreaAsString));
//        }
        
        Tax tax = service.getTax(orderToEdit.getState()); //get tax
        orderToEdit.setTaxRate(tax.getTaxRate());
        
        Product product = service.getProduct(orderToEdit.getProductType()); //get product type
        orderToEdit.setCostPerSquareFoot(product.getCostPerSquareFoot()); 
        orderToEdit.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        
        orderToEdit.setMaterialCost(service.calculateMaterialCost(orderToEdit.getArea(), orderToEdit.getCostPerSquareFoot())); // (Area * CostPerSquareFoot)
        orderToEdit.setLaborCost(service.calculateLaborCost(orderToEdit.getArea(), orderToEdit.getLaborCostPerSquareFoot())); // (Area * LaborCostPerSquareFoot)
        orderToEdit.setTax(service.calculateTax(orderToEdit.getMaterialCost(), orderToEdit.getLaborCost(), orderToEdit.getTaxRate())); //  (MaterialCost + LaborCost) * (TaxRate/100) 
        orderToEdit.setTotal(service.calculateTotal(orderToEdit.getMaterialCost(), orderToEdit.getLaborCost(), orderToEdit.getTax())); // (MaterialCost + LaborCost + Tax)
        
        service.editOrder(orderToEdit, date);
    }
    
    private void removeOrder() throws FlooringMasteryPersistenceException, NoOrdersExistException{
        view.displayRemoveOrderBanner();
        String date = view.displayGetDate();
        List<Order> orderList = service.checkDateGetOrder(date);
        
        int orderNumber = view.getOrderNumberEditOrder();
        service.checkIfOrderExists(orderList, orderNumber);
        String confirm = view.removeConfirmation();
        if(confirm.equalsIgnoreCase("Y")) {
            service.removeOrder(orderNumber, date);
            view.orderRemovedConfirmation();
        }
    }
    
    private void exportData() throws FlooringMasteryPersistenceException{
        view.displayExportAllDataBanner();
        String date = view.displayGetDate();
        service.exportData(date);
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    
}
