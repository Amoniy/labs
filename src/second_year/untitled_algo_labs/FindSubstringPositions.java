package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;

public class FindSubstringPositions {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);
        String subString = in.readString();
        String stringToFindIn = in.readString();
        String string = subString + "#" + stringToFindIn;
        int size = string.length();
        int subSize = subString.length();
        int[] ZFunction = new int[size];
        ZFunction[0] = 0;
        int l = 0;
        int r = 0;
        int count = 0;
        Queue<String> queue = new LinkedList<>();
        for (int i = 1; i < size; i++) {
            if (i <= r) {
                ZFunction[i] = Math.min(ZFunction[i - l], r - i + 1);

            }
            if (i + ZFunction[i] < size) {
                while (string.charAt(ZFunction[i]) == string.charAt(i + ZFunction[i])) {
                    ZFunction[i]++;
                    if (i + ZFunction[i] == size) {
                        break;
                    }
                }
            }
            if (i + ZFunction[i] - 1 > r) {
                l = i;
                r = i + ZFunction[i] - 1;
            }
            if (ZFunction[i] == subSize) {
                count++;
                queue.add((i - subSize) + " ");
            }
        }
        out.printLineln(count);
        while (queue.size() > 0) {
            out.printLine(queue.poll());
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
