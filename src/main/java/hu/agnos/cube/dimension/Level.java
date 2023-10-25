/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.cube.dimension;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Ez az osztály a hierarchiában szereplő hierarchia szintek metaadatait írja le (pl.: Megye, kistérség vagy település).
 * @author parisek
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Level implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;
    
    /**
     * Az adott szint a hierarchia milyen mélységé áll
     */
    private final int depth;
    
    /**
     * Az adott szint egyedi neve
     */
    private final String name;

    /**
     * A szinthez tartozó azonosítót tartalamzó oszlop neve, szerepe a
     * cube-ról készült reportnál jelentkezik
     */
    private final String idColumnName;
    
    /**
     * A szinthez tartozó kódot tartalamzó oszlop neve, szerepe a
     * cube-ról készült reportnál jelentkezik
     */
    private final String codeColumnName;
    
    /**
     * A szinthez tartozó nevet tartalamzó oszlop neve, szerepe a
     * cube-ról készült reportnál jelentkezik
     */    
    private final String nameColumnName;

    public Level(int depth, String name) {
        this.depth = depth;
        this.name = name;
        idColumnName=null;
        codeColumnName=null;
        nameColumnName=null;
    }

    
    
}