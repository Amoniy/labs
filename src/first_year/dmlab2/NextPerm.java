package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class NextPerm {
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static void next(int[] bits, int len, FileWriter writer, FileOutputStream fileOutputStream) throws Exception {
        for (int i = len - 1; i > 0; i--) {
            if (bits[i] > bits[i - 1]) {
                i--;
                int minmore = 0;
                for (int j = len - 1; j > i; j--) {
                    if (bits[j] > bits[i]) {
                        minmore = j;
                        break;
                    }
                }
                int first = bits[i];
                bits[i] = bits[minmore];
                int[] reverse = new int[len - i - 1];
                int k = 0;
                for (int j = len - 1; j > minmore; j--) {
                    reverse[k] = bits[j];
                    k++;
                }
                reverse[k] = first;
                k++;
                for (int j = minmore - 1; j > i; j--) {
                    reverse[k] = bits[j];
                    k++;
                }


                for (int j = 0; j < len - i - 1; j++) {
                    bits[i + j + 1] = reverse[j];
                }

                break;
            }
        }
        //String ans="";
        for (int i = 1; i < len; i++) {
            //ans+=bits[i];
            writer.write((bits[i] + " "));
        }
        //return ans;
    }

    public static void prev(int[] bits, int len, FileWriter writer, FileOutputStream fileOutputStream) throws Exception {
        for (int i = len - 1; i > 0; i--) {
            if (bits[i] < bits[i - 1]) {
                i--;
                int max = 0;
                int minmore = 0;
                for (int j = i + 1; j < len; j++) {
                    if (bits[j] > max) {
                        if (bits[j] < bits[i]) {
                            max = bits[j];
                            minmore = j;
                        } else {
                            break;
                        }
                    }
                }
                int first = bits[i];
                bits[i] = bits[minmore];
                int[] reverse = new int[len - i - 1];
                int k = 0;
                for (int j = len - 1; j > minmore; j--) {
                    reverse[k] = bits[j];
                    k++;
                }
                reverse[k] = first;
                k++;
                for (int j = minmore - 1; j > i; j--) {
                    reverse[k] = bits[j];
                    k++;
                }


                for (int j = 0; j < len - i - 1; j++) {
                    bits[i + j + 1] = reverse[j];
                }

                break;
            }
        }
        //String ans="";
        for (int i = 1; i < len; i++) {
            //ans+=bits[i];
            writer.write((bits[i] + " "));
        }
        //return ans;
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "NextPerm.in";
        String destinationFileName = "NextPerm.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("NextPerm.out", true);
        int[] bits = new int[100001];
        int[] bits2 = new int[100001];
        //int rz=0;
        //int ro=0;
        br.readLine();
        int len = 0;
        bits[0] = 100001;
        boolean firstmark = false;
        boolean secondmark = false;
        String split = "[ ]+";
        String[] str = br.readLine().split(split);
        if (str.length == 1) {
            writer.write(0 + "\n" + 0);
        } else {
            for (int i = 0; i < str.length; i++) {
                int c = Integer.parseInt(str[len]);
                if (c != len + 1) {
                    firstmark = true;
                }
                if (c > bits[len]) {
                    secondmark = true;
                }
                bits[len + 1] = c;
                bits2[len + 1] = c;
                len++;
            }

            if (!firstmark && secondmark) {
                for (int i = 0; i < len; i++) {
                    writer.write("0 ");
                }
                writer.write("\n");
                next(bits, len + 1, writer, fileOutputStream);
            } else if (!secondmark && firstmark) {
                prev(bits, len + 1, writer, fileOutputStream);
                writer.write("\n");
                for (int i = 0; i < len; i++) {
                    writer.write("0 ");
                }
            } else {
                prev(bits, len + 1, writer, fileOutputStream);
                writer.write("\n");
                next(bits2, len + 1, writer, fileOutputStream);
            }
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
