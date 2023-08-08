/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap.measure;

/**
 * Ez az interface a mutatók egységes kezelésére szolgál, szerepe, hogy mind a
 * kalkulált, mind a ténylegesen tárolt mutatókat ugyanúgy kezeljük
 *
 * @author parisek
 */
public interface AbstractMeasure {

    /**
     * Visszaadja a mutató kockán belüli egyedi nevét
     * @return a mutató egyedi neve
     */
    public abstract String getName();

    /**
     * Megadja, hogy a mutató kalkulált vagy ténylegesen tárolt mutató-e
     * @return true ha a mutató kalkulált, ellenben false
     */
    public abstract boolean isCalculatedMember();

}
