package hu.agnos.cube.dimension;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Describes a dimension in the cube. It contains some meta-info about the dimension, the levels, and
 * all the dimension values (called nodes) as an array of elements in every level.
 */
@Getter
@Setter
@ToString
public class Dimension implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;
    private final String name;
    private final List<Level> levels;
    private Node[][] nodes; // Dimension values by levels. First index is the level, second is the index of the node (dimension value).
    private final boolean isOfflineCalculated;  // True if the dimension is offline calculated in the cube
    private transient Map<String, Node> lookupTable;

    public Dimension(String name, boolean isOfflineCalculated) {
        this.levels = new ArrayList<>();
        Level root = new Level(0, "All");
        this.levels.add(root);
        this.name = name;
        this.isOfflineCalculated = isOfflineCalculated;
    }

    public void initLookupTable() {
        resetRootNode();
        this.lookupTable = new HashMap<>();
        addSelfAndAllChildrenToLookupTable(null, nodes[0][0], 0);
    }

    public void resetRootNode() {
        nodes[0][0].setCode("");
    }

    private void addSelfAndAllChildrenToLookupTable(String parentKey, Node node, int nodeLevel) {
        String selfKey = (parentKey == null || parentKey.isEmpty()) ? node.getCode() : parentKey + "," + node.getCode();
        lookupTable.put(selfKey, node);
        if (! node.isLeaf()) {
            int[] childrenIds = node.getChildrenId();
            for (int childId : childrenIds) {
                Node childNode = nodes[nodeLevel + 1][childId];
                addSelfAndAllChildrenToLookupTable(selfKey, childNode, nodeLevel + 1);
            }
        }
    }

    /**
     * Visszaadja a Root csomópontot
     *
     * @return Root csomópont
     * @see hu.agnos.cube.dimension.Node
     */
    public Node getRoot() {
        return this.nodes[0][0];
    }

    /**
     * Az adott hierarchia-szintre beszúrja akapott csomopontok vektorát
     *
     * @param depth a beszúrás hierarchia-szintje
     * @param nodeRow a beszúrandó csomópontok vektora
     * @see hu.agnos.cube.dimension.Node
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
     * @see hu.agnos.cube.dimension.Node
     */
    public Node getNode(int depth, int id) {
        return this.nodes[depth][id];
    }

    public Node getNodeByKnownIdPath(String path) {
        return lookupTable.get(path);
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
     * @see hu.agnos.cube.dimension.Level
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
     * @see hu.agnos.cube.dimension.Level
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
     * @see hu.agnos.cube.dimension.Level
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
     * @see hu.agnos.cube.dimension.Node
     */
    public Node getNode(String path) {
        String[] levelIds = path.split(",", -1);
        if (levelIds[0].isEmpty()) {
            return(getRoot());
        }
        int queryLength = levelIds.length;
        int idx = Integer.parseInt(levelIds[queryLength - 1]);
        return(getNode(levelIds.length, idx));
    }

    public Node[] getChildrenOf(Node node) {
        Node[] children = new Node[node.getChildrenId().length];
        int nodeLevel = node.getLevel();
        for (int i = 0; i < node.getChildrenId().length; i++) {
            int childId = node.getChildrenId()[i];
            children[i] = getNode(nodeLevel + 1, childId);
        }
        return children;
    }

    public void printForDebug() {
        System.out.println(this.name);
        System.out.println("Nodes: ");
        for (Node[] node : nodes) {
            for (Node value : node) {
                for (int c = 0; c < value.getLevel(); c++) {
                    System.out.print("\t");
                }
                System.out.println(value);
            }
        }

        System.out.println("LookupTable:");
        for (Map.Entry<String, Node> e : lookupTable.entrySet()) {
            System.out.println("    '" + e.getKey() + "' -> Node " + e.getValue().getCode() + ":" + e.getValue().getName());
        }
    }

}
