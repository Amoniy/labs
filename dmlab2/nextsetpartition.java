package labs.dmlab2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class nextsetpartition {
    public static ArrayList<ArrayList<Integer>> next(ArrayList<ArrayList<Integer>> list, int N){
        ArrayList<Integer> set=new ArrayList<>();
        for(int i=list.size()-1;i>=0;i--){
            boolean flag=true;
            boolean anotherflag=true;
            if(set.size()>0){
                if(set.get(set.size()-1)>list.get(i).get(list.get(i).size()-1)){//можем дописать
                    flag=false;
                    //list.get(i).add(set.get(0));
                    //set.remove(0);
                    for (int add = 0; add < set.size(); add++) {
                        if (set.get(add) > list.get(i).get(list.get(i).size()-1)) {
                            list.get(i).add(set.get(add));
                            set.remove(add);//added
                            break;
                        }
                    }
                    for (int j=0;j<set.size();j++){
                        list.add(new ArrayList<Integer>());
                        list.get(list.size()-1).add(set.get(j));
                    }//вариант дописывания обработан
                }
            }
            if(!flag){
                break;
            }
            for(int j=list.get(i).size()-1;j>=0;j--){
                if(set.size()>0) {//set не пуст
                    if (list.get(i).size() > 1) {//не последний элемент списка
                        int el = list.get(i).get(j);
                        if (el < set.get(set.size() - 1)) {//can change
                            for (int add = 0; add < set.size(); add++) {
                                if (set.get(add) > el) {
                                    list.get(i).set(j, set.get(add));

                                    set.remove(add);
                                    add(el,set);
                                    break;//changed
                                }
                            }
                            anotherflag=false;
                            for (int q=0;q<set.size();q++){
                                list.add(new ArrayList<Integer>());
                                list.get(list.size()-1).add(set.get(q));
                            }//дописали хвост
                            break;
                        }
                        else {//can't change
                            //set.add(list.get(i).get(j));
                            add(el, set);
                            list.get(i).remove(j);
                        }
                    }
                    else {//last in list
                        //set.add(list.get(i).get(0));
                        //if(list.get(list.size()-1).get(0)>set.get(set.size()-1)) {//не можем дописать
                            add(list.get(i).get(0), set);
                            list.remove(list.size() - 1);
                        /*}
                        else {//можем дописать
                            for (int add = 0; add < set.size(); add++) {
                                if (set.get(add) > list.get(i).get(0)) {
                                    list.get(i).add(set.get(add));
                                    set.remove(add);//added
                                    anotherflag=false;
                                    break;
                                }
                            }
                            for (int q=0;q<set.size();q++){
                                list.add(new ArrayList<Integer>());
                                list.get(list.size()-1).add(set.get(q));
                            }
                        }*/
                        //Collections.sort(set);
                        //mergeSortIterative(set);
                    }
                }
                else {
                    set.add(list.get(i).get(j));//сет был пуст. дописали. убрали из листа
                    list.get(i).remove(j);
                    if(list.get(i).size()==0){
                        list.remove(i);
                    }
                }
            }
            if (!anotherflag){
                break;
            }
        }

        if(list.size()==0){
            for(int i=0;i<N;i++){
                list.add(new ArrayList<Integer>());
                list.get(list.size()-1).add(i+1);
            }
        }
        return list;
    }
    public static void add(int element,ArrayList<Integer> set){
        for(int i=0;i<set.size();i++){
            if(element<set.get(i)){
                set.add(i,element);
                return;
            }
        }
        set.add(element);
    }

    //C:\java\projects\src\labs\dmlab2\test
    //nextsetpartition
    public static void main(String[] args) throws Exception {
        String destinationFileName = "C:\\java\\projects\\src\\labs\\dmlab2\\test.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs\\dmlab2\\test.out", true);
        //FastScanner in = new FastScanner(new File("C:\\java\\projects\\src\\labs\\dmlab2\\test.in"));
        String sourceFileName = "C:\\java\\projects\\src\\labs\\dmlab2\\test.in";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split="[ ]+";

        while(true){

            ArrayList<ArrayList<Integer>> list= new ArrayList<>();
            String[] nk=br.readLine().split(split);
            int n=Integer.parseInt(nk[0]);
            if(n==0){
                break;
            }
            int k=Integer.parseInt(nk[1]);
                for (int i = 0; i < k; i++) {
                    String[] ints = br.readLine().split(split);
                    list.add(new ArrayList<Integer>());
                    for (int j = 0; j < ints.length; j++) {
                        int integer = Integer.parseInt(ints[j]);
                        list.get(i).add(integer);
                    }
            }

            list=next(list,n);
            writer.write(n+" ");
            writer.write(list.size()+"\n");
            for(int i=0;i<list.size();i++){
                for(int j=0;j<list.get(i).size();j++){
                    writer.write(list.get(i).get(j)+" ");
                }
                writer.write("\n");
            }
            writer.write("\n");
            br.readLine();
        }
        writer.flush();
        fileOutputStream.close();
    }
}
