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
public class SensorUAV {
    
    public double heading;//in degrees
    public double groundspeed;//in metres/second
    public double airspeed;//in metres/second

    public SensorUAV() {
    }

    public SensorUAV(double heading, double groundspeed, double airspeed) {
        this.heading = heading;
        this.groundspeed = groundspeed;
        this.airspeed = airspeed;
    }
    
    public void parserInfoHeading(String line) {
        try{
            line = line.substring(12, line.length() - 1);        
            this.heading = Double.parseDouble(line);
        }catch (Exception ex){
            
        }
    }
    
    public void parserInfoGroundSpeed(String line) {
        try{
            line = line.substring(16, line.length() - 1);        
            this.groundspeed = Double.parseDouble(line);
        }catch (Exception ex){
            
        }
    }
    
    public void parserInfoAirSpeed(String line) {
        try{
            line = line.substring(13, line.length() - 1);        
            this.airspeed = Double.parseDouble(line);
        }catch (Exception ex){
            
        }
    }
    
    public void setHeading(String heading) {
        try{
            this.heading = Double.parseDouble(heading);
        }catch (Exception ex){
            
        }        
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }
    
    public void setGroundspeed(String groundspeed) {
        try{
            this.groundspeed = Double.parseDouble(groundspeed);
        }catch (Exception ex){
            
        }
    }
    
    public void setGroundspeed(double groundspeed) {
        this.groundspeed = groundspeed;
    }
    
    public void setAirspeed(String airspeed) {
        try{
            this.airspeed = Double.parseDouble(airspeed);
        }catch (Exception ex){
            
        }        
    } 

    public void setAirspeed(double airspeed) {
        this.airspeed = airspeed;
    }        

    @Override
    public String toString() {
        return "SensorUAV{" + "heading=" + heading + ", groundspeed=" + groundspeed + ", airspeed=" + airspeed + '}';
    }        
        
}
