package labs.dmlab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Exam {
    //C:\java\projects\src\labs\dmlab3\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "exam.in";
        String destinationFileName = "exam.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("exam.out", true);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        String[] temp = br.readLine().split(split);
        double probability = 0;
        int participants = Integer.parseInt(temp[1]);
        int a=Integer.parseInt(temp[0]);
        for (int i = 0; i < a; i++) {
            temp = br.readLine().split(split);
            probability += Double.parseDouble(temp[0]) / participants * Integer.parseInt(temp[1]) / 100;
        }
        writer.write(Double.toString(probability));
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
