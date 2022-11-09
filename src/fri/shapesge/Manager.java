package fri.shapesge;

import java.util.ArrayList;

/**
 * Sends messages to a managed objects as defined in shapesge.ini
 * @author Ján Janech
 * @version 1.0  (9.11.2022)
 */
public class Manager {
    private final ArrayList<Object> managedObjects;

    /**
     * Create a new manager that manages no objects yet.
     */
    public Manager() {
        this.managedObjects = new ArrayList<>();
    }

    /**
     * Set `object` to be managed by the manager.
     */
    public void manageObject(Object object) {
        this.managedObjects.add(object);
    }
}
