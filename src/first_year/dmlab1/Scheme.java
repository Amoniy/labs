package first_year.dmlab1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class Scheme {
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

    public static void main(String[] args) {
        OutputWriter out = new OutputWriter(System.out);
        InputReader in = new InputReader(System.in);
        int n = in.readInt();
        int v = 0;
        int f = 0;
        int[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304};
        int[][] inputs = new int[n + 1][5];
        int[][] outputs = new int[n + 1][32];
        int[] vars = new int[n];
        int[] functions = new int[n];
        int[] sizesofinputs = new int[n + 1];
        while (v + f < n) {
            int k = in.readInt();
            if (k != 0) {
                for (int j = 0; j < k; j++) {
                    inputs[v + f + 1][j] = in.readInt();
                }
                for (int j = 0; j < powers[k]; j++) {
                    outputs[v + f + 1][j] = in.readInt();
                }
                sizesofinputs[v + f + 1] = k;
                functions[f] = v + f + 1;
                f++;
            } else {
                vars[v] = v + f + 1;
                v++;
            }
        }

        int[] rows = new int[n + 1];
        for (int i = 0; i < v; i++) {
            rows[vars[i]] = 0;
        }
        for (int i = 0; i < f; i++) {
            int max = 0;
            for (int j = 0; j < sizesofinputs[functions[i]]; j++) {
                if (rows[inputs[functions[i]][j]] > max) {
                    max = rows[inputs[functions[i]][j]];
                }
            }
            rows[functions[i]] = max + 1;
        }
        out.printLineln(rows[n]);
        int[] array = new int[n + 1];
        for (int i = 0; i < v; i++) {
            array[vars[i]] = 0;//starting string
        }
        for (int j = 0; j < f; j++) {
            int input = 0;
            for (int k = 0; k < sizesofinputs[functions[j]]; k++) {
                if (array[inputs[functions[j]][k]] == 1) {
                    input += powers[sizesofinputs[functions[j]] - k - 1];
                }
            }
            array[functions[j]] = outputs[functions[j]][input];
        }
        out.printLine(array[n]);//

        for (int i = 1; i < powers[v]; i++) {
            boolean k = true;
            for (int j = v - 1; j >= 0; j--) {
                if (k) {
                    if (array[vars[j]] == 0) {
                        array[vars[j]] = 1;
                        k = false;
                    } else {
                        array[vars[j]] = 0;
                    }
                } else {
                    array[vars[j]] = array[vars[j]];
                }
            }//vars done

            for (int j = 0; j < f; j++) {
                int input = 0;
                for (int q = 0; q < sizesofinputs[functions[j]]; q++) {
                    if (array[inputs[functions[j]][q]] == 1) {
                        input += powers[sizesofinputs[functions[j]] - q - 1];
                    }
                }
                array[functions[j]] = outputs[functions[j]][input];
            }
            out.printLine(array[n]);
        }
        out.flush();
        out.close();
    }
}

