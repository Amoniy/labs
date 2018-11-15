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
import java.util.HashSet;
import java.util.InputMismatchException;

public class CheckSquareTime {

    static boolean[] wasVisited;
    static int[] pow;
    static OutputWriter out;
    static boolean[] matrix;
    static boolean[][] ones;

    static void findIncludedSets(int set) {
        if (wasVisited[set]) {
            return;
        }
        for (int i = 0; i < ones[0].length; i++) {
            if (ones[set][i]) {
                int newSet = set - pow[ones[0].length - i - 1];
                if (matrix[newSet] == false) {
                    out.printLineln("NO");
                    out.flush();
                    System.exit(0);
                }
                findIncludedSets(newSet);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String sourceFileName = "check.in";
        String destinationFileName = "check.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        InputReader in = new InputReader(fileInputStream);
        out = new OutputWriter(fileOutputStream);

        int n = in.readInt();
        int m = in.readInt();
        pow = new int[11];
        pow[0] = 1;
        for (int i = 1; i < 11; i++) {
            pow[i] = pow[i - 1] * 2;
        }
        boolean[] wasOne = new boolean[n];
        matrix = new boolean[pow[n]];
        wasVisited = new boolean[pow[n]];
        ones = new boolean[pow[n]][n];
        for (int i = 0; i < m; i++) {
            int sizeOfSet = in.readInt();
            if (sizeOfSet == 0) {
                matrix[0] = true;
                continue;
            }
            int currentSet = 0;
            for (int j = 0; j < sizeOfSet; j++) {
                int currentInt = in.readInt();
                wasOne[currentInt - 1] = true;
                currentSet += pow[currentInt - 1];
            }
            matrix[currentSet] = true;
        }
        if (!matrix[0]) { // 1
            out.printLineln("NO");
            out.flush();
            return;
        }

        int[] countOfOnes = new int[pow[n]];

        HashSet<Integer>[] sets = new HashSet[pow[n]];
        for (int i = 0; i < pow[n]; i++) {
            sets[i] = new HashSet<>();
            int a = i;
            int currentIndex = 1;
            while (a != 0) {
                if (a % 2 == 1) {
                    countOfOnes[i]++;
                    sets[i].add(currentIndex);
                    ones[i][n - currentIndex] = true;
                }
                a /= 2;
                currentIndex++;
            }
        }

        for (int i = 0; i < pow[n]; i++) { // 2
            if (matrix[i]) {
                findIncludedSets(i);
            }
        }

        HashSet<Integer>[][] xWithoutY = new HashSet[pow[n]][pow[n]];
        for (int i = 0; i < pow[n]; i++) {
            for (int j = 0; j < pow[n]; j++) {
                xWithoutY[i][j] = sets[i & (~j)];
            }
        }
        for (int i = 0; i < pow[n]; i++) { // 3
            for (int j = 0; j < pow[n]; j++) {
                if (countOfOnes[j] < countOfOnes[i] && matrix[j] && matrix[i]) {
                    int index = j;
                    boolean wasOnce = false;
                    for (int k : xWithoutY[i][j]) {
                        index += pow[k - 1];
                        if (matrix[index]) {
                            wasOnce = true;
                            break;
                        }
                        index -= pow[k - 1];
                    }
                    if (!wasOnce) {
                        out.printLineln("NO");
                        out.flush();
                        return;
                    }
                }
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
