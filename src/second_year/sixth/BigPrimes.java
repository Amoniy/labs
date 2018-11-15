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
import java.util.Random;

public class BigPrimes {

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

    private static boolean check(long x) {
        if (x == 1) {
            return false;
        }
        if (x == 2) {
            return true;
        }
        if (x % 2 == 0) {
            return false;
        }
        long counter = x - 1;
        int s = 0;
        while (counter % 2 == 0) {
            s++;
            counter /= 2;
        }
        long t = (x - 1) / (long) Math.pow(2, s);
        for (int j = 0; j < log; j++) {
            boolean flag = false;
            int check;
            if (x > Integer.MAX_VALUE) {
                check = Integer.MAX_VALUE;
            } else {
                check = (int) x;
            }
            long randomNum = 2 + new Random().nextInt(check - 2);
            long mod = new BigInteger(String.valueOf(randomNum)).modPow(new BigInteger(String.valueOf(t)), new BigInteger(String.valueOf(x))).longValue();
//            long mod = quickPowMod(randomNum, t, x);
            if (mod == 1 || mod == x - 1) {
                continue;
            }
            for (int k = 0; k < s - 1; k++) {
//                mod = quickPowMod(mod, 2, x);
                mod = new BigInteger(String.valueOf(mod)).modPow(new BigInteger(String.valueOf(2)), new BigInteger(String.valueOf(x))).longValue();
                if (mod == 1) {
                    return false;
                }
                if (mod == x - 1) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static int log = 100;

    public static void main(String[] args) throws IOException {
        InputReader br = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        int n = br.readInt();
        for (int i = 0; i < n; i++) {
            long x = Long.parseLong(br.readString());
            if (check(x)) {
                writer.printLineln("YES");
            } else {
                writer.printLineln("NO");
            }
        }
        writer.close();
    }
}
