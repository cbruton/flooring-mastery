
package com.breakout19.flooringmastery.dao;

import com.breakout19.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cole,kevin,sarah
 */
public interface FlooringMasteryProductDao {
    
    Product getProduct (String productType) throws FlooringMasteryPersistenceException;
    
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
    
    
}
