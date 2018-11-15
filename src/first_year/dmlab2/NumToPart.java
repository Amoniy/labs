package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.StringTokenizer;

public class NumToPart {
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

        long nextInt() {
            return Long.parseLong(next());
        }

        int nextInte() {
            return Integer.parseInt(next());
        }
    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
        }

        public void printLineln(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }

    }

    public static void main(String[] args) throws Exception {
        String destinationFileName = "NumToPart.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter out = new OutputWriter(fileOutputStream);
        //FileWriter writer = new FileWriter(, true);
        FastScanner in = new FastScanner(new File("NumToPart.in"));
        int n = in.nextInte();
        long k = in.nextInt();
        int[] array = new int[n];
        long[][] d = new long[n + 2][n + 2];

        //filling d[][]

        for (int i = 1; i < n + 2; i++) {
            d[i][i] = 1;
        }
        for (int i = 2; i < n + 2; i++) {
            for (int j = i - 1; j > 0; j--) {
                d[i][j] = d[i][j + 1] + d[i - j][j];
            }
            d[i][0] = d[i][1];
        }

        int[] ans = new int[n];
        int size = 0;
        int last = 1;
        while (n > 0) {
            int newlast = 1;
            while (true) {
                if (d[n][last] - d[n][newlast + 1] <= k) {
                    newlast++;
                } else {
                    break;
                }
            }
            k -= d[n][last] - d[n][newlast];
            last = newlast;
            n -= newlast;
            ans[size] = newlast;
            size++;
        }
        for (int i = 0; i < size - 1; i++) {
            out.print(ans[i]);
            out.print("+");
        }
        out.print(ans[size - 1]);
        out.flush();
        out.close();

    }
}
