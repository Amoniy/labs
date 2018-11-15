package first_year.lab5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Cycle {
    static int currentPosition = 0;
    static boolean hasLoops = false;
    static int marker = 0;
    static int anotherMarker = 0;

    static boolean dfs(int v, int[] used, ArrayList<Integer>[] map, int[] answer, int[] parent) {
        used[v] = 0;//visited
        for (int i = 0; i < map[v].size(); i++) {
            int nextVertice = map[v].get(i);
            if (used[nextVertice] == -1) {//not visited
                if (dfs(nextVertice, used, map, answer, parent)) {
                    if (anotherMarker != nextVertice) {
                        parent[nextVertice] = v;
                    }
                    return true;//has loops
                }
            } else if (used[nextVertice] == 0) {
                hasLoops = true;
                parent[nextVertice] = v;
                marker = nextVertice;
                anotherMarker = nextVertice;
                return true;
            }
        }
        used[v] = 1;//finished
        answer[currentPosition] = v;
        currentPosition++;
        return false;
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "Cycle.in";
        String destinationFileName = "Cycle.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        FileWriter writer = new FileWriter("Cycle.out", true);
        String[] input = br.readLine().split(split);
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        ArrayList<Integer>[] map = new ArrayList[n + 1];
        int[] used = new int[n + 1];
        int[] parent = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            map[i] = new ArrayList<>();
            used[i] = -1;
            parent[i] = i;
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
                dfs(i, used, map, answer, parent);
            }
            if (hasLoops) {
                break;
            }
        }
        if (!hasLoops) {
            writer.write("NO");
        }
        if (hasLoops) {
            ArrayList<Integer> output = new ArrayList<>();
            writer.write("YES\n");
            int checker = marker;
            while (true) {
                //writer.write(marker+" ");
                output.add(0, marker);
                marker = parent[marker];
                if (marker == checker) {
                    break;
                }
            }
            writer.write(output.get(output.size() - 1) + " ");
            output.remove(output.size() - 1);
            int q = output.size();
            for (int i = 0; i < q; i++) {
                writer.write(output.get(0) + " ");
                output.remove(0);
            }
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
