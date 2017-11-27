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
public class Attitude {
    
    public double pitch;//in radians
    public double yaw;//in radians
    public double roll;//in radians

    public Attitude() {
    }

    public Attitude(double pitch, double yaw, double roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }
    
    public void parserInfoAttitude(String line) {
        try{
            line = line.substring(9, line.length() - 2);
            String v[] = line.split(",");        
            this.pitch = Double.parseDouble(v[0]);        
            this.yaw = Double.parseDouble(v[1]);
            this.roll   = Double.parseDouble(v[2]);
        }catch (Exception ex){
            
        }
    } 
    
    public void updateAttitude(double pitch, double yaw, double roll){
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }
       
    @Override
    public String toString() {
        return "Attitude{" + "pitch=" + pitch + ", yaw=" + yaw + ", roll=" + roll + '}';
    }        
    
}
