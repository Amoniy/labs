package second_year.sixth;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.InputMismatchException;

public class RSA {

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

    static int gcd(int a, int b, int[] x, int[] y) {
        if (a == 0) {
            x[0] = 0;
            y[0] = 1;
            return b;
        }
        int[] x1 = new int[1];
        int[] y1 = new int[1];
        int d = gcd(b % a, a, x1, y1);
        x[0] = y1[0] - (b / a) * x1[0];
        y[0] = x1[0];
        return d;
    }

    private static long quickPowMod(long a, long b, long m) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) > 0) {
                res = (res * a) % m;
                b--;
            } else {
                a = (a * a) % m;
                b >>= 1;
            }
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        InputReader br = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        int n = br.readInt();
        int e = br.readInt();
        int C = br.readInt();
        int p = 2;
        int q = 0;
        for (; p < n; p++) {
            if ((n % p == 0)
                    && new BigInteger(String.valueOf(p)).isProbablePrime(100)
                    && new BigInteger(String.valueOf(n / p)).isProbablePrime(100)
                    ) {
                q = n / p;
                break;
            }
        }
        int[] x = new int[1];
        int[] y = new int[1];
        gcd(e, (p - 1) * (q - 1), x, y);
        int d = 0;
        if (x[0] > 0) {
            d = x[0];
        } else {
            d = x[0] + (p - 1) * (q - 1);
        }
        long ans = quickPowMod(C, d, n);
        writer.printLineln(ans);
        writer.close();
    }
}
