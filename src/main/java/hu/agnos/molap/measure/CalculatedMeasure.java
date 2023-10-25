/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap.measure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author parisek
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CalculatedMeasure implements java.io.Serializable, AbstractMeasure {

    private static final long serialVersionUID = -8940196742313994740L;
    
    /**
     * A measure egyedi neve
     */
    String name;

    /**
     * A measure értékének kiszámításához használt formula.
     * A formulát postfix alakban kell megadni, benne az operátorokat
     * és az operandusokat szóközzel szeparálva.
     * Operandus csak measure lehet, és annak az egyedi nevét kell megadni.
     *  pl: az ARRES calculált measure kiszámításához használható formula:
     *      ERTEK ELABE_EGYSEGAR MENNYISEG * -
     */
    String formula;

    /**
     *
     * @return mindig igaz
     */
    @Override
    public boolean isCalculatedMember() {
        return true;
    }
}
