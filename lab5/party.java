package labs.lab5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class party {
    static int currentComponent = 1;
    static int currentPosition = 1;

    static void dfs(int v, int[] used, ArrayList<Integer>[] map, int[] answer) {
        used[v] = 1;//visited
        for (int i = 0; i < map[v].size(); i++) {
            int nextVertice = map[v].get(i);
            if (used[nextVertice] == -1) {//not visited
                dfs(nextVertice, used, map, answer);
            }
        }
        answer[v - 1] = currentPosition;
        currentPosition++;
    }

    static void backDfs(int v, int[] used, ArrayList<Integer>[] backMap, int[] answer) {
        used[v] = 1;//visited
        for (int i = 0; i < backMap[v].size(); i++) {
            int nextVertice = backMap[v].get(i);
            if (used[nextVertice] == -1) {//not visited
                backDfs(nextVertice, used, backMap, answer);
            }
        }
        answer[v - 1] = currentComponent;
        currentPosition++;
    }

    static void mergeSortIterative(int[][] a) {
        for (int i = 1; i < a[0].length; i *= 2) {
            for (int j = 0; j < a[0].length - i; j += 2 * i) {
                merge(a, j, j + i, min(j + 2 * i, a[0].length));
            }
        }
    }

    static int min(int a, int b) {
        return (a <= b) ? a : b;
    }

    static void merge(int[][] a, int left, int mid, int right) {
        int it1 = 0;
        int it2 = 0;
        int[][] result = new int[3][right - left];
        while (left + it1 < mid && mid + it2 < right) {
            if (a[1][left + it1] > a[1][mid + it2]) {
                result[0][it1 + it2] = a[0][left + it1];
                result[1][it1 + it2] = a[1][left + it1];
                it1 += 1;
            } else {
                result[0][it1 + it2] = a[0][mid + it2];
                result[1][it1 + it2] = a[1][mid + it2];
                it2 += 1;
            }
        }
        while (left + it1 < mid) {
            result[0][it1 + it2] = a[0][left + it1];
            result[1][it1 + it2] = a[1][left + it1];
            it1 += 1;
        }
        while (mid + it2 < right) {
            result[0][it1 + it2] = a[0][mid + it2];
            result[1][it1 + it2] = a[1][mid + it2];
            it2 += 1;
        }
        for (int i = 0; i < it1 + it2; i++) {
            a[0][left + i] = result[0][i];
            a[1][left + i] = result[1][i];
        }
    }

    //C:\java\projects\src\labs\lab5\test.
    public static void main(String[] args) throws Exception {
        //String sourceFileName = "C:\\java\\projects\\src\\labs\\lab5\\test.in";
        //String destinationFileName = "C:\\java\\projects\\src\\labs\\lab5\\test.out";
        //java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        //java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String split = "[ ]+";
        //FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs\\lab5\\test.out", true);
        String[] input = br.readLine().split(split);
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        ArrayList<Integer>[] map = new ArrayList[2 * n + 1];
        ArrayList<Integer>[] backMap = new ArrayList[2 * n + 1];
        int[] used = new int[2 * n + 1];
        for (int i = 1; i < 2 * n + 1; i++) {
            map[i] = new ArrayList<>();
            backMap[i] = new ArrayList<>();
            used[i] = -1;
        }
        String[] names = new String[n];
        HashMap<String, Integer> nameConverter = new HashMap<>();
        int namesCount = 1;
        for (int i = 0; i < n; i++) {
            names[i] = br.readLine();
            nameConverter.put("+" + names[i], namesCount);
            namesCount++;
            nameConverter.put("-" + names[i], namesCount);
            namesCount++;
        }
        for (int i = 0; i < m; i++) {
            input = br.readLine().split(split);
            String left = (input[0]);//a
            String right = (input[2]);//b
            String negativeLeft;
            String negativeRight;
            if (left.charAt(0) == '-') {
                negativeLeft = "+" + left.substring(1);
            } else {
                negativeLeft = "-" + left.substring(1);
            }
            if (right.charAt(0) == '-') {
                negativeRight = "+" + right.substring(1);
            } else {
                negativeRight = "-" + right.substring(1);
            }
            int leftIndex;
            int rightIndex;
            int negativeLeftIndex;
            int negativeRightIndex;
            if (nameConverter.containsKey(left)) {
                leftIndex = nameConverter.get(left);
            } else {
                leftIndex = namesCount;
                nameConverter.put(left, namesCount);
                namesCount++;
            }
            if (nameConverter.containsKey(right)) {
                rightIndex = nameConverter.get(right);
            } else {
                rightIndex = namesCount;
                nameConverter.put(right, namesCount);
                namesCount++;
            }
            if (nameConverter.containsKey(negativeLeft)) {
                negativeLeftIndex = nameConverter.get(negativeLeft);
            } else {
                negativeLeftIndex = namesCount;
                nameConverter.put(negativeLeft, namesCount);
                namesCount++;
            }
            if (nameConverter.containsKey(negativeRight)) {
                negativeRightIndex = nameConverter.get(negativeRight);
            } else {
                negativeRightIndex = namesCount;
                nameConverter.put(negativeRight, namesCount);
                namesCount++;
            }

            map[leftIndex].add(rightIndex);
            backMap[rightIndex].add(leftIndex);

            map[negativeRightIndex].add(negativeLeftIndex);
            backMap[negativeLeftIndex].add(negativeRightIndex);
        }
        int[] answer = new int[2 * n];
        for (int i = 1; i < 2 * n + 1; i++) {
            if (used[i] == -1) {
                dfs(i, used, map, answer);
            }
        }
        int[][] mapAccordingToTimes = new int[2][2 * n];
        for (int i = 0; i < 2 * n; i++) {
            mapAccordingToTimes[0][i] = i + 1;
            mapAccordingToTimes[1][i] = answer[i];
            //writer.write(answer[i] + " ");
        }
        //writer.write("\n");
        mergeSortIterative(mapAccordingToTimes);

        for (int i = 1; i < 2 * n + 1; i++) {
            //writer.write(mapAccordingToTimes[0][i-1]+" ");
            used[i] = -1;
        }
        for (int i = 1; i < 2 * n + 1; i++) {
            if (used[mapAccordingToTimes[0][i - 1]] == -1) {
                backDfs(mapAccordingToTimes[0][i - 1], used, backMap, answer);
                currentComponent++;
            }
        }
        boolean possible = true;
        int countOfGuests = 0;
        int[] actualAnswer = new int[n];
        for (int i = 0; i < n; i++) {
            int right = answer[nameConverter.get("-" + names[i]) - 1];
            int left = answer[nameConverter.get("+" + names[i]) - 1];
            if (left == right) {
                possible = false;
                //writer.write("-1");
                System.out.println("-1");
                break;
            } else if (left > right) {
                actualAnswer[i] = 1;
                countOfGuests++;
            } else {
                actualAnswer[i] = -1;
            }
        }
        if (possible) {
            /*//
            writer.write((currentComponent - 1) + "\n");
            for (int i = 0; i < 2 * n; i++) {
                writer.write(answer[i] + " ");
            }
            writer.write("\n");
            // not needed*/
            System.out.println(countOfGuests + "");
            for (int i = 0; i < n; i++) {
                if (actualAnswer[i] == 1) {
                    //writer.write(names[i] + "\n");
                    System.out.println(names[i]);
                }
            }
        }
        //writer.flush();
        //fileInputStream.close();
        //fileOutputStream.close();
    }
}

