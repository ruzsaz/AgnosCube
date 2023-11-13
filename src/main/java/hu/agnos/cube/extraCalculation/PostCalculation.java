package hu.agnos.cube.extraCalculation;

import hu.agnos.cube.dimension.Dimension;
import hu.agnos.cube.dimension.Level;
import hu.agnos.cube.measure.AbstractMeasure;

public record PostCalculation(String type, AbstractMeasure measure, Dimension dimension, Level level) implements java.io.Serializable {

    public PostCalculation(String type, AbstractMeasure measure, Dimension dimension) {
        this(type, measure, dimension, null);
    }

}
