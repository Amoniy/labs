package second_year.sixth;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class MorePrimes {

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

    static int blocksSize = 10000;

    public static void main(String[] args) throws IOException {
        InputReader br = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        int n = br.readInt();
        int x = br.readInt();
        int size = (int) Math.sqrt(n);
        boolean[] isNotPrime = new boolean[32000];
        boolean[] isNotPrimeBlock;
        int[] primes = new int[32000];
        int primeCount = 0;
        int ans = 0;
        for (int i = 2; i <= size; i++) {
            if (!isNotPrime[i]) {
                primes[primeCount] = i;
                primeCount++;
                for (int j = i * i; j <= size && j > 0; j += i) {
                    isNotPrime[j] = true;
                }
            }
        }
        int border = n / blocksSize;
        for (int i = 0; i <= border; i++) {
            isNotPrimeBlock = new boolean[blocksSize];
            int startingIndex = i * blocksSize;
            for (int j = 0; j < primeCount; j++) {
                int k = Math.max((startingIndex + primes[j] - 1) / primes[j], 2) * primes[j] - startingIndex;
                while (k < blocksSize) {
                    isNotPrimeBlock[k] = true;
                    k+=primes[j];
                }
            }
            if (i == 0) {
                isNotPrimeBlock[0] = true;
                isNotPrimeBlock[1] = true;
            }
            for (int j = 0; j < blocksSize && startingIndex + j <= n; j++) {
                if (!isNotPrimeBlock[j]) {
                    ans = ans * x + (j + i * blocksSize);
//                    System.out.println(j + i * blocksSize);
                }
            }
        }

        writer.printLineln(ans);
        writer.close();
    }
}
