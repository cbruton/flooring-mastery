
package com.breakout19.flooringmastery.dao;

import com.breakout19.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cole,kevin,sarah
 */
public class FlooringMasteryOrderDaoFileImpl implements FlooringMasteryOrderDao  {
    
    private static final String HEADER = "OrderNumber,CustomerName,State,TaxRate,"
            + "ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
            + "MaterialCost,LaborCost,Tax,Total";
    private String ORDER_FILE;
    private static final String DELIMITER = ",";
    
    Map<Integer, Order> orders = new HashMap<>();
    
    public FlooringMasteryOrderDaoFileImpl() {
    
    }
    
    public FlooringMasteryOrderDaoFileImpl(String orderTextFile) {
        ORDER_FILE = orderTextFile;
    }

    @Override
    public Order addOrder(Order newOrder, String orderDate) throws FlooringMasteryPersistenceException {
        //loadOrder(orderDate);
        orders.put(newOrder.getOrderNumber(), newOrder);
        return newOrder;
    }
    
    @Override
    public Order getOrder(int orderNumber, String orderDate) throws FlooringMasteryPersistenceException {
        loadOrder(orderDate);
        return orders.get(orderNumber);
    }

    @Override
    public List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException {
        //ORDER_FILE =  "Orders/Orders_" + orderDate + ".txt";
        //loadOrder(orderDate);
        return new ArrayList<Order>(orders.values()); 
    }
    
    @Override
    public List<Order> getAllOrdersForDate(String orderDate) throws FlooringMasteryPersistenceException {
        ORDER_FILE =  "Orders/Orders_" + orderDate + ".txt";
        loadOrder(orderDate);
        return new ArrayList<Order>(orders.values()); 
    }

    @Override
    public Order removeOrder(int orderNumber, String orderDate) throws FlooringMasteryPersistenceException {
         loadOrder(orderDate);
         Order removedOrder = orders.remove(orderNumber);
         return removedOrder;
    }
    
    @Override
    public int getNewOrderNumber(String orderDate) throws FlooringMasteryPersistenceException {
        loadOrder(orderDate);
        List<Order> temp = new ArrayList<>(orders.values());
        
        //if orderfile is empty
        if(temp.size() == 0) {
            return 1;
        }
        Collections.sort(temp, new CustomComparator());
        int index = temp.size() - 1;
        return temp.get(index).getOrderNumber() + 1;
    }

    @Override
    public void exportData(String orderDate) throws FlooringMasteryPersistenceException {
        writeOrder(orderDate);
    }
    
    private class CustomComparator implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            Integer temp1 = Integer.valueOf(o1.getOrderNumber());
            Integer temp2 = Integer.valueOf(o2.getOrderNumber());
            return temp1.compareTo(temp2);
        }
    }
    
    @Override
    public void initOrderFile(String orderDate) throws IOException {
        ORDER_FILE = "Orders/Orders_" + orderDate + ".txt";
        File file = new File(ORDER_FILE);
        file.createNewFile();

    }
    
    
    private Order unmarshallOrder(String orderAsText) {
        
        String[] orderTokens = orderAsText.split(DELIMITER);
        
        int orderNumber = Integer.parseInt(orderTokens[0]);
        
        Order orderFromFile = new Order(orderNumber);
        
        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setState(orderTokens[2]);
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
        orderFromFile.setProductType(orderTokens[4]);
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
        orderFromFile.setTax(new BigDecimal(orderTokens[10]));
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));
        
        return orderFromFile;
    }
    
    private String marshallOrder(Order anOrder){
        
        String orderAsText = anOrder.getOrderNumber() + DELIMITER;
        
        orderAsText += anOrder.getCustomerName() + DELIMITER;
        orderAsText += anOrder.getState() + DELIMITER;
        orderAsText += anOrder.getTaxRate() + DELIMITER;
        orderAsText += anOrder.getProductType() + DELIMITER;
        orderAsText += anOrder.getArea() + DELIMITER;
        orderAsText += anOrder.getCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getMaterialCost() + DELIMITER;
        orderAsText += anOrder.getLaborCost() + DELIMITER;
        orderAsText += anOrder.getTax() + DELIMITER;
        orderAsText += anOrder.getTotal();
        
        return orderAsText;
    }
    
    private void loadOrder(String orderDate) throws FlooringMasteryPersistenceException {
        
        ORDER_FILE = "Orders/Orders_" + orderDate + ".txt";
        
        orders.clear();
        
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ORDER_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException (
                    "Could not load order data into memory.", e);
        }
        
        String currentLine;
        Order currentOrder;
        currentLine = scanner.nextLine(); //skips header
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();
    }
    
    private void writeOrder(String date) throws FlooringMasteryPersistenceException {
        
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(ORDER_FILE));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save order data.", e);
        }
        
        String orderAsText;
        
        List<Order> orderList = this.getAllOrders(date);
        out.println(HEADER);
        //lambda
        orderList.stream()
                .forEach((order) ->  {
                    out.println(marshallOrder(order));
                    out.flush();
                        });
        out.close();
    }

   
    //------------------calculations for MaterialCost, LaborCost, Tax and total-------------------------------

    @Override
    public BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) throws FlooringMasteryPersistenceException {
        return area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP); //MaterialCost = (Area * CostPerSquareFoot)
    }

    @Override
    public BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot) throws FlooringMasteryPersistenceException {
        return area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP); //LaborCost = (Area * LaborCostPerSquareFoot)
    }

    @Override
    public BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) throws FlooringMasteryPersistenceException {
        return (materialCost.add(laborCost)).multiply(taxRate.divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP); //Tax = (MaterialCost + LaborCost) * (TaxRate/100)
    }

    @Override
    public BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax) throws FlooringMasteryPersistenceException {
        return materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP); // Total = (MaterialCost + LaborCost + Tax)
    }

  
}
