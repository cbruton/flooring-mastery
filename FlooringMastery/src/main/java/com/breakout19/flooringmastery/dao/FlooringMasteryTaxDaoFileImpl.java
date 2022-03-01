
package com.breakout19.flooringmastery.dao;

import com.breakout19.flooringmastery.dto.Tax;
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
public class FlooringMasteryTaxDaoFileImpl implements FlooringMasteryTaxDao {
    
    private static final String TAX_FILE = "Data/Taxes.txt"; //created the text file
    private static final String DELIMITER = ",";
    private Map <String, Tax> taxes = new HashMap<>();
    
    
     @Override
    public List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException {
        loadTaxFile();
        return new ArrayList(taxes.values());
    }

    @Override
    public Tax getTax(String stateAbb) throws FlooringMasteryPersistenceException {
        loadTaxFile();
        return taxes.get(stateAbb);
    }

  
   //unmarshalling
    private Tax unmarshall(String taxAsText){
    String[] taxTokens = taxAsText.split(DELIMITER);
        String stateAbb = taxTokens[0];
        
        Tax taxFromFile = new Tax(stateAbb);
        
        taxFromFile.setStateName(taxTokens[1]);
        taxFromFile.setTaxRate(new BigDecimal(taxTokens[2]));
        return taxFromFile;
    }
    
    
    
    private void loadTaxFile() throws FlooringMasteryPersistenceException {
        
//        Scanner scanner;
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException (
                    "Could not load tax file data into memory.", e);
        }
        
        String currentLine;
        Tax currentTax;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshall(currentLine);
            taxes.put(currentTax.getStateAbb(), currentTax);
        }
        scanner.close();
    
    }   
}
