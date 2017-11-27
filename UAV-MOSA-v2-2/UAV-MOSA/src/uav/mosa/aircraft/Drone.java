/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.aircraft;

import uav.mosa.aircraft.sensors.Attitude;
import uav.mosa.aircraft.sensors.Battery;
import uav.mosa.aircraft.sensors.GPS;
import uav.mosa.aircraft.sensors.GPSInfo;
import uav.mosa.aircraft.sensors.SensorUAV;
import uav.mosa.aircraft.sensors.StatusUAV;
import uav.mosa.aircraft.sensors.Velocity;

/**
 *
 * @author jesimar
 */
public abstract class Drone {

    double SPEED_MAX;//m/s
    double SPEED_CRUIZE;//m/s
    double MASS;//kg
    double PAYLOAD;//kg
    double ENDURANCE;//segundos
    double time;//segundos
    GPS gps;
    Battery battery;    
    Attitude attitude;
    Velocity velocity;
    GPSInfo gpsinfo;
    SensorUAV sensorUAV;
    StatusUAV statusUAV;
        
    public double getSpeedCruize() {
        return SPEED_CRUIZE;
    }
    
    public double getSpeedMax() {
        return SPEED_MAX;
    }    

    public double getMass() {
        return MASS;
    }

    public double getPayload() {
        return PAYLOAD;
    }
    
    public double getEndurance(){
        return ENDURANCE;
    }
    
    public void incrementTime(double i){
        time += i/1000.0;
    }

    public Battery getBattery() {
        return battery;
    }
    
    public GPS getGPS() {
        return gps;
    }
    
    public Attitude getAttitude(){
        return attitude;
    }
    
    public Velocity getVelocity(){
        return velocity;
    } 

    public SensorUAV getSensorUAV() {
        return sensorUAV;
    }

    public GPSInfo getGPSInfo() {
        return gpsinfo;
    }
    
    public StatusUAV getStatusUAV(){
        return statusUAV;
    }
    
    public void setInfoGPS(double lat, double lng, double alt) {
        this.gps.lat = lat;
        this.gps.lng = lng;
        this.gps.alt = alt;
    }
        
    public void setInfoBattery(double voltage, double current, double level) {
        this.battery.voltage = voltage;
        this.battery.current = current;
        this.battery.level = level;
    }           
    
    public void setInfoAttitude(double pitch, double yaw, double roll) {
        this.attitude.pitch = pitch;
        this.attitude.yaw = yaw;
        this.attitude.roll = roll;
    } 
    
    public void setInfoGPSInfo(int fix, int num_sat) {
        this.gpsinfo.fixType = fix;
        this.gpsinfo.satellitesVisible = num_sat;
    }
    
    public String title(){
        return "time;lat;lng;alt;voltage;current;level;pitch;yaw;roll;vx;vy;vz;"
                + "fixtype;satellitesvisible;heading;groundspeed;airspeed;mode;"
                + "system-status;armed;is-armable;ekf-ok";
    }    
        
    @Override
    public String toString() {
        return time + ";" + gps.lat + ";" + gps.lng + ";" + gps.alt + ";" + 
               battery.voltage + ";" + battery.current + ";" + battery.level +  ";" + 
               attitude.pitch + ";" + attitude.yaw + ";" + attitude.roll + ";" +
               velocity.vx + ";" + velocity.vy + ";" + velocity.vz + ";" +
               gpsinfo.fixType + ";" + gpsinfo.satellitesVisible + ";" +
               sensorUAV.heading + ";" + sensorUAV.groundspeed + ";" + 
               sensorUAV.airspeed + ";" + statusUAV.mode + ";" + 
               statusUAV.systemStatus + ";" + statusUAV.armed + ";" + 
               statusUAV.isArmable + ";" + statusUAV.ekfOk;
    }
}
