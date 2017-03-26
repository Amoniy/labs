package labs.lab1;

import java.io.*;
import java.io.FileWriter;
import java.util.*;

public class priorityqueue4 {
    public class Element{
        public int value;
        public int key;

        public Element(int value, int key) {
            this.value = value;
            this.key = key;
        }
    }
    private List<Element> heap = new ArrayList<>();

    private void swap(int i,int j){
        Element temp = heap.get(i);
        heap.set(i,heap.get(j));
        heap.set(j,temp);
    }
    private void siftUp(int i){
        while (i>0 && heap.get((i-1)/2).value > heap.get(i).value ){
            swap(i,(i-1)/2);
            i = (i-1)/2;
        }
    }
    private void siftDown(int i){
        while(2*i+1<heap.size()){
            int j = 2*i+1;
            if (2*i+2<heap.size() && heap.get(2*i+2).value<heap.get(2*i+1).value){
                j = 2*i+ 2;
            }
            if (heap.get(j).value>=heap.get(i).value){
                break;
            }
            swap(i,j);
            i = j;
        }
    }
    public void push(int value,int key){
        heap.add(new Element (value,key));
        siftUp(heap.size() - 1);
    }
    public int min(){
        return heap.get(0).value;
    }
    public boolean isEmpty(){
        return heap.isEmpty();
    }
    public void deleteMin(){
        swap(0,heap.size() - 1);
        heap.remove(heap.size() - 1);
        siftDown(0);
    }
    public void changeKey(int x,int k){
        for (int i = 0;i < heap.size();i++){
            if (heap.get(i).key == x){
                heap.get(i).value = k;
                siftDown(i);
                siftUp(i);
                break;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        Scanner inp = new Scanner(new File("priorityqueue.in"));
        FileWriter out = new FileWriter("priorityqueue.out");
        priorityqueue4 heap = new priorityqueue4();
        int count = 0;
        while(inp.hasNext()){
            count++;
            String[] words = inp.nextLine().split(" ");
            if (words[0].equals("push")){
                int val = Integer.valueOf(words[1]);
                heap.push(val,count);
            }
            if (words[0].equals("extract-min")){
                if (heap.isEmpty()){
                    out.write("*\n");
                }else{
                    out.write(heap.min()+"\n");
                    heap.deleteMin();
                }
            }
            if (words[0].equals("decrease-key")){
                int x = Integer.valueOf(words[1]);
                int k = Integer.valueOf(words[2]);
                heap.changeKey(x,k);
            }
        }
        out.close();
    }
}