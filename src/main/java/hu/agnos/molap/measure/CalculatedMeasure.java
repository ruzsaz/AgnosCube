/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap.measure;

/**
 *
 * @author parisek
 */
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

    public CalculatedMeasure(String name, String formula) {
        this.name = name;
        this.formula = formula;
    }

    /**
     * {@link CalculatedMeasure#name}
     * @return a measure egyedi neve
     */
    @Override
    public String getName() {
        return name;
    }

     /**
     * {@link CalculatedMeasure#name}
     * @param name measure egyedi neve
     */
    public void setName(String name) {
        this.name = name;
    }

     /**
     * {@link CalculatedMeasure#formula}
     * @return A measure értékének kiszámításához használt formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * {@link CalculatedMeasure#formula}
     * @param formula A measure értékének kiszámításához használt formula
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @return mindig igaz
     */
    @Override
    public boolean isCalculatedMember() {
        return true;
    }

}
