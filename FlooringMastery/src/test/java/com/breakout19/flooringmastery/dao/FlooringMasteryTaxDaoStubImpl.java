package com.breakout19.flooringmastery.dao;

import com.breakout19.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cole
 */
public class FlooringMasteryTaxDaoStubImpl implements FlooringMasteryTaxDao {
    
    Tax taxClone;
    
    public FlooringMasteryTaxDaoStubImpl() {
        taxClone = new Tax("CA");
        taxClone.setStateName("California");
        taxClone.setTaxRate(new BigDecimal("25.00"));
    }
    
    public FlooringMasteryTaxDaoStubImpl(Tax testTax) {
        this.taxClone = testTax;
    }
    
    
    @Override
    public Tax getTax(String stateAbb) throws FlooringMasteryPersistenceException {
        if (stateAbb.equals(taxClone.getStateAbb())) {
            return taxClone;
        } else {
            return null;
        }
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException {
        List<Tax> taxList = new ArrayList<>();
        taxList.add(taxClone);
        return taxList;
    }
    
}