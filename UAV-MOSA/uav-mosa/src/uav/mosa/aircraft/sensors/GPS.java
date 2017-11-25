/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.aircraft.sensors;

/**
 *
 * @author jesimar
 */
public class GPS {
    
    public double lat;
    public double lng;
    public double alt;

    public GPS() {
    }

    public GPS(double lat, double lng, double alt) {
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    public void parserInfoGPS(String line) {
        try{
            line = line.substring(9, line.length() - 2);
            String v[] = line.split(",");
            this.lat = Double.parseDouble(v[0]);        
            this.lng = Double.parseDouble(v[1]);
            this.alt = Double.parseDouble(v[2]);
        }catch (Exception ex){
            
        }
    }
    
    public void updateGPS(String lat, String lng, String alt){
        try{
            this.lat = Double.parseDouble(lat);        
            this.lng = Double.parseDouble(lng);
            this.alt = Double.parseDouble(alt);
        }catch (Exception ex){
            
        }
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }        

    @Override
    public String toString() {
        return "GPS{" + "lat=" + lat + ", lng=" + lng + ", alt=" + alt + '}';
    }   
        
}
