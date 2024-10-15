package org.example.CollectorA.Database;

/**
 * Database Dispatcher interface
 * @author UsoltsevI
 */
public interface DatabaseDispatcher {
    /**
     * This methods saves data in table and returns true if saved successful
     * @param data - user data to save
     * @return true if successful, false if not
     */
    public boolean saveData(UserDataModel data);

    /**
     * This method checks if a user with the same id has been recorded to the database
     * Returns true if already recorded, false if not
     * @param id - userid or username to check if exists
     * @return true if recorded, false if not
     */
    public boolean isAlreadyRecorded(UserDataModel data);
}
