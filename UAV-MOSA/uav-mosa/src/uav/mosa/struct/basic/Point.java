/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uav.mosa.struct.basic;

import java.io.Serializable;

/**
 *
 * @author jesimar
 */
public abstract class Point implements Serializable{
    
    public abstract double distance(Point point);
    
}
