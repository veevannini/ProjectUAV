/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 *
 * @author jesimar
 */
public class UtilIO {
    
    public static long copyFile(File source, File destiny) throws FileNotFoundException, IOException{
       FileInputStream fSource = new FileInputStream(source);
       FileOutputStream fWork = new FileOutputStream(destiny);
       FileChannel fcSource = fSource.getChannel();
       FileChannel fcWork = fWork.getChannel();
       long size = fcSource.transferTo(0, fcSource.size(), fcWork);
       fSource.close();
       fWork.close();
       return size;
    }
    
    public static void copyFileMofif(File src, File dst, String wp, int lineWp, 
            String time, int lineTime) throws FileNotFoundException {
        Scanner sc = new Scanner(src);
        PrintStream print = new PrintStream(dst);
        int i = 1;
        while (sc.hasNextLine()) {
            if (i != lineWp && i != lineTime){
                print.println(sc.nextLine());
            } else if (i == lineWp){
                print.println(wp);
                sc.nextLine();
            } else if (i == lineTime){
                print.println(time);
                sc.nextLine();
            }
            i++;
        }
        sc.close();
        print.close();
    }
    
    public static void deleteFile(File directory, String extension){        
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.getName().contains(extension)){
                    file.delete();
                }
            }
        }
    }
    
    public static int getLineNumber(File file) throws FileNotFoundException, IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        lnr.skip(Long.MAX_VALUE);
        return lnr.getLineNumber();
    }
}
