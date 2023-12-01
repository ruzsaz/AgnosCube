package hu.agnos.cube.measure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

@Getter
@Setter
@ToString
public class Measure extends AbstractMeasure implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    public Measure(String name, String type, boolean hidden) {
        super(name, type, hidden);
    }


    @Override
    public boolean isCalculated() {
        return false;
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

}
