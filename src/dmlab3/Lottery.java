package dmlab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Lottery {
    //C:\java\projects\src\labs\dmlab3\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "lottery.in";
        String destinationFileName = "lottery.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("lottery.out", true);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        String[] temp = br.readLine().split(split);
        int numberOfGames = Integer.parseInt(temp[1]);
        int cost = Integer.parseInt(temp[0]);
        double expectedValue = 0;
        double probabilityToGetHere = 1;
        int currentWinValue = 0;
        for (int i = 0; i < numberOfGames; i++) {
            temp = br.readLine().split(split);
            expectedValue += probabilityToGetHere * currentWinValue * (1 - 1 / Double.parseDouble(temp[0]));
            probabilityToGetHere *= 1 / Double.parseDouble(temp[0]);
            currentWinValue = Integer.parseInt(temp[1]);
        }
        expectedValue += probabilityToGetHere * currentWinValue;
        writer.write(Double.toString(cost - expectedValue));
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
