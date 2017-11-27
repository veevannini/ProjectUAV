/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import uav.mosa.struct.Mission3D;
import uav.mosa.struct.basic.Position3D;

/**
 *
 * @author jesimar
 */
public class ReaderMission {
    
    private final Mission3D mission3D; 
    
    public ReaderMission(Mission3D mission3D){
        this.mission3D = mission3D;
    }
    
    public void reader(File inFile) {
        try {
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                Position3D p3d = split(line);
                mission3D.addPosition3D(p3d);
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchElementException ex) {
            
        }
    }
    
    public Position3D split(String str){
        String v[] = str.split(" ");
        return new Position3D(Double.parseDouble(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2]));
    }
}
