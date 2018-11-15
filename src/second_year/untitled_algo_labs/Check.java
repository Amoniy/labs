package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class Check {
    public static void main(String[] args) throws IOException {
        String sourceFileName = "txt.in";
        String destinationFileName = "txt.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        InputReader in = new InputReader(fileInputStream);
        OutputWriter out = new OutputWriter(fileOutputStream);

        int n = in.readInt();
        int m = in.readInt();
        int[] pow = new int[11];
        pow[0] = 1;
        for (int i = 1; i < 11; i++) {
            pow[i] = pow[i - 1] * 2;
        }
        int maxSize = 0;
        boolean[] wasOne = new boolean[n];
        boolean[] matrix = new boolean[pow[n]];
        boolean[] expectedMatrix = new boolean[pow[n]];
        for (int i = 0; i < m; i++) {
            int sizeOfSet = in.readInt();
            if (sizeOfSet == 0) {
                matrix[0] = true;
                continue;
            }
            int currentSet = 0;
            int currentSize = 0;
            for (int j = 0; j < sizeOfSet; j++) {
                int currentInt = in.readInt();
                wasOne[currentInt - 1] = true;
                currentSet += pow[currentInt - 1];
                currentSize++;
            }
            matrix[currentSet] = true;
            maxSize = Math.max(maxSize, currentSize);
        }
        if (!matrix[0]) {
            out.printLineln("NO");
            out.flush();
            return;
        }

        int[] countOfOnes = new int[pow[n]];
        for (int i = 0; i < pow[n]; i++) {
            int a = i;
            while (a != 0) {
                if (a % 2 == 1) {
                    countOfOnes[i]++;
                }
                a /= 2;
            }
        }

        int mask = 0;
        for (int i = 0; i < n; i++) {
            if (wasOne[i]) {
                mask += pow[i];
            }
        }
        expectedMatrix[0] = true;
        for (int i = 1; i < pow[n]; i++) {
            if (countOfOnes[i] > maxSize) {
                continue;
            }
            int a = mask;
            int b = i;
            boolean flag = false;
            while (a != 0 || b != 0) {
                if (a % 2 < b % 2) {
                    flag = true;
                    break;
                }
                a /= 2;
                b /= 2;
            }
            if (flag) {
                continue;
            }
            expectedMatrix[i] = true;
        }
        for (int i = 0; i < pow[n]; i++) {
            if (matrix[i] != expectedMatrix[i]) {
                out.printLineln("NO");
                out.flush();
                return;
            }
        }

        out.printLineln("YES");

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
