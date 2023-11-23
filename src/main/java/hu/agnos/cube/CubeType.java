/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hu.agnos.cube;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author parisek
 */
@Getter
@AllArgsConstructor
public enum CubeType {
    CLASSICAL("Classical"),
    COUNT_DISTINCT("CountDistinct");
    
    private String type;
    
}
