/**
 * Collection of data classes to describe an Agnos Cube, including all metadata and the data itself. Allows writing and
 * reading a cube from a file, using the object serialization of Java.
 */
package hu.agnos.cube;

import gnu.trove.map.hash.TObjectIntHashMap;
import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
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
public class CountDistinctCube extends Cube {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private String type;

    private int[][] cells; // Values in the cube; first index is the index of the value, second is the row-index
    private TObjectIntHashMap<CacheKey> cache;

    private transient int maxCountDistinctElement;

    public CountDistinctCube(String name, String type) {
        super(name);
        this.type = type;
    }

    public void initCountDistinctCube() {
        maxCountDistinctElement = 0;
        for (int[] cell : cells) {
            for (int element : cell) {
                maxCountDistinctElement = Math.max(maxCountDistinctElement, element);
            }
        }
    }

    public void printCells() {
        for (AbstractMeasure m : getMeasures()) {
            System.out.println("Measure: " + m.getName() + ", type: " + m.getType() + ", hidden: " + m.isHidden());
        }

        for (int i = 0; i < Math.min(cells.length, 50); i++) {
            for (int id : cells[i]) {
                System.out.print("\t" + id);
            }
            System.out.println();
        }

        int cellNumber = 0;
        for (int[] cell : cells) {
            cellNumber += cell.length;
        }

        System.out.println("... total " + cells.length + " rows, " + cellNumber + " values.");
    }

    @Serial
    void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        init();
    }

    public void dropCells() {
        setLastAccessTime(Long.MAX_VALUE);
        setFileSize(0L);
        cells = null;
        cache = null;
    }

    public int[][] getCells() {
        setLastAccessTime(System.currentTimeMillis());
        return cells;
    }

    public void putAllToCache(Map<CacheKey, double[]> tmpCache) {
        this.cache = new TObjectIntHashMap<>(tmpCache.size());
        for (Map.Entry<CacheKey, double[]> entry : tmpCache.entrySet()) {
            CacheKey key = entry.getKey();
            double[] value = entry.getValue();
            cache.put(key, (int) value[0]);
        }
    }

    public int getCacheSize() {
        return (cache == null) ? 0 : cache.size();
    }

}
