package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Partition {
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {
        String destinationFileName = "Test.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("Test.out", true);
        FastScanner in = new FastScanner(new File("Test.in"));
        int v = in.nextInt();
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < v - 1; i++) {
            array.add(1);
            writer.write("1+");
        }
        array.add(1);
        writer.write("1\n");

        while (true) {
            if (array.get(0) == v) {
                break;
            }

            array.set(array.size() - 2, array.get(array.size() - 2) + 1);
            array.set(array.size() - 1, array.get(array.size() - 1) - 1);

            if (array.get(array.size() - 2) < array.get(array.size() - 1)) {
                while (array.get(array.size() - 2) * 2 <= array.get(array.size() - 1)) {
                    array.add(array.size() - 1, array.get(array.size() - 2));
                    array.add(array.size() - 1, array.get(array.size() - 1) - array.get(array.size() - 2));
                    array.remove(array.size() - 1);
                }

            } else if (array.get(array.size() - 2) > array.get(array.size() - 1)) {
                array.set(array.size() - 2, array.get(array.size() - 2) + array.get(array.size() - 1));
                array.remove(array.size() - 1);
            }

            for (int i = 0; i < array.size() - 1; i++) {
                writer.write(array.get(i) + "+");
            }
            writer.write(array.get(array.size() - 1) + "\n");
        }
        writer.flush();
        fileOutputStream.close();
    }
}
