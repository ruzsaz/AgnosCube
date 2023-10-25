/*
 * Itt tárolodnak a measure értékek.
 *  A tömb első indexe a measure oszlop indexe, míg a 2. index megmodja, hogy hányadik sor kell
 */
package hu.agnos.molap.measure;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author parisek
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cells implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * A tartalmazott mutatok tömbje. Első index a mutató sorszáma, a második
     * a mutatón belüli sorszám.
     */
    float[][] cells;

    /**
     * A Cells konstruktora
     *
     * @param columnCnt a mutatók száma
     * @param rowCnt a mutatók sorainak száma
     */
    public Cells(int columnCnt, int rowCnt) {
        this.cells = new float[columnCnt][rowCnt];
    }

    /**
     * Visszaadja egy konkrét mutató értéket
     *
     * @param rowIdx az adott mutató hányadik sora
     * @param columnIdx melyik mutatórol van szó
     * @return a kért mutató érték
     */
    public float getCell(int rowIdx, int columnIdx) {
        return this.cells[columnIdx][rowIdx];
    }

    /**
     * Visszaadje egy adott mutató (a mutató minden sorát / tárolt értékeit)
     *
     * @param columnIdx a mutató kockabéli indexe
     * @return a kért mutató
     */
    public float[] getColumn(int columnIdx) {
        return this.cells[columnIdx];
    }

    /**
     * Beállit egy adott mutató konkrét értékét
     *
     * @param rowIdx az adott mutató melyik sorát írjuk felül
     * @param columnIdx az adott mutató kockabéli indexe
     * @param value a beállítandó érték
     */
    public void setCell(int rowIdx, int columnIdx, float value) {
        this.cells[columnIdx][rowIdx] = value;
    }

    /**
     * Beállít egy adott mutatót
     *
     * @param columnIdx a beállítandó mutató kockabéli indexe
     * @param values a beállítandó mutató
     */
    public void setColumn(int columnIdx, float[] values) {
        this.cells[columnIdx] = values;
    }

}
