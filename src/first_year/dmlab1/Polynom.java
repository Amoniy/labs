package first_year.dmlab1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Polynom {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int m = 1;
        for (int i = 0; i < n; i++) {
            m *= 2;
        }
        String split = "[ ]+";
        String[] chars = new String[m];
        ArrayList<Integer> deck = new ArrayList<Integer>();
        for (int i = 0; i < m; i++) {
            String[] temp = br.readLine().split(split);
            chars[i] = temp[0];
            deck.add(Integer.parseInt(temp[1]));
        }
        int[] ans = new int[m];
        ans[0] = deck.get(0);
        for (int i = 0; i < m - 1; i++) {
            int k = deck.size();
            for (int j = 0; j < k - 1; j++) {
                int x = deck.get(deck.size() - 1);
                if ((x + deck.get(deck.size() - 2)) % 2 == 1) {
                    deck.add(0, 1);
                    deck.remove(deck.size() - 1);
                } else {
                    deck.add(0, 0);
                    deck.remove(deck.size() - 1);
                }
            }
            deck.remove(deck.size() - 1);
            ans[i + 1] = deck.get(0);
        }
        for (int i = 0; i < m; i++) {
            System.out.println(chars[i] + " " + ans[i]);
        }
    }
}
