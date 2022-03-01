
package com.breakout19.flooringmastery.dao;

/**
 *
 * @author breakout19
 */
public interface FlooringMasteryAuditDao {
    public void writeAuditEntry(String entry) throws FlooringMasteryPersistenceException;
}
