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
public class Measure implements java.io.Serializable, AbstractMeasure{
      
    private static final long serialVersionUID = -8940196742313994740L;
    
    /**
     * A measure egyedi neve
     */
    String name;   

    public Measure(String name) {
        this.name = name;       
    }

    /**
     * Visszaadja a mutató egyedi nevét
     * @return a measure egyedi neve
     */
    @Override
    public String getName() {
        return name;
    }

    /** 
     * Beállítja a mutató egyedi nevét nevét
     * @param name measure egyedi neve
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Visszaadja, hogy az adott mutató valós vagy kalkulált-e, jelen esetben ez mindig hamis, azaz valós mutató
     * @return mindig hamis
     */
    @Override
    public boolean isCalculatedMember() {
        return false;
    }
    
}
