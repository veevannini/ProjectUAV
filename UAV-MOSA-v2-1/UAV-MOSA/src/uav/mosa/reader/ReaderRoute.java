/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import uav.mosa.struct.Route3D;
import uav.mosa.struct.basic.Position3D;

/**
 *
 * @author jesimar
 */
public class ReaderRoute {
    
    private final Route3D route3D; 
    private final double alt;
    
    public ReaderRoute(Route3D route3D){
        this.route3D = route3D;
        this.alt = Double.parseDouble(ReaderLoadConfig.getInstance().getAltitudeRelativeMission());
    }
    
    public void reader(File inFile) {
        try {
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine()){
                double px = sc.nextDouble();
                double py = sc.nextDouble();
                double vx = sc.nextDouble();
                double vy = sc.nextDouble();
                Position3D p3d = new Position3D(px, py, alt);
                route3D.addPosition3D(p3d);
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchElementException ex) {
            
        }
    }        
}
