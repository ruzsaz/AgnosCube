package hu.agnos.cube.measure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

@Getter
@Setter
@ToString
public class CalculatedMeasure extends AbstractMeasure  implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * Formula in postfix format to calculate the measure. Should contain only other measures and mathematical operands,
     * like "VALUE UNIT_VALUE VOLUME * -"
     **/
    private String formula;

    public CalculatedMeasure(String name, String type, boolean hidden, String formula) {
        super(name, type, hidden);
        this.formula=formula;
    }

    
    @Override
    public boolean isCalculated() {
        return true;
    }

     @Override
    public boolean isVirtual() {
        return false;
    }

}
