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
import java.util.HashMap;
import java.util.InputMismatchException;

public class SuffixArray {

    final static int infinity = Integer.MAX_VALUE;

    static int min(int v, int l, int r, int NEEDEDLEFT, int NEEDEDRIGHT, int[] tree) {
        int x = infinity;
        if (l == r && l >= NEEDEDLEFT && r <= NEEDEDRIGHT) {
            return tree[v];
        }

        if (NEEDEDLEFT <= l && NEEDEDRIGHT >= r) {
            return tree[v];
        }

        if (NEEDEDLEFT > r || NEEDEDRIGHT < l) {
            return infinity;
        }

        if (NEEDEDLEFT > l || NEEDEDRIGHT < r) {
            int m = (l + r) / 2;
            return minimum(min(v * 2 + 1, l, m, NEEDEDLEFT, NEEDEDRIGHT, tree), min(v * 2 + 2, m + 1, r, NEEDEDLEFT, NEEDEDRIGHT, tree));
        }
        return x;
    }

    static int[] build(int[] a, int v, int l, int r, int[] ret) {
        if (l == r)
            ret[v] = a[l];
        else {
            int m = (l + r) / 2;
            ret = build(a, v * 2 + 1, l, m, ret);
            ret = build(a, v * 2 + 2, m + 1, r, ret);
            ret[v] = minimum(ret[v * 2 + 1], ret[v * 2 + 2]);
        }
        return ret;
    }

    static int minimum(int f, int s) {
        if (f <= s) {
            return f;
        }
        return s;
    }

    public static void main(String[] args) throws IOException {
        String sourceFileName = "common.in";
        String destinationFileName = "common.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        InputReader in = new InputReader(fileInputStream);
        OutputWriter out = new OutputWriter(fileOutputStream);
        String string = "";
        int countOfStrings = 2;
        for (int i = 1; i <= countOfStrings; i++) {
            String tempString = in.readString();
            string += tempString + (char)('a' - i);
        }
        int[] number = new int[string.length()];
        int currentIndex = 1;
        for (int i = 0; i < string.length(); i++) {
            number[i] = currentIndex;
            if (string.charAt(i) == ('a' - currentIndex)) {
                currentIndex++;
            } // разделитель включается в левую строку. строки от 1 до countOfStrings
        }
        int log = 0;
        int len = string.length();
        int temp = len - 1;
        int sizeOfAlphabet = 26 + countOfStrings;
        while (temp > 0) {
            log++;
            temp /= 2;
        }
        int[] pow = new int[log + 1];
        int mod = 97 - countOfStrings;
        pow[0] = 1;
        for (int i = 1; i < log + 1; i++) {
            pow[i] = pow[i - 1] * 2;
        }
        int[] count = new int[sizeOfAlphabet];
        for (int i = 0; i < len; i++) {
            count[string.charAt(i) % mod]++;
        }
        for (int i = 1; i < sizeOfAlphabet; i++) {
            count[i] += count[i - 1];
        }
        int[] permutation = new int[len]; // permutation[i] = substring starting at i (sorted lexicographically)
        int[] equivalenceClass = new int[len]; // order of substring starting at i
        for (int i = 0; i < len; i++) {
            char character = (char) (string.charAt(i) % mod);
            permutation[count[character] - 1] = i;
            count[character]--;
        }

        equivalenceClass[permutation[0]] = 0;
        for (int i = 1; i < len; i++) {
            if (string.charAt(permutation[i]) != string.charAt(permutation[i - 1])) {
                equivalenceClass[permutation[i]] = equivalenceClass[permutation[i - 1]] + 1;
            } else {
                equivalenceClass[permutation[i]] = equivalenceClass[permutation[i - 1]];
            }
        }

        int currentLen = 1;
        while (currentLen < len) {
            count = new int[len];
            int[] tempPermutation = new int[len];
            for (int i = 0; i < len; i++) {
                count[equivalenceClass[i]]++;
            }
            for (int i = 1; i < len; i++) {
                count[i] += count[i - 1];
            }
            for (int i = len - 1; i >= 0; i--) {
                int index = (permutation[i] - currentLen + len) % len;
                int currentClass = equivalenceClass[index];
                count[currentClass]--;
                tempPermutation[count[currentClass]] = index;
            }
            for (int i = 0; i < len; i++) {
                permutation[i] = tempPermutation[i];
            }

            int[] tempEquivalenceClass = new int[len];
            // tempperm = perm
            tempEquivalenceClass[tempPermutation[0]] = 0;
            for (int i = 1; i < len; i++) {
                int current = tempPermutation[i];
                int prev = tempPermutation[i - 1];
                int mid = (current + currentLen) % len;
                int prevMid = (prev + currentLen) % len;
                if (equivalenceClass[current] != equivalenceClass[prev] || equivalenceClass[mid] != equivalenceClass[prevMid]) {
                    tempEquivalenceClass[current] = tempEquivalenceClass[prev] + 1;
                } else {
                    tempEquivalenceClass[current] = tempEquivalenceClass[prev];
                }
            }
            for (int i = 0; i < len; i++) {
                equivalenceClass[i] = tempEquivalenceClass[i];
            }

            currentLen *= 2;
        }

        int[] lcp = new int[len];
        int[] pos = new int[len];

        for (int i = 0; i < len; i++) {
            pos[permutation[i]] = i;
        }
        int k = 0;
        lcp[len - 1] = infinity;
        for (int i = 0; i < len; i++) {
            if (k > 0) {
                k--;
            }
            if (pos[i] == len - 1) {
                k = 0;
            } else {
                int j = permutation[pos[i] + 1];
                while (Math.max(i + k, j + k) < len) {
                    if (string.charAt(i + k) == string.charAt(j + k)) {
                        k++;
                    } else {
                        break;
                    }
                }
                lcp[pos[i]] = k;
            }
        }

        // segment tree build
        int x = 1;
        for (int i = 1; i < len * 2; i *= 2) {
            x = i;
        }
        int[] array = new int[x];
        for (int i = 0; i < len; i++) {
            array[i] = lcp[i];
        }
        for (int i = len; i < x; i++) {
            array[i] = infinity;
        }
        int[] ret = new int[2 * x - 1];
        int[] tree = build(array, 0, 0, x - 1, ret);
        // segment tree build

        int[] ans = new int[len];
        int currentCount = 0;
        int rightIndex = -1;
        HashMap<Integer, Integer> map = new HashMap<>();
        boolean flag = false;
        for (int i = 0; i < len - 1; i++) {
            while (currentCount != countOfStrings) {
                rightIndex++;
                if (rightIndex == len) {
                    flag = true;
                    break;
                }
                int num = number[permutation[rightIndex]];
                if (map.containsKey(num)) {
                    map.put(num, map.get(num) + 1);
                } else {
                    map.put(num, 1);
                    currentCount++;
                }
            }
            if (flag) {
                break;
            }
            ans[i] = min(0, x - 1, 2 * x - 2, i + x - 1, rightIndex + x - 1 - 1, tree);
            if (map.get(number[permutation[i]]) == 1) {
                map.remove(number[permutation[i]]);
                currentCount--;
            } else {
                map.put(number[permutation[i]], map.get(number[permutation[i]]) - 1);
            }
        }
        int max = 0;
        int ansIndex = 0;
        for (int i = 0; i < len - 1; i++) {
            if (ans[i] > max) {
                max = ans[i];
                ansIndex = i;
            }
        }
        out.printLineln(string.substring(permutation[ansIndex], permutation[ansIndex] + max));

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
