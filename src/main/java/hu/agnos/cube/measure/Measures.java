package hu.agnos.cube.measure;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Measures a cube osztályban szereplő mutatók metaadatainak tára, amely egy
 * speciális dimenziónak tekinthető, szokás még tény-dimenziónak is nevezni.
 *
 * @author parisek
 */
@Getter
public class Measures implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;
    private final List<AbstractMeasure> measures;

    public Measures() {
        this.measures = new ArrayList<>();
    }

    /**
     * Egy mutató metájának hozzáadása
     *
     * @param member a hozzáadandó mutató
     */
    public void addMember(AbstractMeasure member) {
        this.measures.add(member);
    }

    /**
     * Determines the index of a non-calculated measure within the cells.
     *
     * @param name Name of the measure
     * @return Index of the measure within the cells
     */
    public int getRealMeasureIdxByName(String name) {
        int i = 0;
        for (AbstractMeasure member : this.measures) {
            if (member.getName().equals(name)) {
                return i;
            }
            if (!member.isCalculatedMember()) {
                i++;
            }
        }
        return -1;
    }

    /**
     * Megadja a nem kalkulált measure-ök darabszámát
     *
     * @return nem kalkulált measure-ök darabszáma
     */
    public int getRealMeasureCount() {
        int result = 0;
        for (AbstractMeasure m : this.measures) {
            if (m instanceof Measure) {
                result++;
            }
        }
        return result;
    }

    /**
     * Egy mutató metájának eltávolitása
     *
     * @param element Az eltávolítandó mutató
     */
    public void removeMeasure(AbstractMeasure element) {
        this.measures.remove(element);
    }

    /**
     * Visszadadja minden mutató (a kalkulált mutatókat is) egyedi nevét egy
     * String vektorban (a sorrend kötött)
     *
     * @return A measure-ök egyedi nevét tartalmazó tömb (a calculated
     * measure-öket is beleértve)
     */
    public String[] getHeader() {
        String[] result = new String[this.measures.size()];
        for (int i = 0; i < this.measures.size(); i++) {
            result[i] = this.measures.get(i).toString();
        }
        return result;
    }

    /**
     * Visszaadja a tartalmazott mutatók nevét
     *
     * @return A tartalmazott mutatók neve
     */
    public String printMembers() {
        String result = "Measure{";
        for (AbstractMeasure member : this.measures) {
            result += member.toString() + ",";
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result + '}';
    }

    @Override
    public String toString() {
        return printMembers();
    }
}
