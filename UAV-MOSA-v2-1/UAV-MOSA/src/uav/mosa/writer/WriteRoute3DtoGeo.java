/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.writer;

import uav.mosa.struct.basic.PointGeo;
import uav.mosa.util.UtilGeo;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import uav.mosa.reader.ReaderLoadConfig;

/**
 *
 * @author jesimar
 */
public class WriteRoute3DtoGeo {

    public WriteRoute3DtoGeo() {
    }        
    
    public void route3DtoGeo(int i) throws IOException{
        ReaderLoadConfig config = ReaderLoadConfig.getInstance();
        String dir = config.getDirPlanner();
        
        String nameFileRoute3D =  "route3D"  + i + ".txt";
        String nameFileRouteGeo = "routeGeo" + i + ".txt";
        
        Scanner readGeoBase = new Scanner(new File(dir + config.getFileGeoBase()));
        String strGeoBase = readGeoBase.nextLine();
        strGeoBase = readGeoBase.nextLine();
        String strGeo[] = strGeoBase.split(" ");
        readGeoBase.close();
        
        double lon = Double.parseDouble(strGeo[0]);
        double lat = Double.parseDouble(strGeo[1]);
        double alt = Double.parseDouble(strGeo[2]);
        PointGeo pGeo = new PointGeo(lon, lat, alt);
        
        File fileRouteGeo = new File(dir + nameFileRouteGeo);
        PrintStream printGeo = new PrintStream(fileRouteGeo);
        
        Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
        while(readRoute3D.hasNext()){                        
            double x = readRoute3D.nextDouble();
            double y = readRoute3D.nextDouble();
            readRoute3D.nextDouble();
            readRoute3D.nextDouble();
            double h = Double.parseDouble(config.getAltitudeRelativeMission());
            printGeo.println(UtilGeo.parseToGeo(pGeo, x, y, h, ";"));            
        }
        readRoute3D.close();
        printGeo.close();
    }
    
}
