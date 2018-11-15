package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class Mirror {
    public static void main(String[] args) throws IOException {
        String destinationFileName = "mirror.out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: s",
                "accept: ac",
                "reject: rj",
                "blank: _",
                "s 0 -> zero 0 >",
                "s 1 -> one 1 >",

                "zero 0 -> zero 0 >",
                "zero 1 -> one 1 >",
                "one 0 -> zero 0 >",
                "one 1 -> one 1 >", // carriyng the last symbol

                "zero _ -> first 2 <",
                "one _ -> first 3 <",
                "first 0 -> toLeft 2 <",
                "first 1 -> toLeft 3 <",
                //"zero _ -> toLeft 2 <",
                //"one _ -> toLeft 3 <", // first time character replacement

                //
                "zero 2 -> clrZero 2 <",
                "zero 3 -> clrZero 3 <",
                "one 2 -> clrOne 2 <",
                "one 3 -> clrOne 3 <",

                "clrZero 0 -> clrZero 2 >",
                "clrZero 1 -> clrZero 3 >",
                "clrOne 0 -> clrOne 2 >",
                "clrOne 1 -> clrOne 3 >", // clearing last 0 or 1
                //

                "clrZero 2 -> clrZero 2 >",
                "clrZero 3 -> clrZero 3 >",
                "clrOne 2 -> clrOne 2 >",
                "clrOne 3 -> clrOne 3 >", // carrying symbol to the right end

                "clrZero _ -> toLeft 2 <",
                "clrOne _ -> toLeft 3 <", // inputing last character

                "toLeft 3 -> toLeft 3 <",
                "toLeft 2 -> toLeft 2 <",
                "toLeft 1 -> toLeft 1 <",
                "toLeft 0 -> toLeft 0 <",
                "toLeft _ -> s _ >", // from end to the beginning

                "s 2 -> ss 0 >",
                "s 3 -> ss 1 >",

                "ss 2 -> ss 0 >",
                "ss 3 -> ss 1 >",
                "ss _ -> back _ <",
                "back 0 -> back 0 <",
                "back 1 -> back 1 <",
                "back _ -> ac _ >",


        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}