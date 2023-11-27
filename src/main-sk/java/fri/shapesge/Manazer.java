package fri.shapesge;

import fri.shapesge.engine.Game;

/**
 * Automaticky posiela správy daným objektom ako je definované v spge.ini
 * @author Ján Janech
 * @version 1.0  (9.11.2022)
 */
@SuppressWarnings("unused")
public class Manazer {
    /**
     * Vytvorí nového manažéra, ktorý nespravuje zatiať žiadne objekty.
     */
    public Manazer() {

    }

    /**
     * Manažér bude spravovať daný objekt.
     * @param objekt objekt, ktorý sa má spravovať
     */
    public void spravujObjekt(Object objekt) {
        Game.getGame().registerEventTarget(objekt);
    }

    /**
     * Manažér prestane spravovať daný objekt.
     * @param objekt objekt, ktorý sa už nemá spravovať
     */
    public void prestanSpravovatObjekt(Object objekt) {
        Game.getGame().deregisterEventTarget(objekt);
    }
}
