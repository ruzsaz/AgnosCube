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

/**
 *
 * @author parisek
 */
public class Cube implements java.io.Serializable {

    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * A kocka egyedi neve
     */
    private final String name;

    /**
     * A forrástábla neve, erre az importálás miatt van szükség
     */
    private String sourceTableName;

    /**
     * A kockában lévő dimenziók listája
     */
    private final List<Dimension> dimensions;

    /**
     * a hierarchiák egyedi nevét tartalmazó tömb, a sorrend kötött. Erre azért
     * van szükség, mert a lekérdezések esetében csak hierarchiák léteznek
     */
    private String[] hierarchyHeader;

    /**
     * Ez a hiearachia sorszámához (amely a hierarchyHeader-ből tudható meg),
     * megmondja, hogy az hányadik dimenzió, hányadik hierarchia eleme. Ez egy
     * mátrix, amelynek fejléce az alábbi: sorszáma, dimenzióIdx, hierarchiaIdx
     */
    private int[][] hierarchyIndex;

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
        this.sourceTableName = null;
        this.createdDate = new Date();
    }

    /**
     * Visszaadja a dimenziók listáját
     *
     * @return dimenziók listája
     * @see hu.agnos.molap.dimension.Dimension
     */
    public List<Dimension> getDimensions() {
        return dimensions;
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
     * Visszaadja a hierarchia headert, amely a hiearachia sorszámához (amely a
     * hierarchyHeader-ből tudható meg), megmondja, hogy az hányadik dimenzió,
     * hányadik hierarchia eleme. Ez egy mátrix, amelynek fejléce az alábbi:
     * sorszáma, dimenzióIdx, hierarchiaIdx
     *
     * @return hierarchie header
     */
    public String[] getHierarchyHeader() {
        return hierarchyHeader;
    }

    /**
     * Visszaadja a hierarchia Indexet, amely a hiearachia sorszámához (amely a
     * hierarchyHeader-ből tudható meg), megmondja, hogy az hányadik dimenzió,
     * hányadik hierarchia eleme. Ez egy mátrix, amelynek fejléce az alábbi:
     * sorszáma, dimenzióIdx, hierarchiaIdx.
     *
     * @return hierarchia index
     */
    public int[][] getHierarchyIndex() {
        return hierarchyIndex;
    }

    /**
     * Visszaadja a kocka létrehozásának dátumát.
     *
     * @return a kocka létrehozásának dátuma
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Beállítja a kocka építésének dátumát.
     *
     * @param createdDate a kocka építésének dátuma
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Visszaadja az adott indexhez tartozo dimenzió és hierarchia indexeket egy
     * kételemű vektorban.
     *
     * @param idx melyik sorszámú hierarchiát keressük
     * @return {dimenzioIdx, hierarcyIdx}
     */
    public int[] getHierarchyInfoByIndex(int idx) {
        return hierarchyIndex[idx];
    }

    /**
     * Visszaadja az adott nevű hierrchiához tartozó dimenzió és hierarchia
     * indexeket egy kételemű vektorban.
     *
     * @param hierarchyUniquename hierarchie egyedi neve
     * @return {dimenzioIdx, hierarcyIdx}
     */
    public int[] getHierarchyInfoByHeader(String hierarchyUniquename) {
        int[] result = null;
        for (int i = 0; i < hierarchyHeader.length; i++) {
            if (hierarchyHeader[i].equals(hierarchyUniquename)) {
                result = getHierarchyInfoByIndex(i);
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
     * Visszaadja a forrástábla nevét
     *
     * @return a tárolt forrástábla neve
     */
    public String getSourceTableName() {
        return sourceTableName;
    }

    /**
     * Beállítja a forrástábla nevét
     *
     * @param sourceTableName a forrástábla neve
     */
    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    /**
     * Visszaadja a tárolt measures értékét, amely a tárolt measure-ök listája.
     * Ez metaadat, measure neve és calculated measur esetében annak
     * kiszámításához használt formula
     *
     * @return a tárolt measure
     * @see hu.agnos.molap.measure.Measures
     */
    public Measures getMeasures() {
        return measures;
    }

    /**
     * A measure értékének beállítása
     *
     * @param measures beállítandó measures (measure-ok listája)
     * @see hu.agnos.molap.measure.Measures
     */
    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    /**
     * Visszaadja a measureHeadert, amely a mutatók egyedi nevét tartalmazó
     * tömb, a sorrendje kötött. Erre lekérdezési időben van szükség, tudni
     * kell, hogy a cell melyik oszlopa, melyik measure értékét reprezentálja.
     * Ez redundáns tárolás, a measures tömből kinyerhető (érteke onnan
     * származik), de a lekérdezések gyosabbá válnak általa.
     *
     * @return measureHeader
     */
    public String[] getMeasureHeader() {
        return measureHeader;
    }

    /**
     * Beállítja a két header tömböt
     */
    public void refreshHeader() {
        this.measureHeader = this.measures.getHeader();
        this.hierarchyHeader = this.generateHierarchiesnHeader();
    }

    /**
     * Visszaadja az összes hierarchia nevét egy tömbe rendezve. A sorrend
     * Kötött!!!!
     *
     * @return A hierarchia neveket tartalmazó, köttött sorendű tömb
     */
    private String[] generateHierarchiesnHeader() {
        int hierarchyCnt = 0;
        for (int i = 0; i < this.dimensions.size(); i++) {
            hierarchyCnt += this.dimensions.get(i).getHierarchyCnt();
        }

        String[] hierarchiesHeader = new String[hierarchyCnt];

        this.hierarchyIndex = new int[hierarchyCnt][];

        int idx = 0;
        for (int i = 0; i < this.dimensions.size(); i++) {
            Dimension dim = this.dimensions.get(i);
            for (int j = 0; j < dim.getHierarchyCnt(); j++) {
                hierarchiesHeader[idx] = dim.getHierarchyById(j).getHierarchyUniqueName();
                this.hierarchyIndex[idx] = new int[]{i, j};
                idx++;
            }
        }
        return hierarchiesHeader;
    }

    /**
     * Visszaadja a cellák tömbjét, amely egy mátrix
     *
     * @return a cellák mátrixa
     * @see hu.agnos.molap.measure.Cells
     */
    public Cells getCells() {
        return cells;
    }

    /**
     * Beállítja a cellák értékét
     *
     * @param cells a beállítandó cellák
     * @see hu.agnos.molap.measure.Cells
     */
    public void setCells(Cells cells) {
        this.cells = cells;
    }

    /**
     * Visszaadja a kocka nevét.
     *
     * @return a kocka neve
     */
    public String getName() {
        return name;
    }
}
