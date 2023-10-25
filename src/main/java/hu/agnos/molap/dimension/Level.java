/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap.dimension;

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

    private static final long serialVersionUID = -8940196742313994740L;
    
    /**
     * Az adott szint a hierarchia milyen mélységé áll
     */
    private final int depth;
    
    /**
     * Az adott szint egyedi neve
     */
    private final String name;

}
