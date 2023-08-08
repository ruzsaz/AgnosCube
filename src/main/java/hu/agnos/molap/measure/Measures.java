/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.agnos.molap.measure;

import java.util.ArrayList;
import java.util.List;

/**
 * Measures a cube osztályban szereplő mutatók metaadatainak tára, amely egy
 * speciális dimenziónak tekinthető, szokás még tény-dimenziónak is nevezni.
 *
 * @author parisek
 */
public class Measures implements java.io.Serializable {

    private static final long serialVersionUID = -8940196742313994740L;

    /**
     * A measure-ök listája, elemei kalkulált és nem kalkulát measure-ök.
     */
    private final List<AbstractMeasure> measures;

    /**
     * A Measures konstruktora
     */
    public Measures() {
        this.measures = new ArrayList<>();
    }

    /**
     * Visszaadja a tárolt mutatók metáját
     *
     * @return a measure-öket tartalamzó tömb
     */
    public List<AbstractMeasure> getMeasures() {
        return measures;
    }

    /**
     * Visszaadja a mutatók számát
     *
     * @return a measure száma
     */
    public int getMemberCount() {
        return measures.size();
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
     * Ez egy nem kalkulált measure nevéből, visszaadja, a cells tömben lévő
     * oszlopsorszámát.
     *
     * @param uniqeName a keresendő measure neve
     * @return az őt reprezentáló cells béli oszlop sorszáma
     */
    public int getRealMeasureIdxByUniquName(String uniqeName) {
        int i = 0;
        for (AbstractMeasure member : this.measures) {
            if (member.getName().equals(uniqeName)) {
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
     * Visszaad egy konkrét mutatót metáját
     *
     * @param idx a konkrét mutató kockabéli sorsáma
     * @return a kívánt mutató
     * @throws IndexOutOfBoundsException ha az IDX értéke nagyobb a measures vektor méreténél
     */
    public AbstractMeasure getMember(int idx) throws IndexOutOfBoundsException {
        return measures.get(idx);
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
