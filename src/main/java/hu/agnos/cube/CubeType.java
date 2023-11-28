package hu.agnos.cube;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CubeType {

    CLASSICAL("Classical"),
    COUNT_DISTINCT("CountDistinct");

    private final String type;

}
