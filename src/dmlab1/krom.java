package dmlab1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class krom {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String split = "[ ]+";
        String[] amount = br.readLine().split(split);

        int n = Integer.parseInt(amount[0]);
        int m = Integer.parseInt(amount[1]);
        int x = 1;
        for (int i = 0; i < n; i++) {
            x *= 2;
        }
        int[][] table = new int[x][n];

        for (int i = 0; i < x; i++) {
            int c = i;
            int[] bin = new int[n];
            int y;
            for (int j = n - 1; j >= 0; j--) {
                y = c % 2;
                if (y == 1) {
                    bin[j] = 1;
                } else {
                    bin[j] = -1;
                }
                c /= 2;
            }

            table[i] = bin;
        }

        ArrayList<String> container = new ArrayList<>();
        ArrayList<Integer[]> bracks = new ArrayList<>();
        int first, second, third, fourth;
        for (int i = 0; i < m; i++) {
            String[] input = br.readLine().split(split);
            if (Integer.parseInt(input[0]) > 0) {
                first = Integer.parseInt(input[0]);
                third = 1;
            } else {
                first = Integer.parseInt(input[0]) * (-1);
                third = -1;
            }
            if (Integer.parseInt(input[1]) > 0) {
                second = Integer.parseInt(input[1]);
                fourth = 1;
            } else {
                second = Integer.parseInt(input[1]) * (-1);
                fourth = -1;
            }
            if (!(container.contains("" + first + second + third + fourth) || container.contains("" + second + first + fourth + third))) {
                Integer[] temp = {first, second, third, fourth};
                bracks.add(temp);
                container.add("" + first + second + third + fourth);
            }
        }

        boolean answer = false;
        m = bracks.size();
        for (int i = 0; i < x; i++) {
            int count = 0;
            for (int j = 0; j < m; j++) {
                if ((table[i][bracks.get(j)[0] - 1] * bracks.get(j)[2] == -1) && (table[i][bracks.get(j)[1] - 1] * bracks.get(j)[3] == -1)) {
                    break;
                }
                count++;
            }
            if (count == m) {
                answer = true;
                break;
            }
        }

        if (answer) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
        }
    }
}