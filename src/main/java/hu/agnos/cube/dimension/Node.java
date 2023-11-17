package hu.agnos.cube.dimension;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Ez az osztály egy Level osztály adott csomopontját reprezentálja. Nem konkrét értékeket, hanem metaadatokat és az
 * adott csomopont konkrét értékének meghatározásához szükséges információkat tárolunk itt. Viszont a Level osztálytól
 * eltérően, ez már nem a struktúrát írja le (Megye kistérség település), hanem e struktúrában lévő elemeket (pl.
 * Hajdú-Bihar vagy Berettyóújfalu). Metaadat a csomopont azonosítója, kódja és neve, még egyéb információ a csomópont
 * által érintett intervallumok alsó és felső indexei, a csomopont szülei és a gyerekei.
 *
 * @author parisek
 */
@Getter
@Setter
@ToString
public class Node implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;

    private final Integer id;   // Id, unique within the level only, auto-created
    private final String name;  // Name to show in the reports
    private String code;  // Known id, or business id of the element, as read from the database; must be unique within the level
    private transient String dataAsString;  // id+code+name, as a json string, just for faster lookups

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
    private int parentId;       // Id of the node's parent
    private int[] childrenId;   // Ids of the node's children

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
        this.intervalsUpperIndexes = null;
        this.childrenId = null;
    }

    /**
     * A Node konstruktora.
     *
     * @param id a csomópont egyedi azonosítója
     * @param code a csomópont kódja
     * @param name a csomópont neve
     * @param lowerIndexes a csomópont által érintett kockabéli intervallumok alsó indexeinek vektora
     * @param upperIndexes a csomópont által érintett kockabéli intervallumok felső indexeinek vektora
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
        return (childrenId == null || childrenId.length == 0);
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        this.dataAsString = "{\"id\":\"" + id + "\",\"knownId\":\"" + code + "\",\"name\":\"" + name + "\"}";
    }

    public List<Integer> kecskeGida() {
        List<Integer> collector = new ArrayList<>();
        for (int index = 0; index < intervalsLowerIndexes.length; index++) {
            for (int i = intervalsLowerIndexes[index]; i < intervalsUpperIndexes[index] + 1; i++) {
                collector.add(i);
            }
        }
        return collector;
    }

}
