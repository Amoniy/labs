package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class Zero {
    public static void main(String[] args) throws IOException {
        String destinationFileName = new Object() { }.getClass().getEnclosingClass()
                .getSimpleName().toLowerCase() + ".out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: s",
                "accept: ac",
                "reject: rj",
                "blank: _",
                "s _ -> ac _ ^",
                "s 0 -> n _ >",
                "n 0 -> s _ >",
                "n _ -> rj _ >"

        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}