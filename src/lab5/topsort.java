package lab5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class topsort {
    static int currentPosition = 0;
    static boolean hasLoops = false;

    static boolean dfs(int v, int[] used, ArrayList<Integer>[] map, int[] answer) {
        used[v] = 0;//visited
        for (int i = 0; i < map[v].size(); i++) {
            int nextVertice = map[v].get(i);
            if (used[nextVertice] == -1) {//not visited
                if (dfs(nextVertice, used, map, answer)) {
                    return true;//has loops
                }
            } else if (used[nextVertice] == 0) {
                hasLoops = true;
                return true;
            }
        }
        used[v] = 1;//finished
        answer[currentPosition] = v;
        currentPosition++;
        return false;
    }

    //C:\java\projects\src\labs\lab4\test.
    public static void main(String[] args) throws Exception {
        String sourceFileName = "C:\\java\\projects\\src\\labs\\lab5\\test.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs\\lab5\\test.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs\\lab5\\test.out", true);
        String[] input = br.readLine().split(split);
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        ArrayList<Integer>[] map = new ArrayList[n + 1];
        int[] used = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            map[i] = new ArrayList<>();
            used[i] = -1;
        }
        for (int i = 0; i < m; i++) {
            input = br.readLine().split(split);
            int left = Integer.parseInt(input[0]);//a
            int right = Integer.parseInt(input[1]);//b
            map[left].add(right);
        }


        int[] answer = new int[n];
        for (int i = 1; i < n + 1; i++) {
            if (used[i] == -1) {
                dfs(i, used, map, answer);
            }
            if (hasLoops) {
                writer.write("-1");
                break;
            }
        }
        if (!hasLoops) {
            for (int i = n - 1; i >= 0; i--) {
                writer.write(answer[i] + " ");
            }
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
