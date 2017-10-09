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

public class matrix {
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

    public static int max(int a, int b) {
        if (a < b) {
            return b;
        } else {
            return a;
        }
    }


    public static int[] findMin(int[][] d, int[][] sizes, int l, int r) {
        int[] ret = new int[4];
        int count = r - l;
        int min = Integer.MAX_VALUE;
        int firstMarker = 0;
        for (int i = 0; i < count; i++) {
            int temp = d[l][i + l] + d[l + i + 1][r] + sizes[0][l] * sizes[1][r] * sizes[1][l + i];
            if (min > temp) {
                min = temp;
                firstMarker = i + l;
            }
        }
        ret[0] = min;
        ret[1] = l;
        ret[2] = firstMarker;
        ret[3] = r;
        return ret;
    }

    public static void rebuildBrackets(int[][][] prev, int l, int r) {
        if (l != r) {
            brackets[0][l]++;
            brackets[1][r]++;
        }
        if (prev[l][r][1] != -1) {
            rebuildBrackets(prev, prev[l][r][1] + 1, r);
            rebuildBrackets(prev, l, prev[l][r][1]);
        }

    }

    public static int[][] brackets = new int[2][400];//0-lefthandside

    //C:\java\projects\src\labs\lab4\lis.in
    public static void main(String[] args) throws Exception {
        String sourceFileName = "matrix.in";
        String destinationFileName = "matrix.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("matrix.out", true);
        InputReader in = new InputReader(fileInputStream);
        int n = in.readInt();
        int[][] sizes = new int[2][n];
        int[][][] prev = new int[n][n][3];
        int[][] d = new int[n][n];
        for (int i = 0; i < n; i++) {
            sizes[0][i] = in.readInt();
            sizes[1][i] = in.readInt();
        }
        int[][][] multipliedSizes = new int[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                d[i][j] = -1;
                prev[i][j][0] = -1;
                prev[i][j][1] = -1;
                prev[i][j][2] = -1;
            }
        }
        for (int i = 0; i < n; i++) {
            d[i][i] = 0;

        }
        int[] temp;
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                temp = findMin(d, sizes, i, j);
                d[i][j] = temp[0];
                prev[i][j][0] = temp[1];//left
                prev[i][j][1] = temp[2];//marker](
                prev[i][j][2] = temp[3];//right
            }
        }
        rebuildBrackets(prev, 0, n - 1);
        //writer.write(d[0][n - 1] + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < brackets[0][i]; j++) {
                writer.write("(");
            }
            writer.write("A");
            for (int j = 0; j < brackets[1][i]; j++) {
                writer.write(")");
            }
        }
        writer.flush();
    }

}
