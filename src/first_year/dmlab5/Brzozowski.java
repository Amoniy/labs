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
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Vector;

public class Brzozowski {
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

    public static int[] reverse(int[] list) {
        int m = list[1];
        int k = list[2];
        int numberOfStarts = list[3];
        int[] newList = new int[4 + k + numberOfStarts + 3 * m];
        newList[0] = list[0];
        newList[1] = list[1];
        newList[2] = list[3];
        newList[3] = list[2];
        for (int i = 0; i < numberOfStarts; i++) {
            newList[4 + i] = list[4 + i + k];
        }
        for (int i = 0; i < k; i++) {
            newList[4 + i + numberOfStarts] = list[4 + i];
        }
        for (int i = 0; i < m; i++) {
            newList[4 + i * 3 + k + numberOfStarts] = list[4 + i * 3 + k + numberOfStarts + 1];
            newList[4 + i * 3 + k + numberOfStarts + 1] = list[4 + i * 3 + k + numberOfStarts];
            newList[4 + i * 3 + k + numberOfStarts + 2] = list[4 + i * 3 + k + numberOfStarts + 2];
        }
        return newList;
    }

    public static int[] determine(int[] list) {
        int n = list[0];
        int m = list[1];
        int k = list[2];
        int numberOfStarts = list[3];

        Vector<Edge>[] map = new Vector[n + 1];
        Vector<Vector<Edge>> definedMap = new Vector<>();
        boolean[] acceptable = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            map[i] = new Vector<>();
            acceptable[i] = false;
        }
        for (int i = 1; i < k + 1; i++) {
            acceptable[list[i + 3]] = true;
        }
        for (int i = 0; i < m; i++) {
            int left = list[4 + k + numberOfStarts + i * 3];
            int right = list[4 + k + numberOfStarts + i * 3 + 1];
            int symbol = list[4 + k + numberOfStarts + i * 3 + 2];//не забыть переделать в ебучие циферки
            map[left].add(new Edge(right, symbol));
        }
        for (int i = 0; i < 10000; i++) {
            definedMap.add(new Vector<>());
        }
        HashSet<String> usedSets = new HashSet<>();
        HashMap<Integer, Boolean> isAcceptableSet = new HashMap<>();
        HashMap<String, Integer> setToIndex = new HashMap<>();
        String tempset;
        tempset = "";
        boolean flagForStart = false;

        for (int i = 0; i < numberOfStarts; i++) {
            tempset += list[4 + k + i];
            tempset += "*";
            if (acceptable[list[4 + k + i]]) {
                flagForStart = true;
            }
        }
        if (flagForStart) {
            isAcceptableSet.put(0, true);
        } else {
            isAcceptableSet.put(0, false);
        }
        usedSets.add(tempset);//поменять это место
        setToIndex.put(tempset, 0);
        int number = 1;

        Vector<String> queueSet = new Vector<>();
        queueSet.add(tempset);
        while (queueSet.size() != 0) {
            tempset = queueSet.get(0);
            queueSet.remove(0);
            Vector<Integer>[] table = new Vector[26];
            for (int i = 0; i < 26; i++) {
                table[i] = new Vector<>();
            }
            int size = tempset.length();
            for (int j = 0; j < size; j++) {
                String num = "";
                while (tempset.charAt(j) != '*') {
                    num += tempset.charAt(j);
                    j++;
                }
                int nextvertice = Integer.parseInt(num);
                for (int i = 0; i < map[nextvertice].size(); i++) {
                    if (!table[map[nextvertice].get(i).symbol].contains(map[nextvertice].get(i).to)) {
                        table[map[nextvertice].get(i).symbol].add(map[nextvertice].get(i).to);
                    }
                }
            }
            for (int i = 0; i < 26; i++) {
                if (table[i].size() > 0) {
                    table[i].sort(new MyComp());
                    String trans = "";
                    for (int j = 0; j < table[i].size(); j++) {
                        trans += table[i].get(j);
                        trans += "*";
                    }
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
                            definedMap.get(setToIndex.get(temp)).add(new Edge(number, i));
                        }
                        number++;
                        usedSets.add(trans);
                        queueSet.add(trans);
                    } else {
                        String temp = tempset;
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

        int newN = number;
        int newM = 0;
        int countOfAcceptables = 0;
        Vector<Integer> newAcceptables = new Vector<>();
        int countOfStarters = 1;//!!!!!!!!!
        for (int i = 0; i < number; i++) {
            newM += definedMap.get(i).size();
            if (isAcceptableSet.get(i)) {
                countOfAcceptables++;
                newAcceptables.add(i + 1);
            }
        }
        int[] newList = new int[4 + countOfAcceptables + countOfStarters + newM * 3];
        int counter = 0;
        for (int i = 0; i < number; i++) {
            Vector<Edge> holder = definedMap.get(i);
            for (int j = 0; j < holder.size(); j++) {
                Edge edge = holder.get(j);
                int left = i + 1;
                int right = edge.to + 1;//dlya krasoti
                int symbol = edge.symbol;
                newList[4 + countOfAcceptables + countOfStarters + counter * 3] = left;
                newList[4 + countOfAcceptables + countOfStarters + counter * 3 + 1] = right;
                newList[4 + countOfAcceptables + countOfStarters + counter * 3 + 2] = symbol;
                counter++;
            }
        }
        newList[0] = newN;
        newList[1] = newM;
        newList[2] = countOfAcceptables;
        newList[3] = countOfStarters;// 1 !!!
        for (int i = 0; i < countOfAcceptables; i++) {
            newList[4 + i] = newAcceptables.get(i);
        }
        for (int i = 0; i < countOfStarters; i++) {
            newList[4 + countOfAcceptables + i] = 1;
        }
        return newList;
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "Minimization.in";
        String destinationFileName = "Minimization.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        InputReader reader = new InputReader(fileInputStream);
        int n = reader.readInt();
        int m = reader.readInt();
        int k = reader.readInt();
        int[] list = new int[4 + k + 1 + m * 3];
        list[0] = n;
        list[1] = m;
        list[2] = k;
        list[3] = 1;
        for (int i = 1; i < k + 1; i++) {
            list[3 + i] = reader.readInt();
        }
        list[4 + k] = 1;
        for (int i = 0; i < m; i++) {
            int left = reader.readInt();
            int right = reader.readInt();
            int symbol = ((int) reader.read()) % 97;
            list[4 + k + 1 + 3 * i] = left;
            list[4 + k + 1 + 3 * i + 1] = right;
            list[4 + k + 1 + 3 * i + 2] = symbol;
        }
        int[] answerList = determine(reverse(determine(reverse(list))));
        for (int i = 0; i < 3; i++) {
            writer.print(answerList[i] + " ");
        }
        writer.print("\n");
        int newK = answerList[2];
        for (int i = 0; i < newK; i++) {
            writer.print(answerList[4 + i] + " ");
        }
        writer.print("\n");
        int newM = answerList[1];
        for (int i = 0; i < newM; i++) {
            writer.print(answerList[4 + newK + 1 + 3 * i] + " ");
            writer.print(answerList[4 + newK + 1 + 3 * i + 1] + " ");
            writer.print((char) (answerList[4 + newK + 1 + 3 * i + 2] + 97) + "\n");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
