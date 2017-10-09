package dmlab1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Gods_Scheme_of_Elements {
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
        int[] vars = new int[27];
        int[] powers = new int[22];
        int tmp = 1;
        for (int i = 0; i < 22; i++) {
            powers[i] = tmp;
            tmp *= 2;
        }
        element[] functions = new element[27];
        while (v + f < n) {
            int k = in.readInt();
            if (k == 0) {
                vars[v] = v + f + 1;
                v++;
            } else {
                int[] inputs = new int[k];
                for (int i = 0; i < k; i++) {
                    inputs[i] = in.readInt();
                }
                int[] output = new int[powers[k]];
                for (int i = 0; i < powers[k]; i++) {
                    output[i] = in.readInt();
                }
                functions[f] = new element(v + f + 1, inputs, output);
                f++;
            }
        }
        int[][] rows = new int[26][27];
        ArrayList<Integer> notvisited = new ArrayList<>();
        for (int i = 0; i < f; i++) {
            notvisited.add(functions[i].number);
        }

        int[] retranslator = new int[n + 1];
        int count = 0;
        for (int i = 0; i < n + 1; i++) {
            if (functions[count].number == i) {
                retranslator[i] = count;
                count++;
            } else {
                retranslator[i] = -1;
            }
        }

        int row = 1;
        ArrayList<Integer> temp = new ArrayList<>();
        while (true) {
            for (int i = 0; i < notvisited.size(); i++) {
                boolean br = false;
                for (int j = 0; j < (functions[retranslator[notvisited.get(i)]].inputs.length); j++) {
                    if (notvisited.contains(functions[retranslator[notvisited.get(i)]].inputs[j])) {
                        br = true;
                        break;
                    }
                }
                if (!br) {
                    temp.add(notvisited.get(i));
                }
            }
            if (temp.size() == 0) {
                break;
            }
            int qurrentrowsize = 0;
            while (temp.size() > 0) {
                rows[row - 1][qurrentrowsize] = functions[retranslator[temp.get(0)]].number;
                qurrentrowsize++;
                for (int p = 0; p < notvisited.size(); p++) {
                    if (notvisited.get(p) == temp.get(0)) {
                        notvisited.remove(p);
                    }
                }
                temp.remove(0);
            }
            rows[row - 1][26] = qurrentrowsize;
            row++;
            if (notvisited.size() == 0) {
                break;
            }
        }
        int depth = row - 1;

        int pow = powers[v];
        int[][] table = new int[pow][n];
        for (int i = 0; i < pow; i++) {
            int k = i;
            for (int j = v - 1; j >= 0; j--) {
                int x = k % 2;
                k /= 2;
                table[i][vars[j] - 1] = x;
            }
        }

        int x = 0;
        for (int q = 0; q < pow; q++) {
            for (int i = 0; i < row - 2; i++) {
                for (int j = 0; j < rows[i][26]; j++) {
                    int integer_input = 0;
                    int qurrentfunc = retranslator[rows[i][j]];
                    for (int k = functions[qurrentfunc].inputs.length - 1; k >= 0; k--) {
                        integer_input += table[q][functions[qurrentfunc].inputs[k] - 1] * powers[functions[qurrentfunc].inputs.length - 1 - k];
                    }
                    table[q][functions[qurrentfunc].number - 1] = functions[qurrentfunc].output[integer_input];
                }
            }
            for (int j = 0; j < rows[row - 2][26]; j++) {
                int integer_input = 0;
                int qurrentfunc = retranslator[rows[row - 2][j]];
                for (int k = functions[qurrentfunc].inputs.length - 1; k >= 0; k--) {
                    integer_input += table[q][functions[qurrentfunc].inputs[k] - 1] * powers[functions[qurrentfunc].inputs.length - 1 - k];
                }
                table[q][functions[qurrentfunc].number - 1] = functions[qurrentfunc].output[integer_input];
                x = functions[qurrentfunc].number - 1;
            }

        }
        out.printLineln(depth);
        for (int i = 0; i < powers[v]; i++) {
            out.print(table[i][x]);
        }
        out.flush();
        out.close();
    }
}