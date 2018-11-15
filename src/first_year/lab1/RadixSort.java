package first_year.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class RadixSort {

    static private char[][] numbers;
    static private char[][] helper;

    static private int number;

    public static void sort(char[][] values, int col, int len, int wid) {
        numbers = values;
        number = len;
        helper = new char[number][wid];
        mergesort(0, number - 1, col);
    }

    private static void mergesort(int low, int high, int col) {
        if (low < high) {
            int middle = low + (high - low) / 2;
            mergesort(low, middle, col);
            mergesort(middle + 1, high, col);
            merge(low, middle, high, col);
        }
    }

    private static void merge(int low, int middle, int high, int col) {

        for (int i = low; i <= high; i++) {
            helper[i] = numbers[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        while (i <= middle && j <= high) {
            if ((int) helper[i][col] <= (int) helper[j][col]) {
                numbers[k] = helper[i];
                i++;
            } else {
                numbers[k] = helper[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
        }

    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "RadixSort.in";
        String destinationFileName = "RadixSort.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        FileWriter writer = new FileWriter("RadixSort.out", true);
        String[] ints = br.readLine().split(split);
        int[] arr = new int[3];
        for (int i = 0; i < 3; i++) {
            arr[i] = Integer.parseInt(ints[i]);
        }
        String[] strings = new String[arr[0]];
        for (int i = 0; i < arr[0]; i++) {
            strings[i] = br.readLine();
        }
        char[][] chararr = new char[arr[0]][];
        for (int i = 0; i < arr[0]; i++) {
            chararr[i] = strings[i].toCharArray();
        }

        int i = arr[1] - 1;
        int j = 0;
        while (i >= 0 && j < arr[2]) {
            sort(chararr, i, arr[0], arr[1]);
            j++;
            i--;
        }

        for (int p = 0; p < arr[0]; p++) {
            String ans = "";
            for (int q = 0; q < arr[1]; q++) {
                ans += chararr[p][q];
            }
            writer.write(ans + "\n");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
