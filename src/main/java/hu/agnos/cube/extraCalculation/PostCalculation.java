package hu.agnos.cube.extraCalculation;

import hu.agnos.cube.dimension.Dimension;
import hu.agnos.cube.measure.AbstractMeasure;

public record PostCalculation(String type, AbstractMeasure measure, Dimension dimension) implements java.io.Serializable {
}
