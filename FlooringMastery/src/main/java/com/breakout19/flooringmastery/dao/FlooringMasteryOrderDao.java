
package com.breakout19.flooringmastery.dao;

import com.breakout19.flooringmastery.dto.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cole,kevin,sarah
 */
public interface FlooringMasteryOrderDao {
    
    Order getOrder(int orderNumber, String orderDate) throws FlooringMasteryPersistenceException;
    List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException ;
    Order addOrder(Order newOrder, String orderDate) throws FlooringMasteryPersistenceException ;
    Order removeOrder(int orderNumber, String orderDate) throws FlooringMasteryPersistenceException;
//    Order editOrder(int orderNumber) throws FlooringMasteryPersistenceException;
    void exportData(String orderDate) throws FlooringMasteryPersistenceException;
    BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) throws FlooringMasteryPersistenceException;
    BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot) throws FlooringMasteryPersistenceException;
    BigDecimal calculateTax (BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) throws FlooringMasteryPersistenceException;
    BigDecimal calculateTotal (BigDecimal materialCost,BigDecimal laborCost, BigDecimal tax) throws FlooringMasteryPersistenceException;
    int getNewOrderNumber(String orderDate) throws FlooringMasteryPersistenceException;
    void initOrderFile(String orderDate) throws IOException;
    
    List<Order> getAllOrdersForDate(String orderDate) throws FlooringMasteryPersistenceException;
}
