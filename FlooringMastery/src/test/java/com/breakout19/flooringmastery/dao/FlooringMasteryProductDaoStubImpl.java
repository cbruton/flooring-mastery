package com.breakout19.flooringmastery.dao;

import com.breakout19.flooringmastery.dto.Product;
import com.breakout19.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cole
 */
public class FlooringMasteryProductDaoStubImpl implements FlooringMasteryProductDao{

     Product onlyProduct;

    public FlooringMasteryProductDaoStubImpl() {
        onlyProduct = new Product("Wood");
        onlyProduct.setCostPerSquareFoot(new BigDecimal("5.15"));
        onlyProduct.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
    }
    
    public FlooringMasteryProductDaoStubImpl(Product testProduct) {
        this.onlyProduct = testProduct;
    }
    
    @Override
    public Product getProduct(String productType) throws FlooringMasteryPersistenceException {
        if (productType.equals(onlyProduct.getProductType())) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        List<Product> productList = new ArrayList<>();
        productList.add(onlyProduct);
        return productList;
    }
    
}
