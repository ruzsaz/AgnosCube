/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap.dimension;

import java.util.ArrayList;
import java.util.List;

/**
 * Ez az osztály egy adott dimenzió hierarchájának írja le (pl. Idő dimenziő
 * üzleti év hierarchia). Tartalma egyrész meta-adat (levels), másrészt
 * tényleges adat(nodes).
 *
 * @author parisek
 */
public class Hierarchy implements java.io.Serializable {

    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * A hierarchia szintek tára
     */
    private final List<Level> levels;

    /**
     * A hierarchia szintek konkrét előfordulásia, a legfinomabb granuralitási
     * szintű elemek kivételével
     */
    private Node[][] nodes;

    /**
     * A hierarchia egyedi neve
     */
    private final String hierarchyUniqueName;

    /**
     * A hierarchia szintjeinek száma (ebbe a base level szint is benne van)
     */
    private int maxDepth;

    /**
     * Ezen hierarchia szerint partícionálva van-e a kocka
     */
    private final boolean isPartitioned;

    /**
     * A hierarchy konstruktora
     *
     * @param hierarchyUniqueName a hierarchia kocka szinten egyedi neve
     * @param isPartitioned a hierarchia particionált-e
     */
    public Hierarchy(String hierarchyUniqueName, boolean isPartitioned) {
        this.levels = new ArrayList<>();
        Level root = new Level("All", "", "", "", 0);
        this.levels.add(root);
        this.hierarchyUniqueName = hierarchyUniqueName;
        this.maxDepth = 0;
        this.isPartitioned = isPartitioned;
    }

    /**
     * A Node-okat tartalmazo vektor inicializálásáa, legelső eleme a Root (0,
     * "All", "All")
     */
    public void initNodeArray() {
        this.nodes = new Node[this.maxDepth][];
        Node root = new Node(0, "All", "All");
        this.nodes[0] = new Node[]{root};
    }

    /**
     * Megadja, hogy a kocka particionált-e a hierarchia szerint.
     *
     * @return true ha van partícionálás eszerint a dimenzió szerint, ellenben
     * false
     */
    public boolean isPartitioned() {
        return this.isPartitioned;
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
     * @param depth a beszúrás hjerarchia-szintja
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
//        System.out.println("hierName: "+this.hierarchyUniqueName);
//        System.out.println("node length: " + this.nodes.length);
//        System.out.println("depth: "+depth+", id: "+id);

        return this.nodes[depth][id];
    }

    /**
     * Visszaadja a hierarchia maximális mélységét
     *
     * @return maximális mélység
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Visszaadja a hierarchia egyedi nevét
     *
     * @return a hierarchia egyedi neve
     */
    public String getHierarchyUniqueName() {
        return hierarchyUniqueName;
    }

    /**
     * Visszaadja a Level-ek vektorát
     *
     * @return Level-ek vektora
     * @see hu.agnos.molap.dimension.Level
     */
    public List<Level> getLevels() {
        return levels;
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

        int entityDepth = entity.getDepth();
        if (entityDepth > this.maxDepth) {
            this.maxDepth = entityDepth;
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
     * A paraméterül kapott csomopontok mátrixát eltárolja
     *
     * @param nodes a beállítandó csomópontok mátrixa
     * @see hu.agnos.molap.dimension.Node
     */
    public void setNodes(Node[][] nodes) {
        this.nodes = nodes;
    }

    /**
     * Visszaadja a csomópontok mátrixát
     *
     * @return a csomópontok mátrixa
     * @see hu.agnos.molap.dimension.Node
     */
    public Node[][] getNodes() {
        return nodes;
    }

    /**
     * Visszaadja, hogy a paraméteréül kapott névvelmegegyező Level-et
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
     * kiírja a hierarchia főbb adatait
     */
    public void printer() {
        System.out.println(this.hierarchyUniqueName);
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
