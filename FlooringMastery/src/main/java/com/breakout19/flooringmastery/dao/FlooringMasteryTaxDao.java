
package com.breakout19.flooringmastery.dao;

import com.breakout19.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author cole,kevin,sarah
 */
public interface FlooringMasteryTaxDao {
    
    Tax getTax(String stateAbb) throws FlooringMasteryPersistenceException;
    List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException;
  
    
}
