/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.Executors;
import uav.mosa.aircraft.iDroneAlpha;
import uav.mosa.struct.Mission3D;
import uav.mosa.util.UtilGeom;
import uav.mosa.util.UtilIO;

/**
 *
 * @author jesimar
 */
public class MissionHGA {

    private final Mission3D pointsMission;
    private final iDroneAlpha iDroneAlpha = new iDroneAlpha();
    private double vx0 = 0.0;
    private double vy0 = 0.0;
    private final String dir;
    private final String cmd = "./exec-hga-mission.sh";
        
    public MissionHGA(Mission3D pointsMission, String dir) throws IOException {
        this.pointsMission = pointsMission;
        this.dir = dir;
        clearLogsOfSystem();
    }
    
    public void execMission(int i) throws IOException, InterruptedException{
        double dist = definePathAB(i);
        updateFileMission(dist, i);
        exec_hga();
        createFileLog(i);
    }  
    
    private double definePathAB(int i) throws FileNotFoundException{
        double px1 = pointsMission.getPosition3D(i).getX();
        double py1 = pointsMission.getPosition3D(i).getY();
        double px2 = pointsMission.getPosition3D(i+1).getX();
        double py2 = pointsMission.getPosition3D(i+1).getY();
        double vx2 = 0;
        double vy2 = 0;
        if (i < pointsMission.size() - 2){
            double px3 = pointsMission.getPosition3D(i+2).getX();
            double py3 = pointsMission.getPosition3D(i+2).getY();        
            double dx = px3 - px1;
            double dy = py3 - py1;
            double norm = Math.sqrt(dx*dx+dy*dy);
            double vc = iDroneAlpha.getSpeedCruize();
            vx2 = dx * vc/norm;
            vy2 = dy * vc/norm;            
        }
        saveFileMissionConfig(px1, py1, px2, py2, vx0, vy0, vx2, vy2, i);
        vx0 = vx2;
        vy0 = vy2;
        return UtilGeom.distanceEuclidian(px2, py2, px1, py1);
    }
    
    private void updateFileMission(double dist, int i) throws FileNotFoundException{
        File src_ga = new File(dir + "ga-config-base");
        File dst_ga = new File(dir + "ga-config");
        double t = 2*dist/iDroneAlpha.getSpeedCruize();
        String time = String.format("%.2f", t);
        String wp = String.format("%d", (int)t);        
        time = time.replace(",", ".");
        System.out.println("Time: " + time);
        UtilIO.copyFileMofif(src_ga, dst_ga, wp, 425, time, 426);
    }
    
    private void createFileLog(int i) throws IOException{
        File src = new File(dir + "output-simulation.log");
        File dst = new File(dir + "out-sim"+i+".log");
        UtilIO.copyFile(src, dst);
        File route = new File(dir + "route"+i+".log");
        
        Scanner sc = new Scanner(dst);
        PrintStream print = new PrintStream(route);  
        boolean test1 = false;
        boolean test2 = false;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!test1 && !test2){
                if (line.equals("------------------------ [ states ] ------------------------")){
                    test1 = true;
                }
            }else if (test1 && !test2){
                if (!line.equals("------------------------ [ controls ] ------------------------")){
                    print.println(line);
                }else{
                    test2 = true;
                }
            }
        }
        sc.close();
        print.close();
    }
    
    private void saveFileMissionConfig(double px1, double py1, double px2, double py2, 
            double vx1, double vy1, double vx2, double vy2, int i) throws FileNotFoundException{
        PrintStream print = new PrintStream(new File(dir + "mission-config.sgl"));
        print.println("----------- start state (px py vx vy) -----------");
        print.println(px1 + "," + py1 + "," + vx1 + "," + vy1);        
        print.println("--------------- end point (px py)---------------");
        if (i < pointsMission.size() - 2){
            print.println(px2 + "," + py2 + ","+ vx2 + "," + vy2);
        }else {
            print.println(px2 + "," + py2 + ",0,0");
        }
        print.println();
        print.println("<TrueName>");
        print.println("Config2D-2.sgl");
        print.close();
    }
    
    private void exec_hga() throws IOException, InterruptedException{
        boolean print = false;
        boolean error = false;
        File f = new File(dir);
        final Process comp = Runtime.getRuntime().exec(cmd, null, f);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (print) {
                    while (sc.hasNextLine()) {
                        System.out.println(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getErrorStream());
                if (error) {
                    while (sc.hasNextLine()) {
                        System.err.println("err:" + sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        comp.waitFor();
    }
    
    private void clearLogsOfSystem() throws IOException{
        File src_ga = new File(dir + "ga-config-base");
        File dst_ga = new File(dir + "ga-config");
        UtilIO.copyFile(src_ga, dst_ga);
        
        new File(dir + "log_error.txt").delete();
        
        UtilIO.deleteFile(new File(dir), ".log");
        UtilIO.deleteFile(new File(dir), ".png");
    }    
}
