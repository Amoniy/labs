package dmlab1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class horn {
    static boolean iszero(ArrayList<ArrayList<Integer>> vars) {
        while (true) {
            int count = 0;
            for (int i = 0; i < vars.size(); i++) {
                if (vars.get(i).size() == 1) {
                    count++;
                    int x = vars.get(i).get(0);

                    for (int j = 0; j < vars.size(); j++) {

                        if (vars.get(j).contains(-x)) {
                            vars.get(j).remove(vars.get(j).indexOf(-x));
                        }
                        if (vars.get(j).size() == 0) {
                            return true;
                        }
                        if (vars.get(j).contains(x)) {
                            vars.remove(j);
                            j--;
                        }
                    }
                }
            }
            if (count == 0) {
                return false;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String split = "[ ]+";
        ArrayList<ArrayList<Integer>> vars = new ArrayList<ArrayList<Integer>>();
        String[] first = br.readLine().split(split);
        int n = Integer.parseInt(first[0]);
        int k = Integer.parseInt(first[1]);
        for (int i = 0; i < k; i++) {
            String[] temp = br.readLine().split(split);
            vars.add(new ArrayList<Integer>());
            for (int j = 1; j < temp.length + 1; j++) {
                if (temp[j - 1].equals("1")) {
                    vars.get(i).add(j);
                } else if (temp[j - 1].equals("0")) {
                    vars.get(i).add(-j);
                }
            }
        }
        if (iszero(vars)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
