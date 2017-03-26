package labs.lab4;

import java.io.*;
import java.util.InputMismatchException;

public class palindrome {
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

    public static int max(int a, int b) {
        if (a < b) {
            return b;
        } else {
            return a;
        }
    }

    //C:\java\projects\src\labs\lab4\lis.in
    public static void main(String[] args) throws Exception {
        String sourceFileName = "palindrome.in";
        String destinationFileName = "palindrome.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("palindrome.out", true);
        InputReader in = new InputReader(fileInputStream);
        String toSearch = br.readLine();
        int[][] d = new int[toSearch.length()][toSearch.length()];
        for (int i = 0; i < toSearch.length(); i++) {
            d[i][i] = 1;
        }
        for (int i = toSearch.length() - 1; i >= 0; i--) {
            for (int j = i + 1; j < toSearch.length(); j++) {
                if (toSearch.charAt(i) == toSearch.charAt(j)) {
                    d[i][j] = d[i + 1][j - 1] + 2;
                } else {
                    d[i][j] = max(d[i + 1][j], d[i][j - 1]);
                }
            }
        }
        writer.write(d[0][toSearch.length() - 1] + "\n");
        char[] sequence = new char[d[0][toSearch.length() - 1]];
        int fPointer = 0;
        int sPointer = toSearch.length() - 1;
        int i = 0;
        int j = 0;
        while (i + j < d[0][toSearch.length() - 1]) {
            if (fPointer == sPointer) {
                sequence[i]=toSearch.charAt(fPointer);
                i++;
            } else if ((d[fPointer][sPointer] == d[fPointer + 1][sPointer - 1] + 2)&&(toSearch.charAt(fPointer)==toSearch.charAt(sPointer))) {
                sequence[i] = toSearch.charAt(fPointer);
                sequence[d[0][toSearch.length() - 1] - j - 1] = toSearch.charAt(sPointer);
                fPointer++;
                sPointer--;
                i++;
                j++;
            } else if (d[fPointer][sPointer] == d[fPointer + 1][sPointer]) {
                fPointer++;
            } else if (d[fPointer][sPointer] == d[fPointer][sPointer - 1]) {
                sPointer--;
            }
        }
        for(i=0;i<d[0][toSearch.length() - 1];i++){
            writer.write(sequence[i]);
        }
        writer.flush();
    }
}
