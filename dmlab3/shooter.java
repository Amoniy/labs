package labs.dmlab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class shooter {
    public static void main(String[] args) throws Exception {
        String sourceFileName = "shooter.in";
        String destinationFileName = "shooter.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("shooter.out", true);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        String[] temp = br.readLine().split(split);
        double probability = 0;
        int participants = Integer.parseInt(temp[0]);
        int shots = Integer.parseInt(temp[1]);
        int number = Integer.parseInt(temp[2]);
        temp = br.readLine().split(split);
        for (int i = 0; i < participants; i++) {
            double currentProb = Double.parseDouble(temp[i]);
            double tempProb = 1;
            for (int j = 0; j < shots; j++) {
                tempProb *= (1 - currentProb);
            }
            probability += tempProb;
        }
        double neededShooter = 1;
        double neededShot = Double.parseDouble(temp[number - 1]);
        for (int j = 0; j < shots; j++) {
            neededShooter *= (1 - neededShot);
        }
        probability = neededShooter / probability;
        writer.write(Double.toString(probability));
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
