package second_year.untitled_algo_labs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;

public class SplayTree {

    static HashMap<Integer, node>[] edges;
    static HashMap<Integer, node>[] backEdges;
    static HashSet<node>[] nodes;

    static class node {
        int value;
        node parent;
        node rightSon;
        node leftSon;
        int weight;

        public node(int value) {
            this.value = value;
            weight = 1;
        }
    }

    static void superZig(node node, boolean isLeftSon) {
        node parent = node.parent;
        node grandparent = parent.parent;
        boolean secondLeft = false;
        if (grandparent.leftSon == parent) {
            secondLeft = true;
        }
        zig(node, isLeftSon);
        node.parent = grandparent;
        if (secondLeft) {
            grandparent.leftSon = node;
        } else {
            grandparent.rightSon = node;
        }
    }

    static void zig(node node, boolean isLeftSon) {
        node parent = node.parent;
        if (isLeftSon) {
            node b = node.rightSon;

            parent.leftSon = b;
            if (b != null) {
                b.parent = parent;
            }

            node.parent = null;
            node.rightSon = parent;
            parent.parent = node;
        } else {
            node b = node.leftSon;

            parent.rightSon = b;
            if (b != null) {
                b.parent = parent;
            }

            node.parent = null;
            node.leftSon = parent;
            parent.parent = node;
        }
        goUp(parent);
    }

    static void zigZig(node node, boolean isLeftSon) {
        node parent = node.parent;
        node grandparent = parent.parent;
        node grandGrandparent = grandparent.parent;
        if (isLeftSon) {
            node b = node.rightSon;
            node c = parent.rightSon;

            grandparent.parent = parent;
            grandparent.leftSon = c;
            if (c != null) {
                c.parent = grandparent;
            }

            parent.leftSon = b;
            parent.rightSon = grandparent;
            if (b != null) {
                b.parent = parent;
            }

            node.rightSon = parent;
            node.parent = grandGrandparent;
            parent.parent = node;
        } else {
            node b = parent.leftSon;
            node c = node.leftSon;

            grandparent.parent = parent;
            grandparent.rightSon = b;
            if (b != null) {
                b.parent = grandparent;
            }

            parent.rightSon = c;
            parent.leftSon = grandparent;
            if (c != null) {
                c.parent = parent;
            }

            node.leftSon = parent;
            node.parent = grandGrandparent;
            parent.parent = node;
        }
        if (grandGrandparent != null) {
            if (grandGrandparent.leftSon == grandparent) {
                grandGrandparent.leftSon = node;
            } else {
                grandGrandparent.rightSon = node;
            }
        }
        goUp(grandparent);
    }

    static void zigZag(node node, boolean isLeftSon) {
        node parent = node.parent;
        node grandparent = parent.parent;
        node grandGrandparent = grandparent.parent;
        if (isLeftSon) {
            node b = node.leftSon;
            node c = node.rightSon;

            grandparent.parent = node;
            grandparent.rightSon = b;
            if (b != null) {
                b.parent = grandparent;
            }

            parent.parent = node;
            parent.leftSon = c;
            if (c != null) {
                c.parent = parent;
            }

            node.rightSon = parent;
            node.leftSon = grandparent;
            node.parent = grandGrandparent;
        } else {
            node b = node.leftSon;
            node c = node.rightSon;

            grandparent.parent = node;
            grandparent.leftSon = c;
            if (c != null) {
                c.parent = grandparent;
            }

            parent.parent = node;
            parent.rightSon = b;
            if (b != null) {
                b.parent = parent;
            }

            node.leftSon = parent;
            node.rightSon = grandparent;
            node.parent = grandGrandparent;
        }
        if (grandGrandparent != null) {
            if (grandGrandparent.leftSon == grandparent) {
                grandGrandparent.leftSon = node;
            } else {
                grandGrandparent.rightSon = node;
            }
        }
        parent.weight = 1;
        if (parent.rightSon != null) {
            parent.weight += parent.rightSon.weight;
        }
        if (parent.leftSon != null) {
            parent.weight += parent.leftSon.weight;
        }
        goUp(grandparent);
    }

