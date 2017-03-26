package labs.lab4;

import java.io.*;
import java.util.InputMismatchException;

public class lis {
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

    public static class element {
        int number;
        int[] inputs;
        int[] output;

        public element(int number, int[] inputs, int[] output) {
            this.number = number;
            this.inputs = inputs;
            this.output = output;
        }
    }

    public static int binIterative(int x, int[] array) {
        int l = -1;
        int r = array.length;
        while (r > l + 1) {
            int m = (l + r) / 2;
            if (array[m] >= x) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }
    //C:\java\projects\src\labs\lab4\lis.in
    public static void main(String[] args) throws Exception{
        String sourceFileName = "lis.in";
        String destinationFileName = "lis.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("lis.out", true);

        InputReader in = new InputReader(fileInputStream);
        int n = in.readInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.readInt();
        }
        int[] min = new int[n + 1];
        int[] position = new int[n + 1];
        int[] previous = new int[n];
        min[0] = Integer.MIN_VALUE;
        position[0] = -1;
        int max = 0;
        for (int i = 1; i <= n; i++) {
            min[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < n; i++) {
            int j = binIterative(array[i], min);
            min[j] = array[i];
            position[j] = i;
            previous[i] = position[j - 1];
            if (j > max) {
                max = j;
            }
        }
        int[] sequence = new int[max];
        int pointer = position[max];
        writer.write(max+"\n");
        for (int i = 0; i < max; i++) {
            sequence[i] = array[pointer];
            pointer = previous[pointer];

        }
        for (int i = max - 1; i >= 0; i--) {
            writer.write(sequence[i]+" ");
        }
        writer.flush();
    }
}
