package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class Balanced {
    public static void main(String[] args) throws IOException {
        String destinationFileName = "balanced.out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: s",
                "accept: ac",
                "reject: rj",
                "blank: _",
                "s _ -> ac _ ^",
                "s ) -> rj _ ^",
                "s ( -> open ( >",
                "open ( -> open ( >",
                "open 0 -> open 0 >",
                "open _ -> rj _ ^",
                "open ) -> clearLast 0 <",
                "clearLast ( -> toLeftStart 0 <",
                "clearLast 0 -> clearLast 0 <",

                "toLeftStart 0 -> toLeftStart 0 <",
                "toLeftStart ( -> open ( >",
                "toLeftStart _ -> toNextStart _ >",
                "toNextStart 0 -> toNextStart 0 >",
                "toNextStart ) -> rj ) ^",
                "toNextStart ( -> open ( >",
                "toNextStart _ -> ac _ ^",


        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}