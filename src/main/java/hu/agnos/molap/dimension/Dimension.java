package hu.agnos.molap.dimension;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Ez az osztály egy adott dimenzió írja le . Tartalma egyrész meta-adat (levels), másrészt
 * tényleges adat(nodes).
 *
 * @author parisek
 */
@Getter
@Setter
@ToString
public class Dimension implements java.io.Serializable {

    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * A dimenzió szintek tára
     */
    private final List<Level> levels;

    /**
     * A dimenzió szintek konkrét előfordulásia, a legfinomabb granuralitási
     * szintű elemek kivételével
     */
    private Node[][] nodes;

    /**
     * A dimenzió egyedi neve
     */
    private final String dimensionUniqueName;

//    /**
//     * A dimenzió szintjeinek száma (+ 1 az All level)
//     */
//    private int levelCount;

    /**
     * Ezen diemenzió szerint partícionálva van-e a kocka
     */
    private final boolean isOfflineCalculated;

    /**
     * A hierarchy konstruktora
     *
     * @param dimensionUniqueName a hierarchia kocka szinten egyedi neve
     * @param isOfflineCalculated a hierarchia particionált-e
     */
    public Dimension(String dimensionUniqueName, boolean isOfflineCalculated) {
        this.levels = new ArrayList<>();
        Level root = new Level(0, "All");
        this.levels.add(root);
        this.dimensionUniqueName = dimensionUniqueName;
        this.isOfflineCalculated = isOfflineCalculated;
    }

    /**
     * A Node-okat tartalmazo vektor inicializálásáa, legelső eleme a Root (0,
     * "All", "All")
     */
    public void initNodeArray(int levelCount) {
        this.nodes = new Node[levelCount][];
        Node root = new Node(0, "All", "All");
        this.nodes[0] = new Node[]{root};
    }

    
    /**
     * Visszaadja a Root csomópontot
     *
     * @return Root csomópont
     * @see hu.agnos.molap.dimension.Node
     */
    public Node getRoot() {
        return this.nodes[0][0];
    }

    /**
     * Az adott hierarchia-szintre beszúrja akapott csomopontok vektorát
     *
     * @param depth a beszúrás hierarchia-szintje
     * @param nodeRow a beszúrandó csomópontok vektora
     * @see hu.agnos.molap.dimension.Node
     */
    public void setNodes(int depth, Node[] nodeRow) {
        this.nodes[depth] = nodeRow;
    }

    /**
     * Visszaadja egy adott hierarchiaszint adott indexű csomópontját
     *
     * @param depth a keresett hierarchia-szint
     * @param id a keresett csomópont hierarchia-szintbéli sorszáma
     * @return a keresett csomópont
     * @see hu.agnos.molap.dimension.Node
     */
    public Node getNode(int depth, int id) {
        return this.nodes[depth][id];
    }

    /**
     * Find a node on a given level by its code (known id).
     *
     * @param depth Depth to look for the code
     * @param code Code to look for
     * @return The selected node, or null if not exists
     */
    public Node getNodeByKnownId(int depth, String code) {
        for (Node n : this.nodes[depth]) {
            if (n.getCode().equals(code)) {
                return n;
            }
        }
        return null;
    }

    /**
     * Visszaadja a hierarchia maximális mélységét
     *
     * @return maximális mélység
     */
    public int getLevelCount() {
        return this.levels.size();
    }

    
    /**
     * A megfelelő index-re beszúrja a paraméterül kapott Level-et
     *
     * @param entity a beszúrandó Level
     * @see hu.agnos.molap.dimension.Level
     */
    public void addLevel(Level entity) {
        int idx = getInsertIdxOfNewLevel(entity);
        if (idx == this.levels.size()) {
            this.levels.add(entity);
        } else {
            this.levels.add(idx, entity);
        }

    }

    /**
     * Ez visszaadja az új elem beszúrási indexét. Ezt arra használjuk, hogy a
     * hierarchiában a szintek rendezetten tárolódhassanak.
     *
     * @param entity a beszúrandó Level
     * @return az új elem beszúrásának indexe
     * @see hu.agnos.molap.dimension.Level
     */
    private int getInsertIdxOfNewLevel(Level entity) {
        int result = -1;
        for (int i = 0; i < this.levels.size(); i++) {
            if (this.levels.get(i).getDepth() > entity.getDepth()) {
                result = i;
                break;
            }
        }
        if (result == -1) {
            result = this.levels.size();
        }
        return result;
    }

    
    /**
     * Visszaadja, hogy a paraméteréül kapott névvel megegyező Level-t
     * tartalmazza-e.
     *
     * @param levelName A keresett Level neve
     * @return true ha tartalmaz ilyen nevű levelet, ellenben false
     * @see hu.agnos.molap.dimension.Level
     */
    public boolean hasLevel(String levelName) {
        boolean result = false;
        for (Level level : this.levels) {
            if (level.getName().equals(levelName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Visszaadja a megcímzett csomópontot. A címzés a hierarchiaindex és egy
     * "baseVector" szegmenssel történik.
     *
     * @param path a DrillVector egy része (":" szeparált részegység )
     * @return a megcímzett elem / node /member, vagy null, ha a path nem valós
     * elemet címez
     * @see hu.agnos.molap.dimension.Node
     */
    public Node getNode(String path) {
        Node result = null;
        String[] levelIds = path.split(",");

        if (levelIds[0].isEmpty()) {
            result = getRoot();
        } else {
            int queryLength = levelIds.length;

            // a path utolsó indexe a kereset elem id-ja
            int idx = Integer.parseInt(levelIds[queryLength - 1]);

            result = getNode(queryLength, idx);
        }

        return result;

    }

    // TODO: kiszervezni egy előre legyártott hashmap-be
    public Node getNodeByKnowIdPath(String path) {
        String[] levelIds = path.split(",");

        if (levelIds[0].isEmpty()) {
            return getRoot();
        } else {
            int queryLength = levelIds.length;

            // a path utolsó indexe a kereset elem knownId-ja
            String knownId = levelIds[queryLength - 1];

            if (this.levels.size() >= queryLength) { 
                return getNodeByKnownId(queryLength, knownId);
            }
            return null;
        }
    }

    /**
     * kiírja a hierarchia főbb adatait
     */
    public void printer() {
        System.out.println(this.dimensionUniqueName);
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                for (int c = 0; c < j; c++) {
                    System.out.print("\t");
                }
                System.out.println(nodes[i][j]);
            }

        }
    }

}
