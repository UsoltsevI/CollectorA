package org.example.CollectorA.Database;

import java.util.HashMap;

/**
 * This interface to describe data model saved in database
 * @author UsoltsevI
 */
public interface DataModel {
    public byte[] getId();
    /**
     * Returns all fields as HashMap{@literal <}byte[] fieldName, byte[] fieldValue{@literal >}
     * @return all fileds and its values as bytes
     */
    public HashMap<byte[],byte[]> getFieldsAsBytes();
}
