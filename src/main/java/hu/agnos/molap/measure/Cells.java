/*
 * Itt tárolodnak a measure értékek.
 *  A tömb első indexe a mesaser oszlop indexe, míg a 2. index megmodja, hogy hányadik sor kell
 */
package hu.agnos.molap.measure;

/**
 *
 * @author parisek
 */
public class Cells implements java.io.Serializable {
    
    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * A tartalmazott mutatok tömbje. Első index a mutatók száma, míg a második index a mutatók sorainak száma.
     */
    double [] [] cells;

    /**
     * A Cells konstruktora
     */
    public Cells() {
    }
       
    /**
     * A Cells konstruktora
     * @param columnCnt a tartalmazott mutatók száma
     * @param rowCnt a tartalmazott mutatok sorainak száma
     */
    public Cells( int columnCnt, int rowCnt) {
        this.cells = new double[columnCnt] [rowCnt];
    }

    /**
     * A Cells konstruktora
     * @param cells a tartalmazott mutatók tömbje
     */
    public Cells(double[][] cells) {
        this.cells = cells;
    }
    
    /**
     * Visszaadja egy konkrét mutató értéket
     * @param rowIdx az adott mutató hányadik sora
     * @param columnIdx melyik mutatórol van szó
     * @return a kért mutató érték
     */
    public double getCell (int rowIdx, int columnIdx) {
        return this.cells[columnIdx] [rowIdx];
    }
    
    /**
     * Visszaadje egy adott mutató (a mutató minden sorát / tárolt értékeit)
     * @param columnIdx a mutató kockabéli indexe
     * @return a kért mutató
     */
    public double[] getColumn (int columnIdx) {
        return this.cells[columnIdx];
    }

    /**
     * Beállit egy adott mutató konkrét értékét
     * @param rowIdx az adott mutató melyik sorát írjuk felül
     * @param columnIdx az adott mutató kockabéli indexe
     * @param value a beállítandó érték
     */
    public void setCell(int rowIdx, int columnIdx, double value) {
        this.cells[columnIdx] [rowIdx] = value;
    }
    
    /**
     * Beállít egy adott mutatót
     * @param columnIdx a beállítandó mutató kockabéli indexe
     * @param values a beállítandó mutató
     */
    public void setColumn(int columnIdx, double[] values) {
        this.cells[columnIdx] = values;
    }

    /**
     * Visszaadjaa teljes Cells-t, azaz a mutatók tömbjét
     * @return a mutatók tömbje
     */
    public double[][] getCells() {
        return cells;
    }

    /**
     * Beállítja a Cells-t, azaz a mutatók tömbjét
     * @param cells a beálllítandó Cells
     */
    public void setCells(double[][] cells) {
        this.cells = cells;
    } 
    
}
