/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.util;

import uav.mosa.struct.basic.Position2D;
import uav.mosa.struct.basic.Position3D;

/**
 *
 * @author jesimar
 */
public class UtilString {
    
    public static String changeValueSeparator(String line){
        line = line.replace("\t", ";");
        line = line.replace("; ", ";");
        line = line.replace(", ", ";");
        line = line.replace(",",  ";");
        return line;
    }
    
    public static String defineSeparator(String separator){
        switch (separator) {
            case "space":
                return " ";
            case "tab":
                return "\t";
            case "semicolon":
                return ";";
            case "comma":
                return ",";
            case "barn":
                return "\n";
            default:
                return "-1";
        }
    }
    
    private static Position2D split2D(String str, String separator){
        String v[] = str.split(separator);
        return new Position2D(Double.parseDouble(v[0]), Double.parseDouble(v[1]));
    }
    
    public static Position3D split3D(String str, String separator){
        String v[] = str.split(separator);
        return new Position3D(Double.parseDouble(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2]));
    }
    
}
