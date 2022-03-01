/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.breakout19.flooringmastery.service;

import com.breakout19.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.breakout19.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author iseek
 */
public class FlooringMasteryServiceLayerImplTest {
    
    private FlooringMasteryServiceLayer service;
  
    public FlooringMasteryServiceLayerImplTest() {
        ApplicationContext ctx = 
            new ClassPathXmlApplicationContext("applicationContext.xml");
        service = 
            ctx.getBean("serviceLayer", FlooringMasteryServiceLayer.class);
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

//    @Test
//    public void testCreateValidOrder() {
//        //ARRANGE
//        int orderNumber = 1;
//        String orderDate = "02022026";
//        Order order = new Order(orderNumber) {};
//        order.setCustomerName("Ada Lovelace");
//        order.setState("CA");
//        order.setProductType("Tile");
//        order.setArea(new BigDecimal("249.00"));
//        //ACT
//        try {
//            service.addOrder(order, orderDate);
//        } catch (FlooringMasteryPersistenceException e) {
//        //ASSERT
//            fail("Order was valid. No exception should have been thrown.");
//        }
//        
//    }
    @Test
    public void testValidateCustomerName() {
        String name = "";
        
        try {
            service.validateCustomerName(name);
            fail("InvalidCustomerNameException not thrown.");
        } catch (FlooringMasteryPersistenceException e) {
            fail("InvalidCustomerNameException not thrown.");
        } catch (InvalidCustomerNameException e) {
            return;
        }
    }
    
    @Test
    public void testValidateAreaMinimum() {
        BigDecimal area = new BigDecimal("99");
        
        try {
            service.validateAreaMinimum(area);
            fail("validateAreaMinException not thrown.");
        } catch (FlooringMasteryPersistenceException e) {
            fail("validateAreaMinException not thrown.");
        } catch (validateAreaMinException e) {
            return;
        }
    }
    
    @Test
    public void testCheckIfOrderExists() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order(2);
        orderList.add(order);
        
        try {
            service.checkIfOrderExists(orderList, 1);
            fail("NoOrdersExistException not thrown.");
        } catch (FlooringMasteryPersistenceException e) {
            fail("NoOrdersExistException not thrown.");
        } catch (NoOrdersExistException e) {
            return;
        }
    }
    
    @Test
    public void testValidateDateInput() {
        String date = "10102021";

        try {
            service.validateDateInput(date);
            fail("NoOrdersExistException not thrown.");
        } catch (FlooringMasteryPersistenceException e) {
            fail("NoOrdersExistException not thrown.");
        } catch (DateValidationException e) {
            return;
        }
    }
    
}
