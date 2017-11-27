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
public class iDroneAlpha {
    
    private final double speed_max = 10.0;//m/s ou 36km/h
    private final double speed_cruize = 5.0;//m/s ou 18km/h
    private final double mass = 2.828;//kg  ??
    private final double payload = 0.600;//kg ??
    private final double comprimento = 0.55;//m
    private final double largura = 0.57;//m
    private final double altura = 0.32;//m
    private final double tempo_voo = 420.0;//seg ou 7 minutos    
    private final double bateria_volt = 11.1;//V
    private final double bateria_corrente = 2200.0;//mAh    
    
    public iDroneAlpha(){
        
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
