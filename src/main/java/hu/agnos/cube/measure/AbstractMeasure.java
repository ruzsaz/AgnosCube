package hu.agnos.cube.measure;

/**
 * Ez az interface a mutatók egységes kezelésére szolgál, szerepe, hogy mind a kalkulált, mind a ténylegesen tárolt
 * mutatókat ugyanúgy kezeljük
 *
 * @author parisek
 */
public interface AbstractMeasure {

    /**
     * Visszaadja a mutató kockán belüli egyedi nevét
     *
     * @return a mutató egyedi neve
     */
    String getName();

    /**
     * Megadja, hogy a mutató kalkulált vagy ténylegesen tárolt mutató-e
     *
     * @return true ha a mutató kalkulált, ellenben false
     */
    boolean isCalculatedMember();

}
