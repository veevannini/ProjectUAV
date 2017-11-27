/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.aircraft;

/**
 *
 * @author jesimar
 */
public class Ararinha {
    
    private final double speed_max = 30.0;//m/s ou 108km/h
    private final double speed_cruize = 20.0;//m/s ou 72km/h
    private final double mass = 2.828;//kg
    private final double payload = 0.600;//kg
    private final double envergadura = 1.90;//m
    private final double comprimento = 1.15;//m
    private final double power_motor = 740;//W
    private final double tempo_voo = 900.0;//seg ou 15 minutos    
    private final double bateria_volt = 14.8;//V
    private final double bateria_corrente = 5000.0;//mAh    
    
    public Ararinha(){
        
    }

    public double getSpeedCruize() {
        return speed_cruize;
    }

    public double getSpeedMax() {
        return speed_max;
    }    
    
    public double getMass() {
        return mass;
    }

    public double getPayload() {
        return payload;
    }
}
