/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap;

import hu.agnos.molap.dimension.Dimension;
import hu.agnos.molap.measure.Cells;
import hu.agnos.molap.measure.Measures;
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

    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * A kocka egyedi neve
     */
    private final String name;


    /**
     * A kockában lévő dimenziók listája
     */
    private final List<Dimension> dimensions;

    /**
     * a hierarchiák egyedi nevét tartalmazó tömb, a sorrend kötött. Erre azért
     * van szükség, mert a lekérdezések esetében csak hierarchiák léteznek
     */
    private String[] dimensionHeader;

    /**
     * Ez a hiearachia sorszámához (amely a hierarchyHeader-ből tudható meg),
     * megmondja, hogy az hányadik dimenzió, hányadik hierarchia eleme. Ez egy
     * mátrix, amelynek fejléce az alábbi: hierarchia sorszáma, dimenzióIdx, hierarchiaIdx
     */
    private int[][] dimensionIndex;

    /**
     * A measure-ket tartalmazó tömb Ez metaadat, measure neve és calculated
     * measur esetében annak kiszámításához használt formula
     */
    private Measures measures;

    /**
     * A mutatók egyedi nevét tartalmazó tömb, a sorrend kötött. Erre lekérdezési
     * időben van szükség, tudni kell, hogy a cell melyik oszlopa, melyik
     * measure értékét reprezentálja. Ez redundáns, a measures tömből kinyerhető
     * (érteke onnan származik), de a lekérdezések gyosabbá válnak általa.
     */
    private String[] measureHeader;

    /**
     * A mutatók konkrét értékei
     */
    private Cells cells;

    /**
     * A kocka építésének dátuma
     */
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
     * Visszaadja az adott indexű dimenziót
     *
     * @param idx a keresett dimenzió indexe
     * @return a keresett dimenzió
     * @throws IndexOutOfBoundsException - ha az index nagyobb a dimenziókat
     * tartalmazó lista méreténél
     * @see hu.agnos.molap.dimension.Dimension
     */
    public Dimension getDimensionByIdx(int idx) {
        return dimensions.get(idx);
    }


    /**
     * Visszaadja az adott indexhez tartozo dimenzió és hierarchia indexeket egy
     * kételemű vektorban.
     *
     * @param idx melyik sorszámú hierarchiát keressük
     * @return {dimenzioIdx, hierarcyIdx}
     */
    public int[] getDimensionInfoByIndex(int idx) {
        return dimensionIndex[idx];
    }

    /**
     * Visszaadja az adott nevű hierarchiához tartozó dimenzió és hierarchia
     * indexeket egy kételemű vektorban.
     *
     * @param dimensionUniquename hierarchia egyedi neve
     * @return {dimenzioIdx, hierarcyIdx}
     */
    public int[] getDimensionInfoByHeader(String dimensionUniquename) {
        int[] result = null;
        for (int i = 0; i < dimensionHeader.length; i++) {
            if (dimensionHeader[i].equals(dimensionUniquename)) {
                result = getDimensionInfoByIndex(i);
                break;
            }
        }
        return result;
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


    /**
     * Beállítja a két header tömböt
     */
    public void refreshHeader() {
        this.measureHeader = this.measures.getHeader();
        this.dimensionHeader = this.generateDimensionHeader();
    }

    /**
     * Visszaadja az összes hierarchia nevét egy tömbe rendezve. A sorrend
     * Kötött!!!!
     *
     * @return A hierarchia neveket tartalmazó, köttött sorendű tömb
     */
    private String[] generateDimensionHeader() {
        int dimensionCnt = this.dimensions.size();
        String[] dimHeader = new String[dimensionCnt];        
        for (int i = 0; i < dimensionCnt; i++) {
                dimHeader[i] = this.dimensions.get(i).getDimensionUniqueName();                
        }
        return dimHeader;
    }
}
