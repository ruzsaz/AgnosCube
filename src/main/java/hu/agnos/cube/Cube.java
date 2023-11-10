/**
 * Collection of data classes to describe an Agnos Cube, including all metadata and the data itself. Allows writing and
 * reading a cube from a file, using the object serialization of Java.
 */
package hu.agnos.cube;

import hu.agnos.cube.dimension.Dimension;
import hu.agnos.cube.extraCalculation.PostCalculation;
import hu.agnos.cube.measure.AbstractMeasure;
import hu.agnos.cube.measure.Cells;
import hu.agnos.cube.measure.Measure;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.ToString;

/**
 * Collection of data classes to describe an Agnos Cube, including all metadata
 * and the data itself. Allows writing and reading a cube from a file, using the
 * object serialization of Java.
 */
@Getter
@Setter
@ToString
public class Cube implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private final String name;
    private final List<Dimension> dimensions;
    private String[] dimensionHeader;
    private final List<AbstractMeasure> measures;

    private String[] measureHeader;
    private Cells cells;
    private Date createdDate;
    private List<PostCalculation> postCalculations;

    public Cube(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>(6);
        this.measures = new ArrayList<>(6);
        this.createdDate = new Date();
        this.postCalculations = new ArrayList<>(1);
    }

    public void init() {
        refreshDimensionHeader();
        refreshMeasureHeader();
        dimensions.forEach(Dimension::initLookupTable);
    }

    private void refreshDimensionHeader() {
        int dimensionNumber = dimensions.size();
        this.dimensionHeader = new String[dimensionNumber];
        for (int i = 0; i < dimensionNumber; i++) {
            dimensionHeader[i] = dimensions.get(i).getName();
        }
    }

    private void refreshMeasureHeader() {
        int measuresSize = measures.size();
        this.measureHeader = new String[measuresSize];
        for (int i = 0; i < measuresSize; i++) {
            measureHeader[i] = measures.get(i).getName();
        }

    }

    /**
     * Inserts a new dimension in the order list of dimensions. The element in
     * that position, and all elements right of it will be shifted right by 1
     * position
     *
     * @param idx Position of the new dimension to insert to
     * @param dimension Dimension to insert
     * @see hu.agnos.cube.dimension.Dimension
     */
    public void addDimension(int idx, Dimension dimension) {
        dimensions.add(idx, dimension);
    }

    public Dimension getDimensionByName(String dimensionName) {
        Dimension result = null;
        for (Dimension dimension : dimensions) {
            if (dimension.getName().equals(dimensionName)) {
                result = dimension;
                break;
            }
        }
        return result;
    }

    /**
     * Egy mutató metájának hozzáadása
     *
     * @param measure a hozzáadandó mutató
     */
    public void addMeasure(AbstractMeasure measure) {
        measures.add(measure);
    }

    public AbstractMeasure getMeasureByName(String measureName) {
        AbstractMeasure result = null;
        for (AbstractMeasure measure : this.measures) {
            if (measure.getName().equals(measureName)) {
                result = measure;
                break;
            }
        }
        return result;
    }

    /**
     * Determines the index of a non-calculated measure within the cells.
     *
     * @param measureName Name of the measure
     * @return Index of the measure within the cells
     */
    public int getRealMeasureIdxByName(String measureName) {
        int i = 0;
        for (AbstractMeasure member : measures) {
            if (member.getName().equals(measureName)) {
                return i;
            }
            if (!member.isCalculatedMember()) {
                i++;
            }
        }
        return -1;
    }

    /**
     * Megadja a nem kalkulált measure-ök darabszámát
     *
     * @return nem kalkulált measure-ök darabszáma
     */
    public int getRealMeasureCount() {
        int result = 0;
        for (AbstractMeasure abstractMeasure : measures) {
            if (abstractMeasure instanceof Measure) {
                result++;
            }
        }
        return result;
    }

    /**
     * Egy mutató metájának eltávolitása
     *
     * @param measure Az eltávolítandó mutató
     */
    public void removeMeasure(AbstractMeasure measure) {
        measures.remove(measure);
    }

}
