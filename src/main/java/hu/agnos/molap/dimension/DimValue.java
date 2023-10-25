package hu.agnos.molap.dimension;


import java.util.Objects;

public record DimValue(String id, String knownId, String name) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DimValue dimValue = (DimValue) o;
        return Objects.equals(knownId, dimValue.knownId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(knownId);
    }

}
