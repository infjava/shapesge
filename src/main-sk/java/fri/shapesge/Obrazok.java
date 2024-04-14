package fri.shapesge;

import fri.shapesge.drawables.ImageDrawable;

/**
 * Trieda Obrazok, reprezentuje bitmapový obrázok, ktorý môže byť vykreslený na plátno.
 *
 * @author originál: Miroslav Kvaššay and Michal Varga
 * @author engine: Ján Janech
 * @version 1.1
 */
@SuppressWarnings("unused")
public class Obrazok {
    private final ImageDrawable drawable;

    /**
     * Vytvor nový obrázok s danou cestou na preddefinovanej pozícii.
     * @param suborSObrazkom cesta k súboru s obrázkom
     */
    @SuppressWarnings("unused")
    public Obrazok(String suborSObrazkom) {
        this(new DataObrazku(suborSObrazkom));
    }

    /**
     * Vytvor nový obrázok s danou cestou na preddefinovanej pozícii.
     * @param dataObrazku dáta obrázku
     */
    public Obrazok(DataObrazku dataObrazku) {
        this(dataObrazku, 100, 100);
    }

    /**
     * Vytvor nový obrázok s danou cestou na danej pozícii.
     * @param suborSObrazkom cesta k súboru s obrázkom
     * @param x x-ová súradnica obrázku
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica obrázku
     *          (vzdialenosť od horného okraja plátna)
     */
    @SuppressWarnings("unused")
    public Obrazok(String suborSObrazkom, int x, int y) {
        this(new DataObrazku(suborSObrazkom), x, y);
    }

    /**
     * Vytvor nový obrázok s danou cestou na danej pozícii.
     * @param dataObrazku dáta obrázku
     * @param x x-ová súradnica obrázku
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica obrázku
     *          (vzdialenosť od horného okraja plátna)
     */
    public Obrazok(DataObrazku dataObrazku, int x, int y) {
        this.drawable = new ImageDrawable(x, y, 0, dataObrazku.getImage());
    }

    /**
     * Zobraz sa.
     */
    @SuppressWarnings("unused")
    public void zobraz() {
        this.drawable.makeVisible();
    }

    /**
     * Skry sa.
     */
    @SuppressWarnings("unused")
    public void skry() {
        this.drawable.makeInvisible();
    }

    /**
     * Posuň sa vpravo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVpravo() {
        this.drawable.moveBy(20, 0);
    }

    /**
     * Posuň sa vľavo o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunVlavo() {
        this.drawable.moveBy(-20, 0);
    }

    /**
     * Posuň sa hore o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunHore() {
        this.drawable.moveBy(0, -20);
    }

    /**
     * Posuň sa dole o pevnú dĺžku.
     */
    @SuppressWarnings("unused")
    public void posunDole() {
        this.drawable.moveBy(0, 20);
    }

    /**
     * Posuň sa zvisle o dĺžku danú parametrom.
     * @param vzdialenost vzdialenosť v pixeloch
     */
    @SuppressWarnings("unused")
    public void posunVodorovne(int vzdialenost) {
        this.drawable.moveBy(vzdialenost, 0);
    }

    /**
     * Posuň sa pomaly vodorovne o dĺžku danú parametrom.
     * @param vzdialenost vzdialenosť v pixeloch
     */
    @SuppressWarnings("unused")
    public void posunZvisle(int vzdialenost) {
        this.drawable.moveBy(0, vzdialenost);
    }

    /**
     * Zmeň obrázok.
     * Súbor s obrázkom musí existovať.
     * @param suborSObrazkom cesta k súboru s obrázkom
     */
    @SuppressWarnings("unused")
    public void zmenObrazok(String suborSObrazkom) {
        this.zmenObrazok(new DataObrazku(suborSObrazkom));
    }

    /**
     * Zmeň obrázok.
     * @param dataObrazku dáta obrázku
     */
    public void zmenObrazok(DataObrazku dataObrazku) {
        this.drawable.changeImage(dataObrazku.getImage());
    }

    /**
     * Zmeň uhol natočenia obrázku podľa parametra.
     * @param uhol uhol natočenia v stupňoch v smere hodinových ručičiek
     */
    @SuppressWarnings("unused")
    public void zmenUhol(int uhol) {
        this.drawable.changeAngle(uhol);
    }

    /**
     * Zmeň polohu obrázka na hodnoty dané parametrami.
     * @param x x-ová súradnica obrázku
     *          (vzdialenosť od ľavého okraja plátna)
     * @param y y-ová súradnica obrázku
     *          (vzdialenosť od horného okraja plátna)
     */
    @SuppressWarnings("unused")
    public void zmenPolohu(int x, int y) {
        this.drawable.moveTo(x, y);
    }

    /**
     * Zrkadlovito otoč obrázok v horizontálnom smere (v smere jeho y-ovej osi).
     */
    @SuppressWarnings("unused")
    public void otocHorizontalne() {
        this.drawable.flip();
    }
}
