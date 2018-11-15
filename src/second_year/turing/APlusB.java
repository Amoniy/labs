package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class APlusB {
    public static void main(String[] args) throws IOException {
        String destinationFileName = "aplusb.out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: s",
                "accept: ac",
                "reject: rj",
                "blank: _",
                "s _ -> ac 0 ^", // не бывает
                "s + -> plusToRight + >",
                "s 0 -> leftToRight 0 >",
                "s 1 -> leftToRight 1 >", // start

                "leftToRight 0 -> leftToRight 0 >",
                "leftToRight 1 -> leftToRight 1 >",
                "leftToRight 2 -> leftToRight 2 >",
                "leftToRight 3 -> leftToRight 3 >",
                "leftToRight 4 -> leftToRight 4 >",
                "leftToRight 5 -> leftToRight 5 >",
                "leftToRight + -> plusToRight + >", // from left to end of left

                "plusToRight _ -> replace _ <",
                // replacing
                "replace + -> replace _ <",
                "replace 0 -> replace 0 <",
                "replace 1 -> replace 1 <",
                "replace _ -> ac _ >",
                "replace 2 -> replace 0 <",
                "replace 3 -> replace 1 <",
                "replace 4 -> replaceWithCarry 0 <",
                "replace 5 -> replaceWithCarry 1 <",
                "replaceWithCarry 0 -> ac 1 ^",
                "replaceWithCarry 1 -> replaceWithCarry 0 <",
                "replaceWithCarry _ -> ac 1 ^",
                //
                "plusToRight 1 -> rightToRightONE 1 >",
                "plusToRight 0 -> rightToRightZERO 0 >",

                "rightToRightONE 1 -> rightToRightONE 1 >",
                "rightToRightONE 0 -> rightToRightZERO 0 >",
                "rightToRightZERO 1 -> rightToRightONE 1 >",
                "rightToRightZERO 0 -> rightToRightZERO 0 >", // right to end of right

                "rightToRightONE _ -> rightToLeftONECLEAR _ <",
                "rightToLeftONECLEAR 1 -> rightToLeftONE _ <",

                "rightToRightZERO _ -> rightToLeftZEROCLEAR _ <",
                "rightToLeftZEROCLEAR 0 -> rightToLeftZERO _ <", // clear last bit

                "rightToLeftONE 1 -> rightToLeftONE 1 <",
                "rightToLeftONE 0 -> rightToLeftONE 0 <",
                "rightToLeftZERO 1 -> rightToLeftZERO 1 <",
                "rightToLeftZERO 0 -> rightToLeftZERO 0 <",

                "rightToLeftONE + -> plusToLeftONE + <",
                "rightToLeftZERO + -> plusToLeftZERO + <", // end of right to start of right

                // 2 - 0, 3 - 1, 4 - 0 with carry, 5 - 1 with carry

                "plusToLeftONE 0 -> leftToRight 3 >",
                "plusToLeftZERO 0 -> leftToRight 2 >",
                "plusToLeftONE 1 -> leftToRight 4 >",
                "plusToLeftZERO 1 -> leftToRight 3 >",
                "plusToLeftONE 2 -> leftToLeftONE 2 <",
                "plusToLeftZERO 2 -> leftToLeftZERO 2 <",
                "plusToLeftONE 3 -> leftToLeftONE 3 <",
                "plusToLeftZERO 3 -> leftToLeftZERO 3 <",
                "plusToLeftONE 4 -> leftToLeftTWO 2 <",
                "plusToLeftZERO 4 -> leftToLeftONE 2 <",
                "plusToLeftONE 5 -> leftToLeftTWO 3 <",
                "plusToLeftZERO 5 -> leftToLeftONE 3 <",


                "leftToLeftONE 0 -> leftToRight 3 >",
                "leftToLeftZERO 0 -> leftToRight 2 >",
                "leftToLeftTWO 0 -> leftToRight 4 >",

                "leftToLeftONE 1 -> leftToRight 4 >",
                "leftToLeftZERO 1 -> leftToRight 3 >",
                "leftToLeftTWO 1 -> leftToRight 5 >",

                "leftToLeftONE 2 -> leftToLeftONE 2 <",
                "leftToLeftZERO 2 -> leftToLeftZERO 2 <",
                "leftToLeftTWO 2 -> leftToLeftTWO 2 <", // вот этой строки на самом деле не бывает

                "leftToLeftONE 3 -> leftToLeftONE 3 <",
                "leftToLeftZERO 3 -> leftToLeftZERO 3 <",
                "leftToLeftTWO 3 -> leftToLeftTWO 3 <", // и этой не бывает

                "leftToLeftONE 4 -> leftToLeftTWO 2 <",
                "leftToLeftZERO 4 -> leftToLeftONE 2 <",
                "leftToLeftTWO 4 -> leftToLeftTHREE 2 <", // тоже не бывает

                "leftToLeftONE 5 -> leftToLeftTWO 3 <",
                "leftToLeftZERO 5 -> leftToLeftONE 3 <",
                "leftToLeftTWO 5 -> leftToRight 3 <", // тоже не бывает

                "leftToLeftONE _ -> leftToRight 3 >",
                "leftToLeftZERO _ -> leftToRight 2 >",
                "leftToLeftTWO _ -> leftToRight 4 >",

        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}
