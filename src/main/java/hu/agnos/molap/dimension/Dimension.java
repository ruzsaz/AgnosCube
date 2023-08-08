package hu.agnos.molap.dimension;

/**
 * Ez az osztály egy kockán belüli dimenziót reprezentál (pl.: Idő). Egy
 * dimenzió több hierarchiát is tartalmazhat (pl. üzleti év, gregorián, stb)
 *
 * @author parisek
 */
public class Dimension implements java.io.Serializable {

    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * A dimenzió kockán belüli egyedi neve
     */
    private final String uniqueName;

    /**
     * mivel egy dimenzióban egynél több hierarchia is lehet, így a
     * hierarchiákat tömben tároljuk
     */
    private final Hierarchy[] hierarchies;

    /**
     * a dimenzióban lévő hierarchiák száma
     */
    private final int hierarchyCnt;

    /**
     * a dimenzió azon nodjainak tömbje, amelyek a dimenzió legfinomabb
     * granuralitási szintjét reprezentálják. Mivel az egy dimenzióban szereplő
     * külömböző hierarchiáknak legfinomabb granuralitási szinten meg kell
     * egyezniük, így ezeket felesleges lenne többször tárolni
     */
    private Node[] baseLevelNodes;

    /**
     * A dimenzió konstruktora.
     *
     * @param uniqueName a dimenzió kockán belüli egyedi neve
     * @param hierarchyCnt a dimenzióban található hierarchiák száma
     * @see hu.agnos.molap.dimension.Hierarchy
     */
    public Dimension(String uniqueName, int hierarchyCnt) {
        this.hierarchyCnt = hierarchyCnt;
        this.hierarchies = new Hierarchy[hierarchyCnt];
        this.uniqueName = uniqueName;
    }

    /**
     * A paraméteréül kapott hierarchiát beszúrja a megadott index-re
     *
     * @param hierarchyIdx a beszúrás helye
     * @param hier a beszúrandó hierarchia
     * @see hu.agnos.molap.dimension.Hierarchy
     */
    public void addHierarchy(int hierarchyIdx, Hierarchy hier) {
        this.hierarchies[hierarchyIdx] = hier;
    }

    /**
     * Visszaadja a dimenzió kockán belüli egyedi nevét
     *
     * @return a dimenzió egyedi neve
     */
    public String getUniqueName() {
        return uniqueName;
    }

    /**
     * Visszaadja az adott indexen szereplű hierarchiát
     *
     * @param hierarchyIdx a keresett hierarchia indexe
     * @return a keresett hierarchia
     * @see hu.agnos.molap.dimension.Hierarchy
     */
    public Hierarchy getHierarchyById(int hierarchyIdx) {
        return this.hierarchies[hierarchyIdx];
    }

    /**
     * Visszaadja a keresett hierarchiát annak neve alapján
     *
     * @param hierarchyUniqeName a keresett hierarchia neve
     * @return a keresett hierarchia, ha nem tartalmaz ilyet, akkor null
     * @see hu.agnos.molap.dimension.Hierarchy
     */
    public Hierarchy getHierarchyByUniqueName(String hierarchyUniqeName) {
        Hierarchy result = null;
        for (Hierarchy h : this.hierarchies) {
            if (h.getHierarchyUniqueName().equals(hierarchyUniqeName)) {
                result = h;
                break;
            }
        }
        return result;
    }

    /**
     * Visszaadja a tartalmazott hierarchiák vektorát
     *
     * @return a tartalmazott hierarchiák vektora
     * @see hu.agnos.molap.dimension.Hierarchy
     */
    public Hierarchy[] getHierarchies() {
        return hierarchies;
    }

    /**
     * Visszaadja a legfinomabb granularitási szinten található csomópontok
     * vektorát. Az egy dimenzión belüli hierarchiák legfinomabb granularitási
     * szintjét reprezentáló csomópontok minden hierarchiában azonosak (pl. az
     * Idő dimenzió esetén a napok), így azokat csak egyszer a dimenzión belül
     * tároljuk, nem a hierarchiákban.
     *
     * @return a legfinomabb granularitási szinten lévő csomópontok vektora
     * @see hu.agnos.molap.dimension.Node
     */
    public Node[] getBaseLevelNodes() {
        return baseLevelNodes;
    }

    /**
     * Beállítja a paraméteréül kapott legfinomabb granularitási szinten
     * található csomópontok vektorát
     *
     * @param baseLevelNodes a beállítandó csomópontok vektora
     * @see hu.agnos.molap.dimension.Node
     */
    public void setBaseLevelNodes(Node[] baseLevelNodes) {
        this.baseLevelNodes = baseLevelNodes;
    }

    /**
     * Visszaadja a megcímzett csomópontot. A címzés a hierarchiaindex és egy
     * "baseVector" szegmenssel történik.
     *
     * @param hierarchyIdx a dimenzión belüli hierarchia sorszáma
     * @param path a DrillVector egy része (":" szeparált részegység )
     * @return a megcímzett elem / node /member, vagy null, ha a path nem valós
     * elemet címez
     * @see hu.agnos.molap.dimension.Node
     */
    public Node getNode(int hierarchyIdx, String path) {
        Node result = null;
        Hierarchy hier = this.hierarchies[hierarchyIdx];
        String[] levelIds = path.split(",");

        if (levelIds[0].isEmpty()) {
            result = hier.getRoot();
        } else {
            int queryLength = levelIds.length;

            // a path utolsó indexe a kereset elem id-ja
            int idx = Integer.parseInt(levelIds[queryLength - 1]);

            if (hier.getMaxDepth() == queryLength) {
                result = baseLevelNodes[idx];
            } else {
                result = hier.getNode(queryLength, idx);
            }
        }

        return result;

    }

    /**
     * Visszaadja a dimenzión belüli hierarchiák számát.
     *
     * @return a hierarchiák száma
     */
    public int getHierarchyCnt() {
        return hierarchyCnt;
    }

    /**
     * Kiírja a dimenzió főbb adatait
     */
    public void printer() {
        for (int i = 0; i < hierarchies.length; i++) {
            hierarchies[i].printer();
        }

        System.out.println("Base level:");
        for (int i = 0; i < baseLevelNodes.length; i++) {
            System.out.println(baseLevelNodes[i]);
        }
    }

}
