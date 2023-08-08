/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap.dimension;

/**
 * Ez az osztály egy Level osztály adott csomopontját reprezentálja. Nem konkrét
 * értékeket, hanem metaadatokat és az adott csomopont konkrét értékének
 * meghatározásához szükséges információkat tárolunk itt. Viszont a Level
 * osztálytól eltérően, ez már nem a struktúrát írja le (Megye kistérség
 * település), hanem e struktúrában lévő elemeket (pl. Hajdú-Bihar vagy
 * Berettyóújfalu). Metaadat a csomopont azonosítója, kódja és neve, még egyéb
 * informéció a csomópont által érintett intervallumok alsó és felső indexei, a
 * csomopont szülei és a gyerekei.
 *
 * @author parisek
 */
public class Node implements java.io.Serializable {

    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * A node érték azonosítója.
     */
    private final Integer id;

    /**
     * A node érték kódja.
     */
    private final String code;

    /**
     * A node érték neve.
     */
    private final String name;

    /**
     * A node-érték szöveges formálya. Azért célszerű ezt letárolni, mert ez
     * off-line időben meghatározható, és így a lekérdezési idő szignifikánsan
     * csökkenthető.
     */
    private final String dataAsString;

    /**
     * A node által érintett Kockabéli intervallumok alsó indexei
     */
    private int[] intervalsLowerIndexes;

    /**
     * A node által érintett Kockabéli intervallumok felső indexei
     */
    private int[] intervalsUpperIndexes;

    /**
     * a szülő node azonosítója
     */
    private int parentId;

    /**
     * a gyerek node-ok azonosítói
     */
    private int[] childrenId;

    /**
     * A Node konstruktora.
     *
     * @param id a csomópont egyedi azonosítója
     * @param code a csomópont kódja
     * @param name a csomópont neve
     */
    public Node(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"id\":\"").append(id);
        sb.append("\",\"knownId\":\"").append(code);
        sb.append("\",\"name\":\"").append(name).append("\"}");
        this.dataAsString = sb.toString();
        this.intervalsUpperIndexes = null;
//        this.aggregatedChildId = -1;
        this.childrenId = null;
    }

    /**
     *
     * A Node konstruktora.
     *
     * @param id a csomópont egyedi azonosítója
     * @param code a csomópont kódja
     * @param name a csomópont neve
     * @param lowerIndexes a csomópont által érintett kockabéli intervallumok
     * alsó indexeinek vektora
     * @param upperIndexes a csomópont által érintett kockabéli intervallumok
     * felső indexeinek vektora
     * @param parentId a csomópont szülöjének azonosítója
     * @param childrenId a csomópont gyerekeinek azonosítóit tartalmazó vektor
     */
    public Node(Integer id, String code, String name, int[] lowerIndexes, int[] upperIndexes, int parentId, int[] childrenId) {
        this.id = id;
        this.code = code;
        this.name = name;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"id\":\"").append(id);
        sb.append("\",\"knownId\":\"").append(code);
        sb.append("\",\"name\":\"").append(name).append("\"}");
        this.dataAsString = sb.toString();
        this.intervalsLowerIndexes = lowerIndexes;
        this.intervalsUpperIndexes = upperIndexes;
        this.parentId = parentId;
        this.childrenId = childrenId;
//        this.aggregatedChildId = aggregatedChildId;
    }

    /**
     * Visszaadja a node által érintett Kockabéli intervallumok alsó indexeit
     *
     * @return az érintett kockabéli intervallumok alsó indexei
     */
    public int[] getIntervalsLowerIndexes() {
        return intervalsLowerIndexes;
    }

    /**
     * Beállítja a node által érintett kockabéli intervallumok alsó indexeit
     *
     * @param intervalsLowerIndexes a beállítandó intervallumok alsó indexei
     */
    public void setIntervalsLowerIndexes(int[] intervalsLowerIndexes) {
        this.intervalsLowerIndexes = intervalsLowerIndexes;
    }

    /**
     * Visszaadja a node által érintett kockabéli intervallumok felső indexeit
     *
     * @return az érintett Kockabéli intervallumok felső indexei
     */
    public int[] getIntervalsUpperIndexes() {
        return intervalsUpperIndexes;
    }

    /**
     * Beállítja a node által érintett kockabéli intervallumok felső indexeit
     *
     * @param intervalsUpperIndexes a beállítandó intervallumok felső indexei
     */
    public void setIntervalsUpperIndexes(int[] intervalsUpperIndexes) {
        this.intervalsUpperIndexes = intervalsUpperIndexes;
    }

    /**
     * Visszaadja a csomópont egyedi azonosítóját
     *
     * @return a csomópont egyedi azonosítója
     */
    public Integer getId() {
        return id;
    }

    /**
     * Visszaadja a csomópont kódját
     *
     * @return a csomópont kódja
     */
    public String getCode() {
        return code;
    }

    /**
     * Visszaadja a csomópont neve
     *
     * @return a csomópont neve
     */
    public String getName() {
        return name;
    }

    /**
     * Visszaadja a csomópont azonosítóját, kódját és nevét egy formázott
     * sztingben
     *
     * @return a csomópont metaadatai egy formázott sztingben
     */
    public String getDataAsString() {
        return dataAsString;
    }

    /**
     * Visszaadja a csomopont szülejének azonosítóját. Ha nincs szülő, akkor ez
     * az érték -1
     *
     * @return a szülő azonosítója
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * Beállítja a csomopont szülejének azonosítóját
     *
     * @param parentId a szülő azonosítója
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    /**
     * Visszaadja a gyerekek azonosítóinak vektorát
     *
     * @return a gyerekek azonosítóinak vektora
     */
    public int[] getChildrenId() {
        return childrenId;
    }

    /**
     * Beállítja a gyerekek azonosítóinak vektorát
     *
     * @param childrenId a gyerekek azonosítóinak vektorát
     */
    public void setChildrenId(int[] childrenId) {
        this.childrenId = childrenId;
    }

    /**
     * Megmodnja, hogy a csomópont levélelem-e a hierarchián belül vagy sem
     *
     * @return igyaz ha levélelem, különben hamis
     */
    public boolean isLeaf() {
        if (this.childrenId == null) {
            return true;
        }
        return !(this.childrenId.length > 0);
    }

    @Override
    public String toString() {
        return "Node{" + ", dataAsString=" + dataAsString
                + ", intervalsLowerIndexes.length=" + intervalsLowerIndexes.length
                + ", intervalsUpperIndexes.length=" + intervalsUpperIndexes.length
                + ", parentId=" + parentId
                + ", childrenId.length=" + childrenId.length
                //                + ", aggregatedChildId=" + aggregatedChildId 
                + '}';
    }

    /**
     * A csomopont által érintett intervallumok alsó és felső értékeinek
     * kiíratása.
     */
    public void printIntervals() {
        System.out.println("intervalsLowerIndexes.length: " + intervalsLowerIndexes.length);
        for (int i = 0; i < this.intervalsLowerIndexes.length; i++) {
            System.out.print(this.intervalsLowerIndexes[i] + " - ");
            System.out.println(this.intervalsUpperIndexes[i]);

        }
    }

}
