package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class Less {
    public static void main(String[] args) throws IOException {
        String destinationFileName = "less.out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: s",
                "accept: ac",
                "reject: rj",
                "blank: _",
                "s < -> forwardWithFirstEnd < >",

                "s 0 -> forward 2 >",
                "s 1 -> forward 3 >", // (23)(01)<(01)(45)
                "forward < -> forward < >",
                "forward 0 -> forward 0 >",
                "forward 1 -> forward 1 >",

                "forward 4 -> backClear 4 <",
                "forward 5 -> backClear 5 <",
                "forward _ -> backClear _ <",

                "backClear 0 -> back 4 <",
                "backClear 1 -> back 5 <",
                "backClear < -> rj < ^", // второе кончилось а первое нет

                "back 0 -> back 0 <",
                "back 1 -> back 1 <",
                "back < -> back < <",
                "back 2 -> forwardClear 2 >",
                "back 3 -> forwardClear 3 >",
                "forwardClear 0 -> forward 2 >",
                "forwardClear 1 -> forward 3 >",
                "forwardClear < -> forwardWithFirstEnd < >",

                "forwardWithFirstEnd 0 -> ac 0 ^",
                "forwardWithFirstEnd 1 -> ac 1 ^", // первое короче
                "forwardWithFirstEnd 4 -> toStart 4 <",
                "forwardWithFirstEnd 5 -> toStart 5 <",
                "forwardWithFirstEnd _ -> rj _ ^", // второе слово тоже кончилось "<"

                "toStart - -> toStart - <",
                "toStart 2 -> toStart 2 <",
                "toStart 3 -> toStart 3 <",
                "toStart < -> toStart < <",
                "toStart _ -> check _ >",
                // длины равны и формат (23)<(45)
                "check 2 -> check0 _ >",
                "check 3 -> check1 _ >",
                "check0 2 -> check0 2 >",
                "check0 3 -> check0 3 >",
                "check1 2 -> check1 2 >",
                "check1 3 -> check1 3 >",

                "check0 < -> check0 < >",
                "check1 < -> check1 < >",

                "check0 - -> check0 - >",
                "check1 - -> check1 - >",
                "check0 4 -> toStart - <",
                "check0 5 -> ac - ^",
                "check1 4 -> rj - ^",
                "check1 5 -> toStart - <",


        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}