    static void splay(node node) {
        while (node.parent != null) {
            node parent = node.parent;
            boolean isLeftSon = false;
            if (parent.leftSon == node) {
                isLeftSon = true;
            }
            if (parent.parent == null) {
                zig(node, isLeftSon);
            } else {
                boolean secondLeft = false;
                node grandparent = parent.parent;
                if (grandparent.leftSon == parent) {
                    secondLeft = true;
                }
                if (isLeftSon && secondLeft) {
                    zigZig(node, true);
                } else if (isLeftSon && (!secondLeft)) {
                    zigZag(node, true);
                } else if ((!isLeftSon) && secondLeft) {
                    zigZag(node, false);
                } else {
                    zigZig(node, false);
                }
            }
        }
    }

    static node addToTheLeft(node node, int value) {
        if (node == null) {
            node newNode = new node(value);
            nodes[value].add(newNode);
            return newNode;
        }
        node firstNode = findFirst(node);
        node newNode = new node(value);
        nodes[value].add(newNode);
        firstNode.leftSon = newNode;
        goUp(firstNode);
        newNode.parent = firstNode;
        edges[value].put(firstNode.value, newNode);
        backEdges[value].put(firstNode.value, firstNode);
        return node;
    }

    static node addToTheRight(node node, int value) {
        if (node == null) {
            node newNode = new node(value);
            nodes[value].add(newNode);
            return newNode;
        }
        node lastNode = findLast(node);
        node newNode = new node(value);
        nodes[value].add(newNode);
        lastNode.rightSon = newNode;
        goUp(lastNode);
        newNode.parent = lastNode;
        edges[lastNode.value].put(newNode.value, lastNode);
        backEdges[lastNode.value].put(newNode.value, newNode);
        return node;
    }

    static node removeFirst(node node) {
        node first = findFirst(node);
        nodes[first.value].remove(first);

        node parent = first.parent;
        if (parent == null) {
            node returnNode = first.rightSon;
            if (returnNode == null) {
                return null;
            }
            returnNode.parent = null;
            return returnNode;
        } else {
            parent.leftSon = first.rightSon;
            if (first.rightSon != null) {
                first.rightSon.parent = parent;
            }
            goUp(parent);
            return parent;
        }
    }

    static node findFirst(node node) {
        if (node == null) {
            return null;
        }
        while (node.parent != null) {
            node = node.parent;
        }
        while (node.leftSon != null) {
            node = node.leftSon;
        }
        return node;
    }

    static node findLast(node node) {
        if (node == null) {
            return null;
        }
        while (node.parent != null) {
            node = node.parent;
        }
        while (node.rightSon != null) {
            node = node.rightSon;
        }
        return node;
    }

    static void merge(node left, node right) {
        if (left == null || right == null) {
            return;
        }
        node lastLeft = findLast(left);
        node firstRight = findFirst(right);
        splay(lastLeft);
        splay(right);
        lastLeft.rightSon = right;
        goUp(lastLeft);
        right.parent = lastLeft;
        edges[lastLeft.value].put(firstRight.value, lastLeft);
        backEdges[lastLeft.value].put(firstRight.value, firstRight);
    }

    static void link(int left, int right) {
        node leftNode = nodes[left].iterator().next();
        node rightNode = nodes[right].iterator().next();
        splay(leftNode);
        node A2 = leftNode.rightSon;
        leftNode.rightSon = null;
        goUp(leftNode);
        if (A2 != null) {
            A2.parent = null;
            A2 = addToTheLeft(A2, leftNode.value);
        } else {
            node newNode = new node(leftNode.value);
            nodes[leftNode.value].add(newNode);
            A2 = newNode;
        }

        splay(rightNode);
        node B1 = rightNode.leftSon;
        rightNode.leftSon = null;
        goUp(rightNode);
        if (B1 != null) {
            B1.parent = null;
            B1 = addToTheRight(B1, rightNode.value);
            B1 = removeFirst(B1);
        } else {
            B1 = null; // ненужная фигня но просто чтобы не запутаться
        }
        // или тут хеши (я хз)
        merge(leftNode, rightNode);
        merge(rightNode, B1);
        merge(rightNode, A2);
    }


