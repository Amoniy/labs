package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Vector;

public class Cycles {

    private static int maxSize;
    private static int maxInt;

    private static boolean check(int bigSet, Vector<Integer> cycles) {
        for (int i = 0; i < cycles.size(); i++) {
            int smallSet = cycles.get(i);
            if (((((smallSet & bigSet) | (~smallSet)) << (32 - maxSize)) >>> (32 - maxSize)) == maxInt) { // большее включает меньшее
                return false;
            }
        }
        return true;
    }

    public static class MyComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            return Integer.compare(-a[0], -b[0]);
        }
    }

    //    11111111111111111111111111111111
//    12345678901234567890123456789012 = 32
    public static void main(String[] args) throws IOException {
        String sourceFileName = "cycles.in";
        String destinationFileName = "cycles.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        InputReader in = new InputReader(fileInputStream);
        OutputWriter out = new OutputWriter(fileOutputStream);
//        maxSize=4;
//        int smallSet = 7;
//        int bigSet = 4;
//        System.out.println((((smallSet & bigSet) | (~smallSet)) << (32 - maxSize)) >>> (32 - maxSize));
//        maxSize=4;
//        System.out.println((2 & 3));
//        System.out.println((~2));
//        System.out.println((2 | 1));
//        System.out.println(((2 & 3) | (~2)));
//        System.out.println(((((2 & 3) | (~2)) << (32 - maxSize)) >>> (32 - maxSize)));
//        System.out.println("2 = " + Integer.toBinaryString(2));
//        System.out.println("-3 = " + Integer.toBinaryString(-3));
//        System.out.println("-1 = " + Integer.toBinaryString(-1));
//        System.out.println("7 = " + Integer.toBinaryString(7));
        int n = in.readInt();
        maxSize = n;
        int m = in.readInt();
        Vector<Integer> cycles = new Vector<>();
        int[] pow = new int[21];
        pow[0] = 1;
        for (int i = 1; i < 21; i++) {
            pow[i] = pow[i - 1] * 2;
        }
        maxInt = pow[n] - 1;
        int[][] weights = new int[n][2];
        for (int i = 0; i < n; i++) {
            weights[i][0] = in.readInt();
            weights[i][1] = i + 1;
        }
        boolean[] isCycle = new boolean[pow[n]];
        for (int i = 0; i < m; i++) {
            int size = in.readInt();
            int index = 0;
            for (int j = 0; j < size; j++) {
                index += pow[in.readInt() - 1];
            }
            cycles.add(index);
            isCycle[index] = true;
        }
        Arrays.sort(weights, new MyComparator());
        int currentIndex = 0;
        int currentSum = 0;
        boolean[] wasVisited = new boolean[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (wasVisited[j]) {
                    continue;
                }
                if (!isCycle[currentIndex + pow[weights[j][1] - 1]] && check(currentIndex + pow[weights[j][1] - 1], cycles)) {
                    currentIndex += pow[weights[j][1] - 1];
                    currentSum += weights[j][0];
                    wasVisited[j] = true;
                }
            }
        }
        out.printLineln(currentSum);
        out.flush();
    }

    static class InputReader {

        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return readString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
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
}
