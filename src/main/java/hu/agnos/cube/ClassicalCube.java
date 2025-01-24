/**
 * Collection of data classes to describe an Agnos Cube, including all metadata and the data itself. Allows writing and
 * reading a cube from a file, using the object serialization of Java.
 */
package hu.agnos.cube;

import lombok.Getter;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

import lombok.Setter;
import lombok.ToString;

import hu.agnos.cube.measure.AbstractMeasure;
import hu.agnos.cube.meta.queryDto.CacheKey;

/**
 * Collection of data classes to describe an Agnos Cube, including all metadata
 * and the data itself. Allows writing and reading a cube from a file, using the
 * object serialization of Java.
 */
@Getter
@Setter
@ToString
public class ClassicalCube extends Cube {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private final String type;

    private float[][] cells; // Values in the cube; first index is the index of the value, second is the row-index
    private Map<CacheKey, double[]> cache;

    public ClassicalCube(String name, String type) {
        super(name);
        this.type= type;
    }

    public void printCells() {
        for (AbstractMeasure m : getMeasures()) {
            System.out.println("Measure: " + m.getName() + ", type: " + m.getType() + ", hidden: " + m.isHidden());
        }

        for (int j = 0; j < Math.min(50, cells[0].length); j++) {
            for (float[] doubles : cells) {
                System.out.print("\t" + doubles[j]);
            }
            System.out.println();
        }
    }

    public void dropCells() {
        setLastAccessTime(Long.MAX_VALUE);
        setFileSize(0L);
        cells = null;
        cache = null;
    }

    public float[][] getCells() {
        setLastAccessTime(System.currentTimeMillis());
        return cells;
    }

    public void putAllToCache(Map<CacheKey, double[]> tmpCache) {
        this.cache = new HashMap<>(tmpCache.size());
        cache.putAll(tmpCache);
    }

    public int getCacheSize() {
        return (cache == null) ? 0 : cache.size();
    }

}