    static void cut(int left, int right) {
        // check which is lefter
        node supposedLeft = edges[left].get(right);
        node supposedRight = backEdges[right].get(left);
        if (supposedLeft == supposedRight) {
            int temp = left;
            left = right;
            right = temp;
        } else {
            splay(supposedLeft);
            superSplay(supposedRight, supposedLeft);
            if (supposedLeft.leftSon == supposedRight) {
                int temp = left;
                left = right;
                right = temp;
            }
        }
        // if false -> switch

        node leftNodeA = edges[left].get(right);
        node rightNodeA = backEdges[right].get(left);

        node leftNodeB = backEdges[left].get(right);
        node rightNodeB = edges[right].get(left);
        // if leftB == rightB

        splay(leftNodeA);
        superSplay(leftNodeB, leftNodeA);
        leftNodeA.rightSon = null;
        goUp(leftNodeA);
        leftNodeB.parent = null;

        splay(rightNodeB);
        superSplay(rightNodeA, rightNodeB);
        rightNodeB.rightSon = null;
        goUp(rightNodeB);
        rightNodeA.parent = null;

        rightNodeA = removeFirst(rightNodeA);
        merge(leftNodeA, rightNodeA);
    }

    static void superSplay(node node, node neededParent) { // make node right son of a needed parent who is a root
        while ((node.parent != neededParent) && (node.parent != null)) {
            node parent = node.parent;
            boolean isLeftSon = false;
            if (parent.leftSon == node) {
                isLeftSon = true;
            }
            if (parent.parent == neededParent) {
                superZig(node, isLeftSon);
            } else {
                boolean secondLeft = false;
                node grandparent = parent.parent;
                if (grandparent.leftSon == parent) {
                    secondLeft = true;
                }
                if (isLeftSon && secondLeft) {
                    zigZig(node, true);
                } else if (isLeftSon && (!secondLeft)) {
                    zigZag(node, true);
                } else if ((!isLeftSon) && secondLeft) {
                    zigZag(node, false);
                } else {
                    zigZig(node, false);
                }
            }
        }
    }

    static boolean connected(node first, node second) {
        while (first.parent != null) {
            first = first.parent;
        }
        while (second.parent != null) {
            second = second.parent;
        }
        return first == second;
    }

    static int size(node node) {
        splay(node);
        return (node.weight + 1) / 2;
    }

    static void goUp(node node) {
        // maybe add the first step
        while (true) {
            if (node == null) {
                break;
            }
            node leftSon = node.leftSon;
            node rightSon = node.rightSon;
            node.weight = 1;
            if (leftSon != null) {
                node.weight += leftSon.weight;
            }
            if (rightSon != null) {
                node.weight += rightSon.weight;
            }
            node = node.parent;
        }
    }

    // везде проверки на нуллы
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);
        int n = in.readInt();
        int m = in.readInt();
        nodes = new HashSet[n + 1];
        edges = new HashMap[n + 1];
        backEdges = new HashMap[n + 1];

        for (int i = 1; i < n + 1; i++) {
            nodes[i] = new HashSet<>();
            node newNode = new node(i);
            nodes[i].add(newNode);
            edges[i] = new HashMap<Integer, node>();
            edges[i].put(i, newNode);
            backEdges[i] = new HashMap<Integer, node>();
            backEdges[i].put(i, newNode);
        }

        for (int i = 0; i < m; i++) {
            String query = in.readString();
            int left = in.readInt();
            if (query.equals("link")) {
                int right = in.readInt();
                link(left, right);
            } else if (query.equals("cut")) {
                int right = in.readInt();
                cut(left, right);
            } else if (query.equals("size")) {
                out.printLineln(size(nodes[left].iterator().next()));
            } else {
                int right = in.readInt();
                boolean connected = connected(nodes[left].iterator().next(), nodes[right].iterator().next());
                if (connected) {
                    out.printLineln("1");
                } else {
                    out.printLineln("0");
                }
            }
        }
        out.flush();
    }

    static class InputReader {

        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return readString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
        }

        public void printLineln(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }

    }
}
