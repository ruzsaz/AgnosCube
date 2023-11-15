package hu.agnos.cube.measure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class VirtualMeasure implements java.io.Serializable, AbstractMeasure {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private String name;

    private String dimensionName;

    private String type; 
            
    @Override
    public boolean isCalculated() {
        return false;
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

}
