/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap.dimension;

/**
 * Ez az osztály a hierarchiában szereplő hierarchia szintek metaadatait írja le (pl.: Megye, kistérség vagy település).
 * @author parisek
 */
public class Level implements java.io.Serializable {

    private static final long serialVersionUID = -8940196742313994740L;
    
    /**
     * Az adott szint a hierarchia milyen mélységé áll
     */
    private final int depth;
    
    /**
     * Az adott szint egyedi neve
     */
    private final String name;
    
    /**
     * A sznthez tartozó azonosítót tartalamzó oszlop neve, szerepe a
     * cube-ról készült reportnál jelentkezik
     */
    private final String idColumnName;
    
    /**
     * A szinthez tartozó kódot tartalamzó oszlop neve, szerepe a
     * cube-ról készült reportnál jelentkezik
     */
    private final String codeColumnName;
    
    /**
     * A szinthez tartozó nevet tartalamzó oszlop neve, szerepe a
     * cube-ról készült reportnál jelentkezik
     */    
    private final String nameColumnName;

    /**
     * A Level konstruktora
     * @param name az adott hierarchia-szint neve
     * @param idColumnName az adott hierarchia-szint azonosítóját tartalmazó adatbázisbéli attribútum-név
     * @param codeColumnName az adott hierarchia-szint kódját tartalmazó adatbázisbéli attribútum-név
     * @param nameColumnName az adott hierarchia-szint nevét tartalmazó adatbázisbéli attribútum-név
     * @param depth az aktuális hierarchia-szint a hierarchia hányadik szintjét írja le.
     */
    public Level(String name, String idColumnName, String codeColumnName, String nameColumnName, int depth) {
        this.depth = depth;
        this.name = name;
        this.idColumnName = idColumnName;
        this.codeColumnName = codeColumnName;
        this.nameColumnName = nameColumnName;
    }

    /**
     * Visszaadja a hierarchia-szint hierarchián belüli szintjét.
     * @return az adott Level mélysége
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Visszaadja az adott hierarchia-szint azonosítóját tartalmazó adatbázisbéli attribútum-névet
     * @return az adott hierarchia-szint azonosítóját tartalmazó adatbázisbéli attribútum -név
     */
    public String getIdColumnName() {
        return idColumnName;
    }
    
    /**
     * Visszaadja az adott hierarchia-szint kódját tartalmazó adatbázisbéli attribútum-névet
     * @return az adott hierarchia-szint kódját tartalmazó adatbázisbéli attribútum -név
     */
    public String getCodeColumnName() {
        return codeColumnName;
    }

    /**
     * Visszaadja az adott hierarchia-szint nevét tartalmazó adatbázisbéli attribútum-névet
     * @return az adott hierarchia-szint nevét tartalmazó adatbázisbéli attribútum -név
     */
    public String getNameColumnName() {
        return nameColumnName;
    }

    /**
     * Visszaadja az adott hierarchia-szint nevét
     * @return az adott hierarchia-szint neve
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ": " + idColumnName + ", " + codeColumnName + ", " + nameColumnName + ", depth=" + depth;
    }

}
