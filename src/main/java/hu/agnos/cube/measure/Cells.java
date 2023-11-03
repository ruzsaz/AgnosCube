package hu.agnos.cube.measure;

import lombok.*;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cells implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private float[][] cells; // Values in the cube; first index is the index of the value, second is the row-index

    /**
     * Constructs the cells
     *
     * @param columnCount number of the values
     * @param rowCount number of rows in the cube
     */
    public Cells(int columnCount, int rowCount) {
        this.cells = new float[columnCount][rowCount];
    }

    /**
     * Visszaadja egy konkrét mutató értéket
     *
     * @param rowIdx az adott mutató hányadik sora
     * @param columnIdx melyik mutatórol van szó
     * @return a kért mutató érték
     */
    public float getCell(int rowIdx, int columnIdx) {
        return cells[columnIdx][rowIdx];
    }

    /**
     * Visszaadje egy adott mutató (a mutató minden sorát / tárolt értékeit)
     *
     * @param columnIdx a mutató kockabéli indexe
     * @return a kért mutató
     */
    public float[] getColumn(int columnIdx) {
        return cells[columnIdx];
    }

    /**
     * Beállit egy adott mutató konkrét értékét
     *
     * @param rowIdx az adott mutató melyik sorát írjuk felül
     * @param columnIdx az adott mutató kockabéli indexe
     * @param value a beállítandó érték
     */
    public void setCell(int rowIdx, int columnIdx, float value) {
        cells[columnIdx][rowIdx] = value;
    }

    /**
     * Beállít egy adott mutatót
     *
     * @param columnIdx a beállítandó mutató kockabéli indexe
     * @param values a beállítandó mutató
     */
    public void setColumn(int columnIdx, float[] values) {
        cells[columnIdx] = values;
    }

}
