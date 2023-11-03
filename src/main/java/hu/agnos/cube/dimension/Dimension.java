package hu.agnos.cube.dimension;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describes a dimension in the cube. It contains some meta-info about the dimension, the levels, and all the dimension
 * values (called nodes) as an array of elements in every level.
 */
@Getter
@Setter
@ToString
public class Dimension implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;
    private final String name;
    private final List<Level> levels;
    private final boolean isOfflineCalculated;  // True if the dimension is offline calculated in the cube
    private Node[][] nodes; // Dimension values by levels. First index is the level, second is the index of the node (dimension value).
    private transient Map<String, Node> lookupTable;

    public Dimension(String name, boolean isOfflineCalculated) {
        this.levels = new ArrayList<>(3);
        Level root = new Level(0, "All");
        levels.add(root);
        this.name = name;
        this.isOfflineCalculated = isOfflineCalculated;
    }

    /**
     * Initializes the lookup table, to make drill-path to node conversion faster. Should be run once, right after the
     * cube is loaded.
     */
    public void initLookupTable() {
        resetRootNode();
        this.lookupTable = new HashMap<>(10);
        addSelfAndAllChildrenToLookupTable(null, nodes[0][0], 0);
    }

    /**
     * Sets the root node's code to "" as required. Should be run once, after the cube is loaded, to be sure the root
     * node's code is as required.
     */
    private void resetRootNode() {
        nodes[0][0].setCode("");
    }

    /**
     * Recursive method to insert a node, and all children to the lookup table.
     *
     * @param parentKey The node's parent key, like "2013,06"
     * @param node The node to insert with all its children
     * @param nodeLevel The node's level (0: root, etc...)
     */
    private void addSelfAndAllChildrenToLookupTable(String parentKey, Node node, int nodeLevel) {
        String selfKey = (parentKey == null || parentKey.isEmpty()) ? node.getCode() : parentKey + "," + node.getCode();
        lookupTable.put(selfKey, node);
        if (!node.isLeaf()) {
            int[] childrenIds = node.getChildrenId();
            for (int childId : childrenIds) {
                Node childNode = nodes[nodeLevel + 1][childId];
                addSelfAndAllChildrenToLookupTable(selfKey, childNode, nodeLevel + 1);
            }
        }
    }

    public Node getRoot() {
        return nodes[0][0];
    }

    /**
     * Az adott hierarchia-szintre beszúrja akapott csomopontok vektorát
     *
     * @param depth a beszúrás hierarchia-szintje
     * @param nodeRow a beszúrandó csomópontok vektora
     * @see hu.agnos.cube.dimension.Node
     */
    public void setNodes(int depth, Node[] nodeRow) {
        nodes[depth] = nodeRow;
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
        return nodes[depth][id];
    }

    public Node getNodeByKnownIdPath(String path) {
        return lookupTable.get(path);
    }

    /**
     * Visszaadja a hierarchia maximális mélységét
     *
     * @return maximális mélység, 0: root level, stb...
     */
    public int getMaxDepth() {
        return levels.size() - 1;
    }

    /**
     * A megfelelő index-re beszúrja a paraméterül kapott Level-et
     *
     * @param entity a beszúrandó Level
     * @see hu.agnos.cube.dimension.Level
     */
    public void addLevel(Level entity) {
        int idx = getInsertIdxOfNewLevel(entity);
        if (idx == levels.size()) {
            levels.add(entity);
        } else {
            levels.add(idx, entity);
        }

    }

    /**
     * Ez visszaadja az új elem beszúrási indexét. Ezt arra használjuk, hogy a hierarchiában a szintek rendezetten
     * tárolódhassanak.
     *
     * @param entity a beszúrandó Level
     * @return az új elem beszúrásának indexe
     * @see hu.agnos.cube.dimension.Level
     */
    private int getInsertIdxOfNewLevel(Level entity) {
        int result = -1;
        for (int i = 0; i < levels.size(); i++) {
            if (levels.get(i).getDepth() > entity.getDepth()) {
                result = i;
                break;
            }
        }
        if (result == -1) {
            result = levels.size();
        }
        return result;
    }

    /**
     * Visszaadja, hogy a paraméteréül kapott névvel megegyező Level-t tartalmazza-e.
     *
     * @param levelName A keresett Level neve
     * @return true ha tartalmaz ilyen nevű levelet, ellenben false
     * @see hu.agnos.cube.dimension.Level
     */
    public boolean hasLevel(String levelName) {
        boolean result = false;
        for (Level level : levels) {
            if (level.getName().equals(levelName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Visszaadja a megcímzett csomópontot. A címzés a hierarchiaindex és egy "baseVector" szegmenssel történik.
     *
     * @param path a DrillVector egy része (":" szeparált részegység )
     * @return a megcímzett elem / node /member, vagy null, ha a path nem valós elemet címez
     * @see hu.agnos.cube.dimension.Node
     */
    public Node getNode(String path) {
        String[] levelIds = path.split(",", -1);
        if (levelIds[0].isEmpty()) {
            return (getRoot());
        }
        int queryLength = levelIds.length;
        int idx = Integer.parseInt(levelIds[queryLength - 1]);
        return (getNode(levelIds.length, idx));
    }

    public Node[] getChildrenOf(Node node) {
        int numberOfChildren = node.getChildrenId().length;
        Node[] children = new Node[numberOfChildren];
        int nodeLevel = node.getLevel();
        for (int i = 0; i < numberOfChildren; i++) {
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
                for (int i = 0; i < value.getLevel(); i++) {
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
