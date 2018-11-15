package second_year.sixth;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class ChineseTheorem {

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

    public static void main(String[] args) throws IOException {
        InputReader br = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        int a = br.readInt();
        int b = br.readInt();
        int n = br.readInt();
        int m = br.readInt();
        int M = n * m;
        int M1 = m;
        int M2 = n;
        // M1*y1=a1 mod(m1)
        // y1(p + q)=a1 mod(m1)
        // y1 p = a1 mod(m1) // q кратно m1
        int p = 0;
        for (; p < M1; p++) {
            if ((M1 - p) % n == 0) {
                break;
            }
        }
        int y1 = 1;
        for (; y1 < Integer.MAX_VALUE; y1++) {
            if ((y1 * p) % n == a) {
                break;
            }
        }
        p = 0;
        for (; p < M2; p++) {
            if ((M2 - p) % m == 0) {
                break;
            }
        }
        int y2 = 1;
        for (; y2 < Integer.MAX_VALUE; y2++) {
            if ((y2 * p) % m == b) {
                break;
            }
        }
        int ans = (M1 * y1 + M2 * y2) % M;
        writer.printLineln(ans);
        writer.close();
    }
}
