
package com.breakout19.flooringmastery.service;
import com.breakout19.flooringmastery.dao.FlooringMasteryAuditDao;
import com.breakout19.flooringmastery.dao.FlooringMasteryAuditDaoFileImpl;
import com.breakout19.flooringmastery.dao.FlooringMasteryOrderDao;
import com.breakout19.flooringmastery.dao.FlooringMasteryOrderDaoFileImpl;
import com.breakout19.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.breakout19.flooringmastery.dao.FlooringMasteryProductDao;
import com.breakout19.flooringmastery.dao.FlooringMasteryProductDaoFileImpl;
import com.breakout19.flooringmastery.dao.FlooringMasteryTaxDao;
import com.breakout19.flooringmastery.dao.FlooringMasteryTaxDaoFileImpl;
import com.breakout19.flooringmastery.dto.Order;
import com.breakout19.flooringmastery.dto.Product;
import com.breakout19.flooringmastery.dto.Tax;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cole,kevin,sarah
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer  {

    FlooringMasteryOrderDao orderDao;
//    = new FlooringMasteryOrderDaoFileImpl();
    FlooringMasteryTaxDao taxDao;
//    = new FlooringMasteryTaxDaoFileImpl();
    FlooringMasteryProductDao productDao;
//    = new FlooringMasteryProductDaoFileImpl();
    FlooringMasteryAuditDao auditDao;
//    = new FlooringMasteryAuditDaoFileImpl();

    public FlooringMasteryServiceLayerImpl(FlooringMasteryOrderDao orderDao, FlooringMasteryTaxDao taxDao, FlooringMasteryProductDao productDao, FlooringMasteryAuditDao auditDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.auditDao = auditDao;
    }
    
    
    
    @Override
    public List<Order> checkDateGetOrder(String orderDate) throws FlooringMasteryPersistenceException {
        if(orderDao.getAllOrdersForDate(orderDate).isEmpty()) {
            throw new FlooringMasteryPersistenceException("ERROR: NO ORDERS ON THAT DATE");
        } else {
            return orderDao.getAllOrdersForDate(orderDate);
        }
    }
    
    private String validateState(String stateAbbInput) throws FlooringMasteryPersistenceException, InvalidStateException {
        List<Tax> taxList = taxDao.getAllTaxes();
        List<String> stateList = new ArrayList<>();
        //list of State Abbreviations
        for(Tax currentTax : taxList) {
            stateList.add(currentTax.getStateAbb());
        }
        //check if stateAbb is in the list
        if(!stateList.contains(stateAbbInput)) {
            throw new InvalidStateException("ERROR: NO SUCH STATE");
        }
        return stateAbbInput;
    }

    @Override
    public Tax getTax(String stateAbbInput) throws FlooringMasteryPersistenceException, InvalidStateException {
        validateState(stateAbbInput);
        return taxDao.getTax(stateAbbInput);
    }

    @Override
    public void checkProductType(String productTypeInput) throws FlooringMasteryPersistenceException, InvalidProductException {
        List<Product> productList = productDao.getAllProducts();
        List<String> productTypeList = new ArrayList<>();
       // String productType = null;
        for (Product currentProduct : productList) {
            productTypeList.add(currentProduct.getProductType());
            }
        //check if productTypeInput is in the list
        if (!productTypeList.contains(productTypeInput)) {
            throw new InvalidProductException ( "ERROR: " + productTypeInput + " is not in the product list.");
        }
    }

    @Override
    public void validateCustomerName(String customerNameInput) throws FlooringMasteryPersistenceException, InvalidCustomerNameException {
        if (customerNameInput.isBlank()  || customerNameInput.isEmpty()) {  //Checks first that field is null or have whitespace/empty
            throw new InvalidCustomerNameException (
                    "ERROR: customer name cannot be blank.");
                }
    }

    @Override
    public void validateAreaMinimum(BigDecimal areaInput) throws FlooringMasteryPersistenceException, validateAreaMinException {
        if (areaInput.compareTo(new BigDecimal("100"))<0) {     //checks if order meets Minimum order size (100 sq ft).
            throw new validateAreaMinException (
            "ERROR: the area is below the minimum order");
        }
    }

    @Override
    public Order removeOrder(int orderNumber, String orderDate) throws FlooringMasteryPersistenceException, NoOrdersExistException {
         auditDao.writeAuditEntry("Order# "+ orderNumber+ " for date " + orderDate+ " REMOVED.");
        return orderDao.removeOrder(orderNumber, orderDate);
    }
    
    @Override
    public void checkIfOrderExists(List<Order> orderList, int orderNumber) throws NoOrdersExistException {
        List<Integer> orderNumberList = new ArrayList<>();
        for (Order currentOrder : orderList) {
            orderNumberList.add(currentOrder.getOrderNumber());
        }
        
        if(!orderNumberList.contains(orderNumber)) {
            throw new NoOrdersExistException("ORDER DOES NOT EXIST");
        }
    }

    @Override
    public void validateDateInput(String orderDate) throws FlooringMasteryPersistenceException, DateValidationException {
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyy"); //don't think we need this
        LocalDate orderDateInput =LocalDate.parse(orderDate, dtf);
        //String formattedDate = todayDate.format(dtf);
        if (orderDateInput.isBefore(todayDate)) {
            throw new DateValidationException ("ERROR: Date must be in the future.");
        }
    } 
    
    @Override
    //pass-through atm
    public int getNewOrderNumber(String orderDate) throws FlooringMasteryPersistenceException {
        return orderDao.getNewOrderNumber(orderDate);
    }

    @Override
    //pass-through atm
    public Product getProduct(String productType) throws FlooringMasteryPersistenceException {
        return productDao.getProduct(productType);
    }
    
    //pass-through atm
    @Override
    public BigDecimal calculateMaterialCost(BigDecimal bd, BigDecimal bd1) throws FlooringMasteryPersistenceException {
        return orderDao.calculateMaterialCost(bd1, bd1);
    }
    
    //pass-through atm
    @Override
    public BigDecimal calculateLaborCost(BigDecimal bd, BigDecimal bd1) throws FlooringMasteryPersistenceException {
        return orderDao.calculateLaborCost(bd1, bd1);
    }
    
    //pass-through atm
    @Override
    public BigDecimal calculateTax(BigDecimal bd, BigDecimal bd1, BigDecimal bd2) throws FlooringMasteryPersistenceException {
        return orderDao.calculateTax(bd2, bd2, bd2);
    }
    
    //pass-through atm
    @Override
    public BigDecimal calculateTotal(BigDecimal bd, BigDecimal bd1, BigDecimal bd2) throws FlooringMasteryPersistenceException {
        return orderDao.calculateTotal(bd2, bd2, bd2);
    }
    //pass-through atm
    @Override
    public Order addOrder(Order newOrder, String orderDate) throws FlooringMasteryPersistenceException, DateValidationException, InvalidCustomerNameException, InvalidStateException, validateAreaMinException{
       auditDao.writeAuditEntry("Order# "+ orderDao.getNewOrderNumber(orderDate) + " for date "+ orderDate+ " ADDED.");
       validateDateInput(orderDate);
       validateCustomerName(newOrder.getCustomerName());
       validateState(newOrder.getState());
       validateAreaMinimum(newOrder.getArea());
       return orderDao.addOrder(newOrder, orderDate);

    }  
    //pass-through
    @Override
    public Order getOrder(int orderNumber, String orderDate) throws FlooringMasteryPersistenceException {
        return orderDao.getOrder(orderNumber, orderDate);
    }
    //pass-through
    @Override
    public void exportData(String orderDate) throws FlooringMasteryPersistenceException {
        orderDao.exportData(orderDate);
    }
    
    //pass-through atm
    @Override
    public List<Product> getProducts () throws FlooringMasteryPersistenceException {
        return productDao.getAllProducts();
    }

    @Override
    public void initOrderFile(String orderDate) throws IOException {
        String ORDER_FILE = "Orders/Orders_" + orderDate + ".txt";
        File f = new File(ORDER_FILE);
        if (!f.exists()) {
            orderDao.initOrderFile(orderDate);
        }
    }

    @Override
    public List<Order> getAllOrdersForDate(String orderDate) throws FlooringMasteryPersistenceException {
        if(orderDao.getAllOrdersForDate(orderDate).isEmpty()) {
            throw new FlooringMasteryPersistenceException("ERROR: NO ORDERS ON THAT DATE");
        }
        else {
            return orderDao.getAllOrdersForDate(orderDate);
        }
    }
    
    @Override
    public String checkEditName(Order orderToEdit, String newCustomerName) throws FlooringMasteryPersistenceException {
        String oldName = orderToEdit.getCustomerName();
        if (newCustomerName.isEmpty() || newCustomerName.isBlank() || newCustomerName.equals(oldName)) {
            return oldName;
        } else {
            return newCustomerName;
        }
    }
    
    @Override
    public String checkEditState(Order orderToEdit, String newStateAbb) throws FlooringMasteryPersistenceException {
        String oldState = orderToEdit.getState();
        if (newStateAbb.isEmpty() || newStateAbb.isBlank() || newStateAbb.equals(oldState)) {
            return oldState;
        } else {
            return newStateAbb;
        }
    }

    @Override
    public String checkEditProduct(Order orderToEdit, String newProductType) throws FlooringMasteryPersistenceException {
        String oldProd = orderToEdit.getProductType();
        if (newProductType.isEmpty() || newProductType.isBlank() || newProductType.equals(oldProd)) {
            return oldProd;
        } else {
            return newProductType;
        }
    }
    
    @Override
    public BigDecimal checkEditArea(Order orderToEdit, String newAreaAsString) throws FlooringMasteryPersistenceException {
        String oldArea = orderToEdit.getArea().toString();
        if (newAreaAsString.isEmpty() || newAreaAsString.isBlank() || newAreaAsString.equals(oldArea)) {
            return new BigDecimal(oldArea);
        } else {
            return new BigDecimal(newAreaAsString);
        }
    }
    @Override
    public Order editOrder(Order newOrder, String orderDate) throws FlooringMasteryPersistenceException {
        auditDao.writeAuditEntry("Order# "+ orderDao.getNewOrderNumber(orderDate) + " for date "+ orderDate+ " EDITED.");
        //validateOrder(newOrder);
        return orderDao.addOrder(newOrder, orderDate);
    } 
}
          

    

