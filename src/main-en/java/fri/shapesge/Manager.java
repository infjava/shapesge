package fri.shapesge;

import fri.shapesge.engine.Game;

/**
 * Sends messages to a managed objects as defined in spge.ini
 * @author Ján Janech
 * @version 1.0  (9.11.2022)
 */
@SuppressWarnings("unused")
public class Manager {
    /**
     * Create a new manager that manages no objects yet.
     */
    public Manager() {

    }

    /**
     * Set `object` to be managed by the manager.
     * @param object object to be managed
     */
    public void manageObject(Object object) {
        Game.getGame().registerEventTarget(object);
    }

    /**
     * Set `object` to be managed by the manager.
     * @param object managed object
     */
    public void stopManagingObject(Object object) {
        Game.getGame().deregisterEventTarget(object);
    }
}
