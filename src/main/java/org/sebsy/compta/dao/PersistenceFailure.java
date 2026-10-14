package org.sebsy.compta.dao;

public class PersistenceFailure extends RuntimeException {
    public PersistenceFailure(String message) {
        super(message);
    }
}
