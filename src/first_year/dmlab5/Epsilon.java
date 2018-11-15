package first_year.dmlab5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.Vector;

public class Epsilon {
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
        String sourceFileName = "Epsilon.in";
        String destinationFileName = "Epsilon.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        int n = 27;
        Vector<String>[] map = new Vector[n + 1];
        boolean[] acceptable = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            map[i] = new Vector<>();
            acceptable[i] = false;
        }
        String[] temp = br.readLine().split(split);
        int m = Integer.parseInt(temp[0]);
        int S = temp[1].charAt(0) % 64;
        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(split);
            int left = temp[0].charAt(0) % 64;
            if (temp.length == 2) {
                acceptable[left] = true;
            } else {
                String right = temp[2];
                String upperCaseRight = right.toUpperCase();
                if (right.equals(upperCaseRight)) {
                    map[left].add(right);
                }
            }
        }
        boolean somethingChanged = true;
        while (somethingChanged) {
            somethingChanged = false;
            for (int i = 1; i < n; i++) {
                if (acceptable[i]) {
                    continue;
                }
                for (int j = 0; j < map[i].size(); j++) {
                    String string = map[i].get(j);
                    boolean iscool = true;
                    for (int k = 0; k < string.length(); k++) {
                        if (!acceptable[string.charAt(k) % 64]) {
                            iscool = false;
                            break;
                        }
                    }
                    if (iscool) {
                        acceptable[i] = true;
                        somethingChanged = true;
                    }
                }
            }
        }
        for (int i = 1; i < n; i++) {
            if (acceptable[i]) {
                char a = (char) (i + 64);
                writer.print(a);
                writer.print(" ");
            }
        }

        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
