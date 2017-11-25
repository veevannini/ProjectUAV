/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.struct.basic;

/**
 *
 * @author jesimar
 */
public class Waypoint {
    
    private String action;
    private double lat;//equivale a coordenada y.
    private double lng;//equivale a coordenada x.
    private double alt;//equivale a coordenada z.
    
    public Waypoint(){
        
    }
    
    public Waypoint(double lat, double lng, double alt){
        this.action = "";
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
            
    public Waypoint(String action, double lat, double lng, double alt){
        this.action = action;
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    public void addWaypoint(String action, double lat, double lng, double alt){
        this.action = action;
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    public void addWaypoint(double lat, double lng, double alt){
        this.action = "";
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    public String string(){
        return action + ", " + lat + ", " + lng + ", " + alt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }        

    @Override
    public String toString() {
        return "Waypoint: [" + action + ", " + lat + ", " + lng + ", " + alt + "]";
    }        
}
