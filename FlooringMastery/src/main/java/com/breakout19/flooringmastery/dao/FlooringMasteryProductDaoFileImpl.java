
package com.breakout19.flooringmastery.dao;

import com.breakout19.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author cole,kevin,sarah
 */
public class FlooringMasteryProductDaoFileImpl implements FlooringMasteryProductDao {
    
    private static final String PRODUCTS_FILE = "Data/Products.txt"; //created the text file
    private static final String DELIMITER = ",";
    private Map<String, Product> products = new HashMap<>();

    @Override
    public Product getProduct(String productType) throws FlooringMasteryPersistenceException{
        loadProductsFile();
        return products.get(productType);
    }
    
    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        loadProductsFile();
        return new ArrayList(products.values());   
    }

    
    private Product unmarshallProduct(String productAsText){
        String [] productTokens = productAsText.split(DELIMITER);
        
        String productType = productTokens[0];
        Product productFromFile = new Product(productType);
        
        productFromFile.setCostPerSquareFoot(new BigDecimal(productTokens[1])); //get the CostPerSquareFoot
        productFromFile.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2])); //get the LaborCostPerSquareFoot

        return productFromFile;
    }
    
    
    private void loadProductsFile() throws FlooringMasteryPersistenceException {
        //Open File:
        Scanner scanner;
        try {
            scanner = new Scanner(
            new BufferedReader(
            new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
            "-_- Could not load product data into memory",e);
        }
        String currentLine; //read from file
        Product currentProduct;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            
            products.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }
}

   
