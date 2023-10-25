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
@AllArgsConstructor
@ToString
public class Measure implements java.io.Serializable, AbstractMeasure{
      
    private static final long serialVersionUID = -8940196742313994740L;
    
    /**
     * A measure egyedi neve
     */
    String name;   

    /**
     * Visszaadja, hogy az adott mutató valós vagy kalkulált-e, jelen esetben ez mindig hamis, azaz valós mutató
     * @return mindig hamis
     */
    @Override
    public boolean isCalculatedMember() {
        return false;
    }
    
}
