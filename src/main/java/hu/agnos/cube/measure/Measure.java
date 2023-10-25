package hu.agnos.cube.measure;

import java.io.Serial;

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
public class Measure implements java.io.Serializable, AbstractMeasure {
      
    @Serial
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
