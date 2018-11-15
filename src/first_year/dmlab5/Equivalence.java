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
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Equivalence {
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
        String sourceFileName = "Equivalence.in";
        String destinationFileName = "Equivalence.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        int k = reader.readInt();
        int[][] firstMap = new int[n + 1][26];
        boolean[] firstAcceptable = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            firstAcceptable[i] = false;
        }
        for (int i = 1; i < k + 1; i++) {
            firstAcceptable[reader.readInt()] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 26; j++) {
                firstMap[i][j] = 0;
            }
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            int symbol = ((int) reader.read()) % 97;
            firstMap[left][symbol] = right;
        }
        int N = reader.readInt();
        int M = reader.readInt();
        int K = reader.readInt();
        int[][] secondMap = new int[N + 1][26];
        boolean[] secondAcceptable = new boolean[N + 1];
        for (int i = 1; i < N + 1; i++) {
            secondAcceptable[i] = false;
        }
        for (int i = 1; i < K + 1; i++) {
            secondAcceptable[reader.readInt()] = true;
        }
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < 26; j++) {
                secondMap[i][j] = 0;
            }
        }
        for (int i = 0; i < M; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            int symbol = ((int) reader.read()) % 97;
            secondMap[left][symbol] = right;
        }
        boolean isEquivalent = true;
        boolean[] firstVisited = new boolean[n + 1];
        boolean[] secondVisited = new boolean[N + 1];
        for (int i = 0; i < n + 1; i++) {
            firstVisited[i] = false;
        }
        for (int i = 0; i < N + 1; i++) {
            secondVisited[i] = false;
        }
        ArrayList<int[]> queue = new ArrayList<>();
        queue.add(new int[]{1, 1});
        while (queue.size() > 0) {
            int firstVertice = queue.get(0)[0];
            int secondVertice = queue.get(0)[1];
            queue.remove(0);
            firstVisited[firstVertice] = true;
            secondVisited[secondVertice] = true;
            if (firstAcceptable[firstVertice] ^ secondAcceptable[secondVertice]) {
                isEquivalent = false;
                break;
            }
            for (int i = 0; i < 26; i++) {
                int firstChild = firstMap[firstVertice][i];
                int secondChild = secondMap[secondVertice][i];
                if (!firstVisited[firstChild] || !secondVisited[secondChild]) {
                    queue.add(queue.size(), new int[]{firstChild, secondChild});
                }
            }
        }
        if (isEquivalent) {
            writer.print("YES");
        } else {
            writer.print("NO");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
