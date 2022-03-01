
package com.breakout19.flooringmastery.service;

import com.breakout19.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.breakout19.flooringmastery.dto.Order;
import com.breakout19.flooringmastery.dto.Product;
import com.breakout19.flooringmastery.dto.Tax;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cole,kevin,sarah
 */
public interface FlooringMasteryServiceLayer {
    
    public List<Order> checkDateGetOrder(String orderdate) throws FlooringMasteryPersistenceException;
    public Tax getTax(String stateAbbInput) throws FlooringMasteryPersistenceException, InvalidStateException;
    public void checkProductType (String productInput) throws FlooringMasteryPersistenceException, InvalidProductException;
    public void validateCustomerName(String customerNameInput) throws FlooringMasteryPersistenceException,InvalidCustomerNameException;
    public void validateAreaMinimum (BigDecimal areaInput) throws FlooringMasteryPersistenceException, validateAreaMinException;
    public void validateDateInput (String orderDate) throws FlooringMasteryPersistenceException, DateValidationException;
    Order removeOrder(int orderNumber, String orderDate) throws FlooringMasteryPersistenceException, NoOrdersExistException;
    void checkIfOrderExists(List<Order> orderList, int orderNumber) throws FlooringMasteryPersistenceException, NoOrdersExistException;
    int getNewOrderNumber(String orderDate) throws FlooringMasteryPersistenceException;
    
    Product getProduct(String productType) throws FlooringMasteryPersistenceException;
    
    BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) throws FlooringMasteryPersistenceException;
    BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot) throws FlooringMasteryPersistenceException;
    BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) throws FlooringMasteryPersistenceException;
    BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax) throws FlooringMasteryPersistenceException;
    
    Order addOrder(Order newOrder, String orderDate) throws FlooringMasteryPersistenceException,
            InvalidCustomerNameException,
            InvalidStateException,
            DateValidationException,
            validateAreaMinException;
    
    Order getOrder(int orderNumber, String orderDate) throws FlooringMasteryPersistenceException;
    
    void exportData(String orderDate) throws FlooringMasteryPersistenceException;
    public List<Product> getProducts () throws FlooringMasteryPersistenceException;
    
    void initOrderFile(String orderDate) throws IOException;
    
    List<Order> getAllOrdersForDate(String orderDate) throws FlooringMasteryPersistenceException;
    
    public String checkEditName(Order orderToEdit, String newCustomerName) throws FlooringMasteryPersistenceException;
    public String checkEditState(Order orderToEdit, String newStateAbb) throws FlooringMasteryPersistenceException;
    public String checkEditProduct(Order orderToEdit, String newProductType) throws FlooringMasteryPersistenceException;
    public BigDecimal checkEditArea(Order orderToEdit, String newAreaAsString) throws FlooringMasteryPersistenceException;
    public Order editOrder(Order newOrder, String orderDate) throws FlooringMasteryPersistenceException;
}
