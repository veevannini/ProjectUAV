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
public class iDroneAlpha extends Drone{
    
    public iDroneAlpha(){
        SPEED_MAX = 10.0;
        SPEED_CRUIZE = 5.0;
        MASS = 2.828;
        PAYLOAD = 0.600;
        ENDURANCE = 420.0;
        time = 0.0;
        battery = new Battery(11.1, 0.0, 100.0);
        gps = new GPS();        
        attitude = new Attitude();
        velocity = new Velocity();
        gpsinfo = new GPSInfo();
        sensorUAV = new SensorUAV();
        statusUAV = new StatusUAV();
    }
}
