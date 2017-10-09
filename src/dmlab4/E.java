package dmlab4;

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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Vector;

public class E {
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

    //C:\java\projects\src\labs\lab5\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "test.in";
        String destinationFileName = "test.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        int k = reader.readInt();
        int l = reader.readInt();
        Vector<Edge>[] map = new Vector[n + 1];
        Vector<Vector<Edge>> definedMap = new Vector<>();
        boolean[] acceptable = new boolean[n + 1];
        ArrayList<Integer>[] queue = new ArrayList[l + 1];
        for (int i = 0; i < l + 1; i++) {
            queue[i] = new ArrayList<>();
        }
        for (int i = 0; i < n + 1; i++) {
            map[i] = new Vector<>();
            acceptable[i] = false;
        }
        for (int i = 1; i < k + 1; i++) {
            acceptable[reader.readInt()] = true;
        }
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            int symbol = ((int) reader.read()) % 97;
            map[left].add(new Edge(right, symbol));
            //backMap[right].add(new Edge(left, symbol));
        }
        for (int i = 0; i < 5000; i++) {
            definedMap.add(new Vector<>());
        }
        HashSet<String> usedSets = new HashSet<>();//////////////////////
        HashMap<Integer, Boolean> isAcceptableSet = new HashMap<>();
        HashMap<String, Integer> setToIndex = new HashMap<>();///////////
        String tempset;//////////////////////////////
        tempset = "1*";
        if (acceptable[1]) {
            isAcceptableSet.put(0, true);
        } else {
            isAcceptableSet.put(0, false);
        }
        usedSets.add(tempset);
        setToIndex.put(tempset, 0);
        int number = 1;

        ArrayList<String> queueSet = new ArrayList<>();/////////
        queueSet.add(tempset);
        while (queueSet.size() != 0) {
            tempset = queueSet.get(0);
            queueSet.remove(0);
            ArrayList<Integer>[] table = new ArrayList[26];//////////////
            for (int i = 0; i < 26; i++) {
                table[i] = new ArrayList<>();/////////////
            }
            int size = tempset.length();
            for (int j = 0; j < size; j++) {
                String num = "";
                while (tempset.charAt(j) != '*') {
                    num += tempset.charAt(j);
                    j++;
                }
                int nextvertice = Integer.parseInt(num);
                //зафигачить тут норм номера
                for (int i = 0; i < map[nextvertice].size(); i++) {
                    if (!table[map[nextvertice].get(i).symbol].contains(map[nextvertice].get(i).to)) {
                        table[map[nextvertice].get(i).symbol].add(map[nextvertice].get(i).to);
                    }
                }
            }//tempset eto string, a saveset eto arraylist
            for (int i = 0; i < 26; i++) {
                if (table[i].size() > 0) {
                    table[i].sort(new MyComp());
                    String trans = "";
                    for (int j = 0; j < table[i].size(); j++) {
                        trans += table[i].get(j);//то есть транс это строка обозначающая список вершин в которые можно попасть по итой букве
                        trans += "*";
                    }
                    //тут посортить аррей и запихать в строку
                    if (!usedSets.contains(trans)) {
                        boolean flag = false;
                        for (int j = 0; j < table[i].size(); j++) {
                            if (acceptable[table[i].get(j)]) {
                                isAcceptableSet.put(number, true);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            isAcceptableSet.put(number, false);
                        }
                        setToIndex.put(trans, number);
                        String temp = tempset;
                        boolean needToAdd = true;
                        if (needToAdd) {
                            definedMap.get(setToIndex.get(temp)).add(new Edge(number, i));/////////error
                        }
                        number++;
                        usedSets.add(trans);
                        queueSet.add(trans);
                    } else {
                        String temp = tempset;
                        //может тут проверку допилить
                        boolean needToAdd = true;
                        for (int j = 0; j < definedMap.get(setToIndex.get(temp)).size(); j++) {
                            if (definedMap.get(setToIndex.get(temp)).get(j).symbol == i && definedMap.get(setToIndex.get(temp)).get(j).to == setToIndex.get(trans)) {
                                needToAdd = false;
                                break;
                            }
                        }
                        if (needToAdd) {
                            definedMap.get(setToIndex.get(temp)).add(new Edge(setToIndex.get(trans), i));
                        }
                    }
                }
            }
        }

        int[][] count = new int[number][l + 1];

        int totalCount = 0;
        int mod = 1000000007;
        queue[0].add(0);
        count[0][0] = 1;
        for (int i = 0; i < l; i++) {
            while (queue[i].size() > 0) {
                int currentVertice = queue[i].get(0);
                queue[i].remove(0);
                for (int j = 0; j < definedMap.get(currentVertice).size(); j++) {
                    int nextVertice = definedMap.get(currentVertice).get(j).to;
                    if (!queue[i + 1].contains(nextVertice)) {
                        queue[i + 1].add(nextVertice);
                    }
                    count[nextVertice][i + 1] += count[currentVertice][i];
                    count[nextVertice][i + 1] = count[nextVertice][i + 1] % mod;
                }
            }
        }
        for (int i = 0; i < number; i++) {
            if (isAcceptableSet.get(i)) {
                totalCount += count[i][l];
                totalCount = totalCount % mod;
            }
        }
        writer.print(totalCount + "");

        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}