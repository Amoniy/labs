package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class HashFunction {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);
        String string = in.readString();
        int m = in.readInt();
        int size = string.length();
        final int hashValue = 31;
        long[] hashFunction = new long[size];
        long[] hash = new long[size];
        hashFunction[0] = string.charAt(0);
        hash[0] = 1;
        for (int i = 1; i < size; i++) {
            hash[i] = hash[i - 1] * hashValue;
        }
        for (int i = 1; i < size; i++) {
            hashFunction[i] = string.charAt(i) * hash[i] + hashFunction[i - 1];
        }
        for (int i = 0; i < m; i++) {
            int firstLeft = in.readInt() - 1;
            int firstRight = in.readInt() - 1;
            int secondLeft = in.readInt() - 1;
            int secondRight = in.readInt() - 1;
            if (secondRight - secondLeft != firstRight - firstLeft) {
                out.printLineln("No");
            } else {
                long firstSubtraction = 0;
                long secondSubtraction = 0;
                if (firstLeft > 0) {
                    firstSubtraction = hashFunction[firstLeft - 1];
                }
                if (secondLeft > 0) {
                    secondSubtraction = hashFunction[secondLeft - 1];
                }
                long firstHash = hashFunction[firstRight] - firstSubtraction;
                long secondHash = hashFunction[secondRight] - secondSubtraction;
                if (firstLeft > secondLeft) {
                    if (firstHash == secondHash * hash[firstLeft - secondLeft]) {
                        out.printLineln("Yes");
                    } else {
                        out.printLineln("No");
                    }
                } else {
                    if (firstHash * hash[secondLeft - firstLeft] == secondHash) {
                        out.printLineln("Yes");
                    } else {
                        out.printLineln("No");
                    }
                }
            }
        }

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
