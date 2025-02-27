package hu.agnos.cube.measure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Measure implements java.io.Serializable, AbstractMeasure {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private String name;
    private String type;
    private boolean hidden;

    @Override
    public boolean isCalculated() {
        return false;
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

}
