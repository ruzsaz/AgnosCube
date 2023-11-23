package hu.agnos.cube.measure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CalculatedMeasure implements java.io.Serializable, AbstractMeasure {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private String name;
    
    private String type;

    /**
     * Formula in postfix format to calculate the measure. Should contain only other measures and mathematical operands,
     * like "VALUE UNIT_VALUE VOLUME * -"
     **/
    private String formula;

    @Override
    public boolean isCalculated() {
        return true;
    }

     @Override
    public boolean isVirtual() {
        return false;
    }

}
