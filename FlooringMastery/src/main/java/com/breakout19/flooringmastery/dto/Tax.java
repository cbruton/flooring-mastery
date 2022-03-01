
package com.breakout19.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author cole,kevin,sarah
 */
public class Tax {
    
    private String stateAbb;
    private String stateName;
    private BigDecimal taxRate;
    
    public Tax(String stateAbb) {
        this.stateAbb = stateAbb;
    }

    public String getStateAbb() {
        return stateAbb;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setStateAbb(String stateAbb) {
        this.stateAbb = stateAbb;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    
    
    
}

