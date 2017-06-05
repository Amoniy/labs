package labs.lab5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class points {
    static int currentTime = 1;
    static ArrayList<Integer> answer = new ArrayList<>();

    static void dfs(int v, boolean[] used, ArrayList<int[]>[] map, int[] timeIn, int[] arrayForChecking, int parentVertice) {
        used[v] = true;
        timeIn[v] = currentTime;
        arrayForChecking[v] = currentTime;
        currentTime++;
        int children = 0;
        for (int i = 0; i < map[v].size(); i++) {
            int nextVertice = map[v].get(i)[0];
            if (nextVertice == parentVertice) {
                //donothing
            } else {
                if (!used[nextVertice]) {//downVertics
                    dfs(nextVertice, used, map, timeIn, arrayForChecking, v);
                    arrayForChecking[v] = Math.min(arrayForChecking[v], arrayForChecking[nextVertice]);
                    if ((arrayForChecking[nextVertice] >= timeIn[v]) && (parentVertice != -1)) {
                        if(!answer.contains(v)) {
                            answer.add(answer.size(), v);
                        }
                    }
                    children++;
                } else {//upVertice
                    arrayForChecking[v] = Math.min(arrayForChecking[v], timeIn[nextVertice]);//timeIn
                }
            }
        }
        if ((children >= 2) && (parentVertice == -1)) {
            if(!answer.contains(v)) {
                answer.add(answer.size(), v);
            }
        }
    }

    private static class MyComp implements Comparator<Integer> {
        public int compare(Integer e1, Integer e2) {
            if (e1 > e2) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    //C:\java\projects\src\labs\lab5\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "points.in";
        String destinationFileName = "points.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        FileWriter writer = new FileWriter("points.out", true);
        String[] input = br.readLine().split(split);
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        ArrayList<int[]>[] map = new ArrayList[n + 1];
        boolean[] used = new boolean[n + 1];
        int[] timeIn = new int[n + 1];
        int[] arrayForChecking = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            map[i] = new ArrayList<>();
            used[i] = false;
        }
        for (int i = 0; i < m; i++) {
            input = br.readLine().split(split);
            int left = Integer.parseInt(input[0]);//a
            int right = Integer.parseInt(input[1]);//b
            map[left].add(new int[]{right, i + 1});
            map[right].add(new int[]{left, i + 1});
        }

        for (int i = 1; i < n + 1; i++) {
            if (!used[i]) {
                dfs(i, used, map, timeIn, arrayForChecking, -1);
            }
        }
        writer.write(answer.size() + "\n");
        answer.sort(new MyComp());
        for (int i = 0; i < answer.size(); i++) {
            writer.write(answer.get(i) + " ");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
