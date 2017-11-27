/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.struct.basic;

/**
 *
 * @author marcio
 */
public class PointGeo {
    
    private final String name;
    private final double longitude;
    private final double latitude;
    private final double altitude;
    
    public PointGeo(double longitude, double latitude, double altitude) {
        this.name = "";
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }
    
    public PointGeo(String name, double longitude, double latitude, double altitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }
    
    public PointGeo(String point) {
        this.name = "";
        String v[] = point.split(",");        
        this.longitude = Double.parseDouble(v[0]);
        this.latitude = Double.parseDouble(v[1]);
        this.altitude = Double.parseDouble(v[2]);
    }
    
    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }    
}
