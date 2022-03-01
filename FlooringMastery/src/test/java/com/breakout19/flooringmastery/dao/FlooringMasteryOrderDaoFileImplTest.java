package com.breakout19.flooringmastery.dao;

import com.breakout19.flooringmastery.dto.Order;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author cole
 */
public class FlooringMasteryOrderDaoFileImplTest {
    
    FlooringMasteryOrderDao testOrderDao;
    
    public FlooringMasteryOrderDaoFileImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        String testFile = "Orders_02022026.txt";
        new FileWriter(testFile);
        testOrderDao = new FlooringMasteryOrderDaoFileImpl(testFile);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetOrder() throws Exception {
        int orderNumber = 1;
        String orderDate = "02022026";
        Order order = new Order(orderNumber) {};
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00"));
        
        testOrderDao.addOrder(order, orderDate);
        
        Order retrievedOrder = testOrderDao.getOrder(orderNumber, orderDate);
        
        assertEquals(order.getOrderNumber(), retrievedOrder.getOrderNumber(), "Checking order number.");
        assertEquals(order.getCustomerName(), retrievedOrder.getCustomerName(), "Checking customer name.");
        assertEquals(order.getState(), retrievedOrder.getState(), "Checking state.");
        assertEquals(order.getProductType(), retrievedOrder.getProductType(), "Checking product type.");
        assertEquals(order.getArea(), retrievedOrder.getArea(), "Checking area.");
        
    }
    
    @Test
    public void testAddGetAllOrder() throws Exception {
        int orderNumber = 1;
        String orderDate = "02022026";
        Order order = new Order(orderNumber) {};
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00"));
        
        int orderNumber2 = 2;
        Order order2 = new Order(orderNumber2) {};
        order2.setCustomerName("Markus");
        order2.setState("TX");
        order2.setProductType("Carpet");
        order2.setArea(new BigDecimal("125"));
        
        testOrderDao.addOrder(order, orderDate);
        testOrderDao.addOrder(order2, orderDate);
        
        List<Order> allOrders = testOrderDao.getAllOrders(orderDate);
        
        assertNotNull(allOrders, "The list of students must not be null");
        assertEquals(2, allOrders.size(),"List of students should have 2 orders.");
        
        assertTrue(testOrderDao.getAllOrders(orderDate).contains(order),"The list should include Ada.");
        assertTrue(testOrderDao.getAllOrders(orderDate).contains(order2),"The list should include Markus.");
        
    }

    @Test
    public void testRemoveOrder() throws Exception {
        int orderNumber = 1;
        String orderDate = "02022026";
        Order order = new Order(orderNumber) {};
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00"));
        
        int orderNumber2 = 2;
        Order order2 = new Order(orderNumber2) {};
        order2.setCustomerName("Markus");
        order2.setState("TX");
        order2.setProductType("Carpet");
        order2.setArea(new BigDecimal("125"));
        
        testOrderDao.addOrder(order, orderDate);
        testOrderDao.addOrder(order2, orderDate);
        
        Order removedOrder = testOrderDao.removeOrder(orderNumber, orderDate);
        
        assertEquals(removedOrder.getCustomerName(), order.getCustomerName(), "The removed order should be order Ada.");
        
        List<Order> allOrders = testOrderDao.getAllOrders(orderDate);
        assertNotNull(allOrders, "The list of students must not be null");
         assertEquals(1, allOrders.size(),"List of students should have  order.");
    }
}
