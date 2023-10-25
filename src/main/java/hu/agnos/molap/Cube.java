/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap;

import hu.agnos.molap.dimension.Dimension;
import hu.agnos.molap.measure.Cells;
import hu.agnos.molap.measure.Measures;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author parisek
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

    /**
     * A kocka konstruktora.
     * @param name a kocka neve
     */
    public Cube(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>();
        this.createdDate = new Date();
    }

    /**
     * Beszúr egy dimenziót a dimenziók listájába
     *
     * @param idx az új dimenzió listabéli indexe
     * @param dimension a beszúrandó dimenzió
     * @see hu.agnos.molap.dimension.Dimension
     */
    public void addDimension(int idx, Dimension dimension) {
        this.dimensions.add(idx, dimension);
    }

}
