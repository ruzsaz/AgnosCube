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
public class CountDistinctCube extends Cube implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private String type;

    private int[][] cells; // Values in the cube; first index is the index of the value, second is the row-index

    public CountDistinctCube(String name, String type) {
        super(name);
        this.type = type;
    }

    public void printCells() {
//        for (int j = 0; j < cells[0].length; j++) {
//            for (float[] doubles : cells) {
//                System.out.print("\t" + doubles[j]);
//            }
//            System.out.println();
//        }
//    }
        System.out.println("majd meg tudod...");
    }
}
