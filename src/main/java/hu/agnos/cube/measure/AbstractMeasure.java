package hu.agnos.cube.measure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Ez az interface a mutatók egységes kezelésére szolgál, szerepe, hogy mind a kalkulált, mind a ténylegesen tárolt
 * mutatókat ugyanúgy kezeljük
 *
 * @author parisek
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractMeasure {

    public String name;

    public String type; 
    
    public boolean hidden;   

    /**
     * Megadja, hogy a mutató kalkulált vagy ténylegesen tárolt mutató-e
     *
     * @return true ha a mutató kalkulált, ellenben false
     */
    public abstract boolean isCalculated();
    
    public abstract boolean isVirtual();

}
