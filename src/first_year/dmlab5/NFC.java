package first_year.dmlab5;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Vector;

public class NFC {
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

    public static class Edge {
        int to;
        int symbol;

        public Edge(int to, int symbol) {
            this.symbol = symbol;
            this.to = to;
        }
    }

    private static class MyComp implements Comparator<Integer> {
        public int compare(Integer e1, Integer e2) {
            if (e1 > e2) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "Test.in";
        String destinationFileName = "Test.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int S = reader.readString().charAt(0) - 65;//c 0
        Vector<Integer>[] terminals = new Vector[26];
        Vector<int[]>[] nonTerminals = new Vector[26];
        for (int i = 0; i < 26; i++) {
            terminals[i] = new Vector<>();
            nonTerminals[i] = new Vector<>();
        }
        for (int i = 0; i < n; i++) {
            int left = reader.readString().charAt(0) - 65;
            String right = reader.readString();
            right = reader.readString();
            if (right.length() == 2) {
                nonTerminals[left].add(new int[]{right.charAt(0) - 65, right.charAt(1) - 65});
            } else {
                terminals[left].add(right.charAt(0) - 97);
            }
        }
        //nAij n-iteration A-nonterminal i,j-i,j
        String word = reader.readString();
        long[][][] d = new long[26][word.length()][word.length()];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < word.length(); j++) {
                int charAtJ = word.charAt(j) - 97;
                for (int k = 0; k < terminals[i].size(); k++) {
                    if (terminals[i].get(k) == charAtJ) {
                        d[i][j][j] = 1;
                        break;
                    }
                }
            }
        }
        int mod = 1000000007;
        //int length = 1;
        for (int length = 2; length <= word.length(); length++) {
            for (int i = 0; i <= word.length() - length; i++) {//проход по индексу начала подстроки
                for (int k = 0; k < 26; k++) {//проход по нетерминалам
                    for (int q = 0; q < nonTerminals[k].size(); q++) {//проход по детям нетерминала
                        int firstChild = nonTerminals[k].get(q)[0];
                        int secondChild = nonTerminals[k].get(q)[1];
                        for (int j = i; j < Math.min(i + length - 1, word.length() - 1); j++) {//проход от индекса начала до индекса конца подстроки
                            d[k][i][i + length - 1] += d[firstChild][i][j] * d[secondChild][j + 1][i + length - 1];//mb ne nado len-1 a prosto len
                            d[k][i][i + length - 1] = d[k][i][i + length - 1] % mod;
                        }
                    }
                }
            }
        }
        writer.print(d[S][0][word.length() - 1]);


        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
