package hu.agnos.cube.measure;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Measures a cube osztályban szereplő mutatók metaadatainak tára, amely egy speciális dimenziónak tekinthető, szokás
 * még tény-dimenziónak is nevezni.
 *
 * @author parisek
 */
@Getter
public class Measures implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = -8940196742313994740L;
    private final List<AbstractMeasure> measures;

    public Measures() {
        this.measures = new ArrayList<>(6);
    }

    /**
     * Egy mutató metájának hozzáadása
     *
     * @param measure a hozzáadandó mutató
     */
    public void addMeasure(AbstractMeasure measure) {
        measures.add(measure);
    }

    public AbstractMeasure getMeasureByName(String measureName){
        AbstractMeasure result = null;
        for(AbstractMeasure m : this.measures){
            if(m.getName().equals(measureName)){
                result = m;
                break;
            }
        }
        return result;
    }
    /**
     * Determines the index of a non-calculated measure within the cells.
     *
     * @param name Name of the measure
     * @return Index of the measure within the cells
     */
    public int getRealMeasureIdxByName(String name) {
        int i = 0;
        for (AbstractMeasure member : measures) {
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
        for (AbstractMeasure abstractMeasure : measures) {
            if (abstractMeasure instanceof Measure) {
                result++;
            }
        }
        return result;
    }

    /**
     * Egy mutató metájának eltávolitása
     *
     * @param measur Az eltávolítandó mutató
     */
    public void removeMeasure(AbstractMeasure measur) {
        measures.remove(measur);
    }

    /**
     * Visszadadja minden mutató (a kalkulált mutatókat is) egyedi nevét egy String vektorban (a sorrend kötött)
     *
     * @return A measure-ök egyedi nevét tartalmazó tömb (a calculated measure-öket is beleértve)
     */
    public String[] getHeader() {
        int measuresSize = measures.size();
        String[] result = new String[measuresSize];
        for (int i = 0; i < measuresSize; i++) {
            result[i] = measures.get(i).getName();
        }
        return result;
    }

    /**
     * Visszaadja a tartalmazott mutatók nevét
     *
     * @return A tartalmazott mutatók neve
     */
    public String printMembers() {
        StringBuilder result = new StringBuilder("Measure{");
        for (AbstractMeasure member : measures) {
            result.append(member.toString()).append(",");
        }
        if (result.toString().endsWith(",")) {
            result = new StringBuilder(result.substring(0, result.length() - 1));
        }
        return result.toString() + '}';
    }

    @Override
    public String toString() {
        return printMembers();
    }

}
