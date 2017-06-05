package labs.dmlab3;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class test {
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
        ArrayList<double[]>[] ingoingMap = new ArrayList[n + 1];
        BigDecimal[] probability = new BigDecimal[n + 1];
        BigDecimal[] oldProbability = new BigDecimal[n + 1];
        boolean[] isAbsorbing = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            isAbsorbing[i] = false;
            ingoingMap[i] = new ArrayList<>();
            probability[i] = new BigDecimal(1D / (double) n);
            oldProbability[i] = new BigDecimal(1D / (double) n);
        }
        for (int i = 0; i < m; i++) {
            int from = reader.readInt();
            int to = reader.readInt();
            double prob = Double.parseDouble(reader.readString());
            if (from == to && prob == 1) {
                isAbsorbing[from] = true;
            }
            ingoingMap[to].add(new double[]{from, prob});
        }
        BigDecimal delta;
        boolean somethingHappened;
        boolean parity = true;
        while (true) {
            somethingHappened = false;
            for (int i = 1; i < n + 1; i++) {
                if (parity) {
                    probability[i] = new BigDecimal(0);
                } else {
                    oldProbability[i] = new BigDecimal(0);
                }
                for (int j = 0; j < ingoingMap[i].size(); j++) {
                    if (parity) {
                        delta = oldProbability[(int) ingoingMap[i].get(j)[0]].multiply(new BigDecimal(ingoingMap[i].get(j)[1]));
                        probability[i]=probability[i].add(delta);
                    } else {
                        delta = probability[(int) ingoingMap[i].get(j)[0]].multiply(new BigDecimal(ingoingMap[i].get(j)[1]));
                        oldProbability[i]=oldProbability[i].add(delta);
                    }

                }
                if ((Math.abs((oldProbability[i].subtract(probability[i])).doubleValue())) >= 0.000005) {
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
        BigDecimal totalProbability = new BigDecimal(0);
        for (int i = 1; i < n + 1; i++) {
            if (isAbsorbing[i]) {
                totalProbability=totalProbability.add(probability[i]);
            }
        }

        for (int i = 1; i < n + 1; i++) {
            if (!isAbsorbing[i]) {
                writer.print("0.0\n");
            } else {
                writer.print((probability[i].divide(totalProbability,5,BigDecimal.ROUND_HALF_UP)).doubleValue() + "\n");
            }
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}