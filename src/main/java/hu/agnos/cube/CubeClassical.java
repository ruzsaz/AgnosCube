/**
 * Collection of data classes to describe an Agnos Cube, including all metadata and the data itself. Allows writing and
 * reading a cube from a file, using the object serialization of Java.
 */
package hu.agnos.cube;

import lombok.Getter;

import java.io.Serial;
import lombok.Setter;
import lombok.ToString;

/**
 * Collection of data classes to describe an Agnos Cube, including all metadata
 * and the data itself. Allows writing and reading a cube from a file, using the
 * object serialization of Java.
 */
@Getter
@Setter
@ToString
public class CubeClassical extends Cube implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private final String type;

    private float[][] cells; // Values in the cube; first index is the index of the value, second is the row-index

    public CubeClassical(String name, String type) {
        super(name);
        this.type= type;
    }

}
