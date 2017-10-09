package dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class nextpartition {
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

    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "C:\\java\\projects\\src\\labs\\dmlab2\\test.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs\\dmlab2\\test.out", true);
        String sourceFileName = "C:\\java\\projects\\src\\labs\\dmlab2\\test.in";
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));

        ArrayList<Integer> array = new ArrayList<>();
        String s = "";
        while (true) {
            int c = br.read();
            if (c == 61) {
                break;
            }
            s += (c - 48);
        }

        int v = Integer.parseInt(s);
        String str = "";
        while (true) {
            int c = br.read();
            if (c == -1 || c == 13) {
                array.add(Integer.parseInt(str));
                break;
            }
            if (c == 43) {
                array.add(Integer.parseInt(str));
                str = "";
            } else {
                str += (c - 48);
            }
        }

        if (array.get(0) == v) {
            writer.write("No solution");
        } else {
            writer.write("" + v + "=");
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

