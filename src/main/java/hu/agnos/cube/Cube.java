/**
 * Collection of data classes to describe an Agnos Cube, including all metadata and the data itself. Allows writing and
 * reading a cube from a file, using the object serialization of Java.
 */
package hu.agnos.cube;

import hu.agnos.cube.dimension.Dimension;
import hu.agnos.cube.extraCalculation.PostCalculation;
import hu.agnos.cube.measure.Cells;
import hu.agnos.cube.measure.Measures;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Collection of data classes to describe an Agnos Cube, including all metadata and the data itself.
 * Allows writing and reading a cube from a file, using the object serialization of Java.
 */
@Getter
@Setter
public class Cube implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private final String name;
    private final List<Dimension> dimensions;
    private String[] dimensionHeader;
    private Measures measures;
    private String[] measureHeader;
    private Cells cells;
    private Date createdDate;
    private List<PostCalculation> postCalculations;

    public Cube(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>();
        this.createdDate = new Date();
        this.postCalculations = new ArrayList<>();
    }

    public void init() {
        refreshDimensionHeader();
        refreshMeasureHeader();
//        dimensions.forEach(d -> d.getNode(0,0));
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
        this.measureHeader = measures.getHeader();
    }

    /**
     * Inserts a new dimension in the order list of dimensions. The element
     * in that position, and all elements right of it will be shifted right by
     * 1 position
     *
     * @param idx Position of the new dimension to insert to
     * @param dimension Dimension to insert
     * @see hu.agnos.cube.dimension.Dimension
     */
    public void addDimension(int idx, Dimension dimension) {
        dimensions.add(idx, dimension);
    }

    public Dimension getDimensionByName(String dimensionName){
        Dimension result = null;
        for(Dimension d : this.dimensions){
            if(d.getName().equals(dimensionName)){
                result = d;
                break;
            }
        }
        return result;
    }
}
