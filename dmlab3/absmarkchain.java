package labs.dmlab3;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class absmarkchain {
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

    //C:\java\projects\src\labs\dmlab3\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "absmarkchain.in";
        String destinationFileName = "absmarkchain.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        boolean[] isAbsorbing = new boolean[n];
        double[][] map = new double[m][3];
        for (int i = 0; i < n; i++) {
            isAbsorbing[i] = false;
        }
        for (int i = 0; i < m; i++) {
            int from = reader.readInt() - 1;
            int to = reader.readInt() - 1;
            double prob = Double.parseDouble(reader.readString());
            map[i][0] = from;
            map[i][1] = to;
            map[i][2] = prob;
            if (from == to && prob == 1) {
                isAbsorbing[from] = true;
            }
        }
        int countOfAbsorbers = 0;
        int countOfNonAbsorbers = 0;
        int[] position = new int[n];
        for (int i = 0; i < n; i++) {
            if (isAbsorbing[i]) {
                position[i] = countOfAbsorbers;
                countOfAbsorbers++;
            } else {
                position[i] = countOfNonAbsorbers;
                countOfNonAbsorbers++;
            }
        }
        double[][] R = new double[n][n];
        double[][] Q = new double[n][n];
        for (int i = 0; i < m; i++) {
            if (isAbsorbing[(int) map[i][1]]) {
                if (!isAbsorbing[(int) map[i][0]]) {
                    R[position[(int) map[i][0]]][position[(int) map[i][1]]] = map[i][2];//доправить ишки-1
                }
            } else {
                Q[position[(int) map[i][0]]][position[(int) map[i][1]]] = map[i][2];
            }
        }
        double[][] N = new double[countOfNonAbsorbers][countOfNonAbsorbers];
        double[][] E = new double[countOfNonAbsorbers][countOfNonAbsorbers];
        for (int i = 0; i < countOfNonAbsorbers; i++) {
            N[i][i] = 1;
            E[i][i] = 1;
            for (int j = 0; j < countOfNonAbsorbers; j++) {
                E[i][j] -= Q[i][j];
            }
        }
        for (int i = 0; i < countOfNonAbsorbers; i++) {
            if (E[i][i] != 1) {
                double mul = E[i][i];
                for (int j = 0; j < countOfNonAbsorbers; j++) {
                    E[i][j] /= mul;
                    N[i][j] /= mul;
                }
            }
            for (int row = 0; row < countOfNonAbsorbers; row++) {
                if (i != row) {
                    double mul = E[row][i];
                    for (int j = 0; j < countOfNonAbsorbers; j++) {
                        E[row][j] -= mul * E[i][j];
                        N[row][j] -= mul * N[i][j];
                    }
                }
            }
        }
        double[][] G = new double[countOfNonAbsorbers][countOfAbsorbers];
        for (int i = 0; i < countOfNonAbsorbers; i++) {
            for (int j = 0; j < countOfAbsorbers; j++) {
                G[i][j] = 0;
                for (int k = 0; k < countOfNonAbsorbers; k++) {
                    G[i][j] += N[i][k] * R[k][j];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            double prob = 0;
            if (isAbsorbing[i]) {
                for (int j = 0; j < countOfNonAbsorbers; j++) {
                    prob += G[j][position[i]];
                }
                prob++;
                prob /= n;
            }
            writer.print(prob + "\n");
        }


        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
