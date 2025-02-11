package hu.agnos.cube.measure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;

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
    
    String getType();; 


    /**
     * Megadja, hogy a mutató kalkulált vagy ténylegesen tárolt mutató-e
     *
     * @return true ha a mutató kalkulált, ellenben false
     */
    boolean isCalculated();
    
    boolean isVirtual();

    boolean isHidden();

}
