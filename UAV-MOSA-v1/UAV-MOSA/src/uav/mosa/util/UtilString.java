/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.util;

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
        if (separator.equals("space")){
            return " ";
        } else if (separator.equals("tab")){
            return "\t";
        } else if (separator.equals("semicolon")){
            return ";";
        } else if (separator.equals("comma")){
            return ",";
        } else if (separator.equals("barn")){
            return "\n";
        } else {
            return "-1";
        }
    }
    
}
