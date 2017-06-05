package labs.dmlab3;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class markchain {
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

        public void print(Object...objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object...objects) {
            print(objects);
        }
        public void printLineln(Object...objects) {
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
        String sourceFileName = "markchain.in";
        String destinationFileName = "markchain.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        OutputWriter writer=new OutputWriter(fileOutputStream);
        InputReader reader=new InputReader(fileInputStream);
        int n = reader.readInt();
        double[][] map = new double[n][n];
        double[] probability = new double[n];
        double[] oldProbability = new double[n];
        for (int i = 0; i < n; i++) {
            probability[i] = 1D / (double) n;
            oldProbability[i] = probability[i];
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = Double.parseDouble(reader.readString());
            }
        }
        double delta;
        boolean somethingHappened;
        boolean parity = true;
        while (true) {
            somethingHappened = false;
            for (int i = 0; i < n; i++) {
                if (parity) {
                    probability[i] = 0;
                } else {
                    oldProbability[i] = 0;
                }
                for (int j = 0; j < n; j++) {
                    if (parity) {
                        delta = oldProbability[j] * map[j][i];
                        probability[i] += delta;
                    } else {
                        delta = probability[j] * map[j][i];
                        oldProbability[i] += delta;
                    }

                }
                if ((Math.abs(oldProbability[i]-probability[i])) >= 0.0001) {
                    somethingHappened = true;
                }
            }
            if (parity) {
                parity = false;
            } else {
                parity = true;
            }
            if (!somethingHappened) {
                break;
            }
        }
        for (int i = 0; i < n; i++) {
            writer.print(probability[i]+" ");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
