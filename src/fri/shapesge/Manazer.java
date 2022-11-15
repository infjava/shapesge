package fri.shapesge;

/**
 * Sends messages to a managed objects as defined in spge.ini
 * @author Ján Janech
 * @version 1.0  (9.11.2022)
 */
@SuppressWarnings("unused")
public class Manazer {
    /**
     * Create a new manager that manages no objects yet.
     */
    public Manazer() {

    }

    /**
     * Set `object` to be managed by the manager.
     */
    public void manageObject(Object object) {
        Game.getGame().registerEventTarget(object);
    }

    /**
     * Set `object` to be managed by the manager.
     */
    public void stopManagingObject(Object object) {
        Game.getGame().deregisterEventTarget(object);
    }
}
