package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class kenobi {
    public static class lightsabre {
        int next;
        int prev;
        int number;

        public lightsabre(int number, int next, int prev) {
            this.number = number;
            this.next = next;
            this.prev = prev;
        }
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "kenobi.in";
        String destinationFileName = "kenobi.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("kenobi.out", true);
        String split = "[ ]+";
        int n = Integer.parseInt(br.readLine());
        lightsabre[] listt = new lightsabre[n + 1];
        int size = 0;

        int start = -1;
        int last = -1;
        int mid = -1;

        for (int i = 0; i < n; i++) {
            String[] command = br.readLine().split(split);
            if (command[0].equals("add")) {
                if (size > 2) {
                    if (size % 2 == 1) {
                        mid = listt[mid].next;
                    }
                    int j = Integer.parseInt(command[1]);
                    listt[j] = new lightsabre(j, -1, last);
                    listt[last] = new lightsabre(last, j, listt[last].prev);
                    last = j;
                    size++;
                } else if (size == 1 || size == 2) {
                    int j = Integer.parseInt(command[1]);
                    listt[j] = new lightsabre(j, -1, last);
                    listt[last] = new lightsabre(last, j, listt[last].prev);
                    last = j;
                    size++;
                } else {
                    int j = Integer.parseInt(command[1]);
                    start = j;
                    mid = j;
                    last = j;
                    listt[j] = new lightsabre(j, -1, -1);
                    size++;
                }
            } else if (command[0].equals("take")) {
                if (size != 0) {

                    if (size % 2 == 0 && size > 2) {
                        mid = listt[mid].prev;
                    }
                    last = listt[last].prev;
                    if (size > 1) {
                        listt[last].next = -1;
                    }

                    size--;
                }
            } else {
                if (size <= 1) {

                } else if (size % 2 == 1) {
                    listt[last].next = start;
                    int templast = last;
                    last = mid;
                    start = listt[mid].next;
                    listt[mid].next = -1;
                    mid = listt[templast].prev;
                    listt[listt[listt[mid].next].next].prev = listt[mid].next;
                    listt[start].prev = -1;
                } else {
                    listt[last].next = start;
                    int templast = last;
                    last = mid;
                    start = listt[mid].next;
                    listt[mid].next = -1;
                    mid = templast;
                    listt[listt[mid].next].prev = mid;
                    listt[start].prev = -1;
                }
            }
        }
        writer.write("" + size + "\n");
        int k = size;
        for (int i = 0; i < k; i++) {
            writer.write("" + listt[start].number + " ");
            start = listt[start].next;
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
/*
18
add 1
add 2
add 3
add 4
add 5
add 6
add 8
take
mum!
num!
num!
add 7
num!
add 8
num!
take
take
add 9
 */