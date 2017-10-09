package lab1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TaskH {

    static class HeapUtils {
        public static void heapify(int[] array, int size, int pos) {
            while (2 * pos + 1 < size) {                       //Процедура нормализации
                int t = 2 * pos + 1;                           //подкучи в куче с
                //головой в pos
                if (2 * pos + 2 < size && array[2 * pos + 1] < array[2 * pos + 2]) {
                    t = 2 * pos + 2;
                }
                if (array[pos] < array[t]) {
                    swap(array, pos, t);
                    pos = t;
                } else {
                    break;
                }
            }
        }

        public static int[] heapMake(int[] array) {  //Построение кучи из массива при
            int n = array.length;                    //помощи функции heapify
            for (int i = n - 1; i >= 0; i--) {
                heapify(array, n, i);
            }
            return array;
        }

        public static void sort(int[] array) { //Собственно сама сортировка
            int n = array.length;
            heapMake(array);
            while (n > 0) {
                swap(array, 0, n - 1);
                n--;
                heapify(array, n, 0);
            }
        }

        private static void swap(int[] array, int i, int j) { //Меняет местами
            int temp = array[i];                                //элементы с
            array[i] = array[j];                                //индексами i и j
            array[j] = temp;                                    //в массиве array
        }
    }

    public static int del(int i1, int i2, int i3, int i4) {
        int max = Math.max(Math.max(i1, i2), Math.max(i3, i4));
        int min = min(min(i1, i2), min(i3, i4));
        int del = max - min;
        return del;
    }

    public static int min(int i1) {
        return i1;
    }

    public static int min(int i1, int i2) {
        return Math.min(i1, i2);
    }

    public static int min(int i1, int i2, int i3) {
        return Math.min(Math.min(i1, i2), i3);
    }

    public static int min(int i1, int i2, int i3, int i4) {
        return Math.min(Math.min(i1, i2), Math.min(i3, i4));
    }

    public static boolean m(int i1, int i2) {
        return (i1 == i2 - 1);
    }


    public static void main(String[] args) throws IOException {
        String sourceFileName = "C:\\java\\projects\\src\\labs.lab1\\style.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs.lab1\\style.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));

        int n1 = Integer.parseInt(br.readLine());
        int[] arr1 = new int[n1];
        String split = "[ ]+";
        String[] ints = br.readLine().split(split);
        for (int i = 0; i < n1; i++) {
            arr1[i] = Integer.parseInt(ints[i]);
        }

        int n2 = Integer.parseInt(br.readLine());
        int[] arr2 = new int[n2];
        String split2 = "[ ]+";
        String[] ints2 = br.readLine().split(split2);
        for (int i = 0; i < n2; i++) {
            arr2[i] = Integer.parseInt(ints2[i]);
        }

        int n3 = Integer.parseInt(br.readLine());
        int[] arr3 = new int[n3];
        String split3 = "[ ]+";
        String[] ints3 = br.readLine().split(split3);
        for (int i = 0; i < n3; i++) {
            arr3[i] = Integer.parseInt(ints3[i]);
        }

        int n4 = Integer.parseInt(br.readLine());
        int[] arr4 = new int[n4];
        String split4 = "[ ]+";
        String[] ints4 = br.readLine().split(split4);
        for (int i = 0; i < n4; i++) {
            arr4[i] = Integer.parseInt(ints4[i]);
        }


        HeapUtils.sort(arr1);
        HeapUtils.sort(arr2);
        HeapUtils.sort(arr3);
        HeapUtils.sort(arr4);

        for (int i = 0; i < n1; i++) {
            System.out.println(arr1[i]);
        }
        for (int i = 0; i < n2; i++) {
            System.out.println(arr2[i]);
        }
        for (int i = 0; i < n3; i++) {
            System.out.println(arr3[i]);
        }
        for (int i = 0; i < n4; i++) {
            System.out.println(arr4[i]);
        }

        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int si1 = 0;
        int si2 = 0;
        int si3 = 0;
        int si4 = 0;
        int d = del(arr1[i1], arr2[i2], arr3[i3], arr4[i4]);

        while (i1 < n1 && i2 < n2 && i3 < n3 && i4 < n4) {
            if (del(arr1[i1], arr2[i2], arr3[i3], arr4[i4]) < d) {
                d = del(arr1[i1], arr2[i2], arr3[i3], arr4[i4]);
                si1 = i1;
                si2 = i2;
                si3 = i3;
                si4 = i4;
                if (d == 0) break;
            }

            if (arr1[i1] == min(min(arr1[i1], arr2[i2]), min(arr3[i3], arr4[i4]))) {
                i1++;
            } else if (arr2[i2] == min(min(arr1[i1], arr2[i2]), min(arr3[i3], arr4[i4]))) {
                i2++;
            } else if (arr3[i3] == min(min(arr1[i1], arr2[i2]), min(arr3[i3], arr4[i4]))) {
                i3++;
            } else {
                i4++;
            }

        }

        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs.lab1\\style.out", true);
        writer.write(arr1[si1] + " " + arr2[si2] + " " + arr3[si3] + " " + arr4[si4]);
        writer.flush();///////////////////
        fileInputStream.close();
        fileOutputStream.close();
    }
}