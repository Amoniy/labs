package dmlab5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Vector;

public class useless {
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

    //C:\java\projects\src\labs\lab5\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "useless.in";
        String destinationFileName = "useless.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        OutputWriter writer = new OutputWriter(fileOutputStream);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        int n = 27;
        Vector<String>[] map = new Vector[n + 1];
        Vector<Integer>[] connectionMap = new Vector[n + 1];
        boolean[][] alreadyHasRoute = new boolean[n + 1][n + 1];
        boolean[] acceptable = new boolean[n + 1];
        boolean[] isReachable = new boolean[n + 1];
        boolean[] wasMentioned = new boolean[n + 1];
        boolean[] isAdequate = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                alreadyHasRoute[i][j] = false;
            }
            map[i] = new Vector<>();
            connectionMap[i] = new Vector<>();
            acceptable[i] = false;
            isReachable[i] = false;
            wasMentioned[i] = false;
            isAdequate[i] = false;
        }
        String[] temp = br.readLine().split(split);
        int m = Integer.parseInt(temp[0]);
        int S = temp[1].charAt(0) % 64;
        wasMentioned[S] = true;
        acceptable[S] = true;//whyyyyyyyyyyyyyyyyyyyyyyyyyyyyy
        isReachable[S] = true;
        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(split);
            int left = temp[0].charAt(0) % 64;
            wasMentioned[left] = true;
            if (temp.length == 2) {
                isAdequate[left] = true;
                acceptable[left] = true;//WTF
            } else {
                String right = temp[2];
                String lowerCaseRight = right.toLowerCase();
                if (right.equals(lowerCaseRight)) {//то есть все точно ok
                    isAdequate[left] = true;
                    acceptable[left] = true;
                } else {//возможно не ok
                    map[left].add(right);
                    for (int j = 0; j < right.length(); j++) {
                        char charAtJ = right.charAt(j);
                        if (!Character.isLowerCase(charAtJ)) {
                            wasMentioned[charAtJ % 64] = true;
                        }
                    }
                }
            }
        }
        //
        boolean somethingChanged = true;
        while (somethingChanged) {
            somethingChanged = false;
            for (int i = 1; i < n; i++) {
                if (isAdequate[i]) {
                    continue;
                }
                for (int j = 0; j < map[i].size(); j++) {
                    String string = map[i].get(j);
                    boolean iscool = true;
                    for (int k = 0; k < string.length(); k++) {
                        if (Character.isLowerCase(string.charAt(k))) {
                            continue;
                        }
                        if (!isAdequate[string.charAt(k) % 64]) {
                            iscool = false;
                            break;
                        }
                    }
                    if (iscool) {
                        isAdequate[i] = true;
                        somethingChanged = true;
                        break;
                    }
                }
            }
        }
        for (int i = 1; i < n; i++) {
            if (!isAdequate[i]) {
                map[i] = new Vector<>();
            }//?
            for (int j = 0; j < map[i].size(); j++) {
                String string = map[i].get(j);
                for (int k = 0; k < string.length(); k++) {
                    char c = string.charAt(k);
                    if (Character.isLowerCase(c)) {
                        continue;
                    }
                    if (!isAdequate[c % 64]) {
                        map[i].remove(j);
                        j--;
                        break;
                    }
                }
            }
        }
        for (int i = 1; i < n; i++) {//построение самоu карты
            for (int j = 0; j < map[i].size(); j++) {
                String string = map[i].get(j);
                String upperCaseString = string.toUpperCase();
                //StringBuilder builder = new StringBuilder();
                if (!string.equals(upperCaseString)) {
                    acceptable[i] = true;//то есть есть маленькие и все збс
                    continue;
                }
                for (int q = 0; q < string.length(); q++) {
                    char charAtJ = string.charAt(q);
                    if (!Character.isLowerCase(charAtJ)) {
                        //builder.append(charAtJ);
                        if (!alreadyHasRoute[i][charAtJ % 64]) {
                            connectionMap[i].add(charAtJ % 64);
                            alreadyHasRoute[i][charAtJ % 64] = true;
                        }
                    }
                }
                //strbldr
            }
        }
        somethingChanged = true;
        while (somethingChanged) {
            somethingChanged = false;
            for (int i = 1; i < n; i++) {
                if (acceptable[i]) {
                    continue;
                }
                for (int j = 0; j < map[i].size(); j++) {
                    String string = map[i].get(j);
                    for (int k = 0; k < string.length(); k++) {
                        if (Character.isLowerCase(string.charAt(k))) {
                            continue;
                        }
                        if (acceptable[string.charAt(k) % 64] && isAdequate[string.charAt(k) % 64]) {
                            acceptable[i] = true;
                            somethingChanged = true;
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < map[i].size(); j++) {
                String string = map[i].get(j);
                for (int q = 0; q < string.length(); q++) {
                    char charAtJ = string.charAt(q);
                    if (!Character.isLowerCase(charAtJ)) {
                        if (!(isAdequate[charAtJ % 64] && acceptable[charAtJ % 64])) {
                            map[i].remove(j);
                            j--;
                            break;
                        }
                    }
                }
            }
        }
        /*//
        Vector<Integer> queue = new Vector<>();
        if (isAdequate[S]) {
            queue.add(S);
        }
        while (queue.size() != 0) {
            int start = queue.get(0);
            queue.remove(0);
            for (int i = 0; i < connectionMap[start].size(); i++) {
                int connectedVertice = connectionMap[start].get(i);
                if (!isReachable[connectedVertice] && isAdequate[connectedVertice]) {
                    queue.add(queue.size(), connectedVertice);
                    isReachable[connectedVertice] = true;
                }
            }
        }
        */
        somethingChanged = true;
        while (somethingChanged) {
            somethingChanged = false;
            for (int i = 1; i < n; i++) {
                if (!(isReachable[i])) {
                    continue;
                }
                for (int j = 0; j < map[i].size(); j++) {
                    String string = map[i].get(j);
                    for (int k = 0; k < string.length(); k++) {
                        if (Character.isLowerCase(string.charAt(k))) {
                            continue;
                        }
                        if (!isReachable[string.charAt(k) % 64]) {
                            isReachable[string.charAt(k) % 64] = true;
                            somethingChanged = true;
                        }
                    }
                }
            }
        }

        for (int i = 1; i < n; i++) {
            if (!(acceptable[i] && isReachable[i] && isAdequate[i]) && wasMentioned[i]) {
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
