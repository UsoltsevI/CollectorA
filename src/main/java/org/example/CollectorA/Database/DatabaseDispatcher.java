package org.example.CollectorA.Database;

/**
 * Database Dispatcher interface.
 * It uses to connect to the database and operate with a one table from the database.
 * There are only commands to write data to the database
 * @author UsoltsevI
 */
public interface DatabaseDispatcher {
    /**
     * This method opens a new connections with the database and
     * creates a table with tableName. If such table is already exists
     * it connects to it. Returns true if the operations complited successfully
     * @param tableName
     * @return true if connected successfully, false if failed
     */
    public boolean connect(String tableName);

    /**
     * This methods saves data in table and returns true if saved successful
     * @param data - user data to save
     * @return true if successful, false if not
     */
    public boolean saveData(DataModel data);

    /**
     * This method checks if a user with the same id has been recorded to the database
     * Returns true if already recorded, false if not
     * @param id - userid or username to check if exists
     * @return true if recorded, false if not
     */
    public boolean isAlreadyRecorded(DataModel data);

    /**
     * This method closes a connection with server
     */
    public void close();
}
