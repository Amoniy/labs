package lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class lcs {
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

    public static class element {
        int number;
        int[] inputs;
        int[] output;

        public element(int number, int[] inputs, int[] output) {
            this.number = number;
            this.inputs = inputs;
            this.output = output;
        }
    }

    public static int binIterative(int x, int[] array) {
        int l = -1;
        int r = array.length;
        while (r > l + 1) {
            int m = (l + r) / 2;
            if (array[m] >= x) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }

    //C:\java\projects\src\labs\lab4\lis.in
    public static void main(String[] args) throws Exception {
        String sourceFileName = "C:\\java\\projects\\src\\labs\\lab4\\lis.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs\\lab4\\lis.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs\\lab4\\lis.out", true);
        InputReader in = new InputReader(fileInputStream);
        int n = in.readInt();
        int[] first = new int[n];
        for (int i = 0; i < n; i++) {
            first[i] = in.readInt();
        }
        int m = in.readInt();
        int[] second = new int[m];
        for (int i = 0; i < m; i++) {
            second[i] = in.readInt();
        }
        int[][] d = new int[n + 1][m + 1];
        int[][] lastFirstIndex = new int[n + 1][m + 1];
        int[][] lastSecondIndex = new int[n + 1][m + 1];
        int firstMax = 0;
        int max = 0;
        int secondMax = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (first[i - 1] == second[j - 1]) {
                    d[i][j] = d[i - 1][j - 1] + 1;
                    lastFirstIndex[i][j] = i - 1;
                    lastSecondIndex[i][j] = j - 1;
                    if (d[i][j] > max) {
                        max = d[i][j];
                        firstMax = i;
                        secondMax = j;
                    }
                } else if (d[i - 1][j] > d[i][j - 1]) {
                    d[i][j] = d[i - 1][j];
                    lastFirstIndex[i][j] = i - 1;
                    lastSecondIndex[i][j] = j;
                } else {
                    d[i][j] = d[i][j - 1];
                    lastFirstIndex[i][j] = i;
                    lastSecondIndex[i][j] = j - 1;
                }
            }
        }
        writer.write(d[firstMax][secondMax] + "\n");
        int firstPointer = firstMax;
        int secondPointer = secondMax;
        int[] sequence = new int[d[firstMax][secondMax]];
        for (int i = 0; i < d[firstMax][secondMax]; ) {
            if (first[firstPointer - 1] == second[secondPointer - 1]) {
                sequence[i] = first[firstPointer - 1];
                int temp = firstPointer;
                firstPointer--;
                secondPointer--;
                i++;
            } else if (lastFirstIndex[firstPointer][secondPointer] == firstPointer - 1) {
                firstPointer--;
            } else {
                secondPointer--;
            }
        }
        for (int i = d[firstMax][secondMax] - 1; i >= 0; i--) {
            writer.write(sequence[i] + " ");
        }
        writer.flush();
    }
}
