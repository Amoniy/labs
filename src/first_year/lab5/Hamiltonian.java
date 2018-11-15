package first_year.lab5;

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
import java.util.Vector;

public class Hamiltonian {
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

    public static void main(String[] args) throws Exception {
        String sourceFileName = "Hamiltonian.in";
        String destinationFileName = "Hamiltonian.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        Vector<Integer>[] map = new Vector[n + 1];
        Vector<Integer>[] ingoingMap = new Vector[n + 1];
        boolean[] used = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            map[i] = new Vector<>();
            ingoingMap[i] = new Vector<>();
            used[i] = false;
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            map[left].add(right);
            ingoingMap[right].add(left);
        }
        int countOfUsedVertices = 1;
        int startingVertice = 0;
        for (int i = 1; i < n + 1; i++) {
            if (ingoingMap[i].size() == 0) {
                if (startingVertice == 0) {
                    startingVertice = i;
                } else {
                    startingVertice = 0;
                    break;
                }
            }
        }
        while (true) {
            if (startingVertice == 0) {
                break;
            }
            used[startingVertice] = true;

            for (int i = 0; i < map[startingVertice].size(); i++) {
                int nextVertice = 0;
                for (int j = 0; j < ingoingMap[map[startingVertice].get(i)].size(); j++) {
                    if (!used[ingoingMap[map[startingVertice].get(i)].get(j)]) {
                        nextVertice = 0;
                        break;
                    }
                    nextVertice = map[startingVertice].get(i);
                }
                if (nextVertice != 0) {
                    countOfUsedVertices++;
                    startingVertice = nextVertice;
                }
            }


            if (map[startingVertice].size() == 0) {
                break;
            }
        }
        if (countOfUsedVertices == n) {
            writer.print("YES");
        } else {
            writer.print("NO");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
