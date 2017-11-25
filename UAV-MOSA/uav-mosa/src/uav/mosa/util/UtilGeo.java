package uav.mosa.util;

import java.io.PrintStream;
import java.util.Locale;
import uav.mosa.struct.basic.PointGeo;
import uav.mosa.struct.basic.Line3D;
import uav.mosa.struct.basic.Point3D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jesimar
 */
public class UtilGeo {
        
    public final static double CIRC_EQUATORIAL = 40075017.0;//40075000.0;
    public final static double CIRC_MERIDIONAL = 40007860.0;//40000000.0;            
    
    public static String parseToGeo(PointGeo base, double x, double y, double h, String separator){
        double lat = converteYtoLatitude(base.getLatitude(), y);
        double lon = converteXtoLongitude(base.getLongitude(), base.getLatitude(), x);        
        double alt = base.getAltitude() + h;
        return lat + separator + lon + separator + alt;
    }
    
    public static PointGeo parseToGeo1(PointGeo base, double x, double y, double h){
        return new PointGeo(
            converteXtoLongitude(base.getLongitude(), base.getLatitude(), x), 
            converteYtoLatitude(base.getLatitude(), y), 
            base.getAltitude() + h
        );
    }
    
    public static String parseToGeo2(PointGeo base, double x, double y, double h){
        double lat = converteYtoLatitude(base.getLatitude(), y);
        double lon = converteXtoLongitude(base.getLongitude(), base.getLatitude(), x);        
        double alt = base.getAltitude() + h;
        return lat + "\t" + lon + "\t" + alt;
    }
    
    public static String parseToGeo3(PointGeo base, double x, double y){
        double lat = converteYtoLatitude(base.getLatitude(), y);
        double lon = converteXtoLongitude(base.getLongitude(), base.getLatitude(), x);
        return lon + "," + lat + ",0 ";
    }
    
    public static String parseToGeoRelativeGround2(PointGeo base, double x, double y, double h){
        double lat = converteYtoLatitude(base.getLatitude(), y);
        double lon = converteXtoLongitude(base.getLongitude(), base.getLatitude(), x);
        return lat + "\t" + lon + "\t" + h;
    }
    
    public static double converteYtoLatitude(double lat, double y){
        return lat + y*360/CIRC_MERIDIONAL;
    }
    
    public static double converteXtoLongitude(double lon, double lat, double x){
        return lon+ x*360/(CIRC_EQUATORIAL*Math.cos(lat*Math.PI/180));
    }
    
    public static void savePointsDirections(PrintStream output, Line3D line3d) {
        double vx = 24;
        double vy = 0;
        Point3D p1 = line3d.getPoint3D(0);
        Point3D p2 = line3d.getPoint3D(1);
        double px = p1.getX();
        double py = p1.getY();
        double pz = 100.0;
        double vz = 0.0;         
        double px2 = p2.getX();
        double py2 = p2.getY();
        double angle = Math.atan2(py2 - py, px2 - px);//Math.atan2(py - py2, px - px2);
        vx = Math.cos(angle) * 24;
        vy = Math.sin(angle) * 24;
        System.out.println("angle:" + angle);
        output.println(String.format(
                Locale.ENGLISH,"%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", 
                px, py, pz, vx, vy, vz));
    }
}
