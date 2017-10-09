package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class bst {
    public static Random random = new Random(1);

    public static class node {
        int value;
        int priority;
        node leftson;
        node rightson;
        node parent;

        public node(int value) {
            this.value = value;
            this.priority = random.nextInt();
            this.leftson = null;
            this.rightson = null;
            this.parent = null;
        }
    }

    public static node[] split(node root, int k) {
        if (root == null) {
            node[] ret = {null, null};
            return ret;
        }
        if (root.value < k) {
            node[] ret = split(root.rightson, k);
            root.rightson = ret[0];
            ret[0] = root;
            return ret;
        } else {
            node[] ret = split(root.leftson, k);
            root.leftson = ret[1];
            ret[1] = root;
            return ret;
        }
    }

    public static node merge(node first, node second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        if (first.priority < second.priority) {
            first.rightson = merge(first.rightson, second);
            return first;
        } else {
            second.leftson = merge(first, second.leftson);
            return second;
        }
    }

    public static node add(node root, int x) {
        if (root == null) {
            return new node(x);
        } else {
            node[] temp = split(root, x);
            return merge(merge(temp[0], new node(x)), temp[1]);
        }

    }

    public static node remove(node root, int x) {
        if (root.value == x) {
            return merge(root.leftson, root.rightson);
        }
        if (x < root.value) {
            root.leftson = remove(root.leftson, x);
        } else {
            root.rightson = remove(root.rightson, x);
        }
        return root;
    }

    public static boolean exists(node root, int q) {
        node temp = root;
        if (root == null) {
            return false;
        }
        while (true) {
            if (temp.value < q) {
                if (temp.rightson != null) {
                    temp = temp.rightson;
                } else {
                    return false;
                }
            } else if (temp.value > q) {
                if (temp.leftson != null) {
                    temp = temp.leftson;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    public static String next(node root, int x) {
        node[] temp = split(root, x + 1);
        node t = temp[1];
        if (t == null) {
            root = merge(temp[0], temp[1]);
            return "none";
        }
        while (true) {
            if (t.leftson != null) {
                t = t.leftson;
            } else {
                root = merge(temp[0], temp[1]);
                return "" + t.value;
            }
        }
    }

    public static String prev(node root, int x) {
        node[] temp = split(root, x);
        node t = temp[0];
        if (t == null) {
            root = merge(temp[0], temp[1]);
            return "none";
        }
        while (true) {
            if (t.rightson != null) {
                t = t.rightson;
            } else {
                root = merge(temp[0], temp[1]);
                return "" + t.value;
            }
        }
    }

    //C:\java\projects\src\labs\lab3\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "bst.in";
        String destinationFileName = "bst.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("bst.out", true);
        String split = "[ ]+";

        node root = null;
        String[] input = br.readLine().split(split);
        String ans = "";
        try {
            while (true) {
                int q = Integer.parseInt(input[1]);
                if (input[0].equals("insert")) {
                    if (!exists(root, q)) {
                        root = add(root, q);
                    }
                } else if (input[0].equals("delete")) {
                    if (exists(root, q)) {
                        root = remove(root, q);
                    }
                } else if (input[0].equals("exists")) {
                    if (exists(root, q)) {
                        writer.write("true\n");
                    } else {
                        writer.write("false\n");
                    }
                } else if (input[0].equals("next")) {
                    ans = next(root, q);
                    writer.write(ans + "\n");
                } else {
                    ans = prev(root, q);
                    writer.write(ans + "\n");
                }
                input = br.readLine().split(split);
            }
        } catch (Exception e) {

        }
        //ans+=";";
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
