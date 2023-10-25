package hu.agnos.cube.dimension;

import java.io.Serial;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Ez az osztály egy Level osztály adott csomopontját reprezentálja. Nem konkrét
 * értékeket, hanem metaadatokat és az adott csomopont konkrét értékének
 * meghatározásához szükséges információkat tárolunk itt. Viszont a Level
 * osztálytól eltérően, ez már nem a struktúrát írja le (Megye kistérség
 * település), hanem e struktúrában lévő elemeket (pl. Hajdú-Bihar vagy
 * Berettyóújfalu). Metaadat a csomopont azonosítója, kódja és neve, még egyéb
 * információ a csomópont által érintett intervallumok alsó és felső indexei, a
 * csomopont szülei és a gyerekei.
 *
 * @author parisek
 */
@Getter
@Setter
@ToString
public class Node implements java.io.Serializable {

    @Serial
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
     * A node-érték szöveges formája. Azért célszerű ezt letárolni, mert ez
     * off-line időben meghatározható, és így a lekérdezési idő csökkenthető.
     */    
    private transient String dataAsString;

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

    private int level;

    /**
     * A Node konstruktora.
     *
     * @param id a csomópont egyedi azonosítója
     * @param code a csomópont kódja
     * @param name a csomópont neve
     */
    public Node(Integer id, String code, String name, int level) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.level = level;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"id\":\"").append(id);
        sb.append("\",\"knownId\":\"").append(code);
        sb.append("\",\"name\":\"").append(name).append("\"}");
        this.dataAsString = sb.toString();
        this.intervalsUpperIndexes = null;
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
    public Node(Integer id, String code, String name, int level, int[] lowerIndexes, int[] upperIndexes, int parentId, int[] childrenId) {
        this(id, code, name, level);
        this.intervalsLowerIndexes = lowerIndexes;
        this.intervalsUpperIndexes = upperIndexes;
        this.parentId = parentId;
        this.childrenId = childrenId;
    }


    /**
     * Megmodnja, hogy a csomópont levélelem-e a hierarchián belül vagy sem
     *
     * @return igyaz ha levélelem, különben hamis
     */
    public boolean isLeaf() {
        return (this.childrenId == null || this.childrenId.length == 0);
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
