package lab6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class pathbge {
    //C:\java\projects\src\labs\lab6\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "pathbge1.in";
        String destinationFileName = "pathbge1.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        FileWriter writer = new FileWriter("pathbge1.out", true);
        String[] input = br.readLine().split(split);
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        ArrayList<Integer>[] map = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            map[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            input = br.readLine().split(split);
            int left = Integer.parseInt(input[0]);//a
            int right = Integer.parseInt(input[1]);//b
            map[left].add(right);
            map[right].add(left);
        }
        int[] length = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            length[i] = Integer.MAX_VALUE;
        }
        length[1] = 0;
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(1);
        while (queue.size() != 0) {
            int start = queue.get(0);
            queue.remove(0);
            for (int i = 0; i < map[start].size(); i++) {
                int connectedVertice = map[start].get(i);
                if (length[connectedVertice] > length[start] + 1) {
                    queue.add(queue.size(), connectedVertice);
                    length[connectedVertice] = length[start] + 1;
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            writer.write(length[i] + " ");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
