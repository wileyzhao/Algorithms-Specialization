package sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by wanli on 30/05/2018.
 */
public class huffmanCode {
    public static List<Node> nodeList = new ArrayList<>();
    public static List<Node> treeNode = new ArrayList<>();
    public static List<Node> huffmanList = new ArrayList<>();
    public static Queue<String> huffmancodeQ = new LinkedList<String>();
    public static HashMap<Integer,Integer> maxIS = new HashMap<>();
    public static HashMap<Integer,Integer> fvalues = new HashMap<>();
    public static List<Integer> pathG = new ArrayList<>();



    public static void addNode(String value, int key){
        Node n = new Node();
        n.key = key;
        n.value = value;
        n.huffmanCode = "";
        nodeList.add(n);
        treeNode.add(n);
    }
    public static void huffman(List<Node> nl){
        if(nl.size() < 2)
            return;
        else{
            List<Node> nlTemp = quickSort(nl);
            Node n = new Node();
            n.key = nlTemp.get(0).key + nlTemp.get(1).key;
            n.value = nlTemp.get(0).value + nlTemp.get(1).value;
            n.lChild = nlTemp.get(0);
            n.rChild = nlTemp.get(1);
            n.huffmanCode = "";
            nl.remove(nlTemp.get(0));
            nl.remove(nlTemp.get(1));
            nl.add(n);
            treeNode.add(n);
            huffman(nl);
            return;
        }
    }
    public static List<Node> quickSort(List<Node> nlist) {
        int n = nlist.size();
        if (n < 2) {
            return nlist;
        }
        Node pivot = nlist.get(0);
        List<Node> begin = new ArrayList<>();
        List<Node> end = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (pivot.key > nlist.get(i).key)
                begin.add(nlist.get(i));
            else
                end.add(nlist.get(i));
        }
        List<Node> resultNodes = quickSort(begin);
        resultNodes.add(pivot);
        resultNodes.addAll(quickSort(end));
        return resultNodes;
    }
    public static void visit(Node n){
        if(n.lChild != null){
            n.lChild.huffmanCode = n.huffmanCode + "0";
            visit(n.lChild);
        }
        else {
            huffmanList.add(n);
        }
        if(n.rChild != null){
            n.rChild.huffmanCode = n.huffmanCode + "1";
            visit(n.rChild);
        }
    }
    public static void huffmanDecrypt(String huffC, Node n){
        Node top = n;
        for(char c : huffC.toCharArray()){
            if(c == '0'){
                n = n.lChild;
                if(n.lChild == null) {
                    huffmancodeQ.add(n.value);
                    n = top;
                }
            }
            else if(c == '1'){
                n = n.rChild;
                if(n.rChild == null){
                    huffmancodeQ.add(n.value);
                    n = top;
                }
            }
            else{
                System.out.println("Error!");
            }


        }

    }
    public static void readFile(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));   //如果是读大文件，设置缓存
            String tempString = null;
            Integer symbol = 0;
            while ((tempString = reader.readLine()) != null) {

                if(symbol == 0){
                    symbol++;
                    continue;
                }
                symbol++;
                addNode(symbol.toString(), Integer.valueOf(tempString));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void testmain(){
        addNode("A",45);
        addNode("B",13);
        addNode("C",12);
        addNode("D",16);
        addNode("E",9);
        addNode("F",5);
        addNode("G",35);
        huffman(nodeList);
        System.out.println(treeNode.size());

        Node n = treeNode.get(treeNode.size()-1);
        visit(n);
        String huffC = "01001111011101110001";
        huffmanDecrypt(huffC,n);
        System.out.println("huffman code is " + huffmancodeQ);
    }

    public static void problem1(){
        String file = "src/main/resources/huffman.txt";
        readFile(file);
        huffman(nodeList);
        System.out.println(treeNode.size());
        Node n = treeNode.get(treeNode.size()-1);
        visit(n);
        System.out.println(huffmanList.size());
        int size = 0;
        for(Node nn : huffmanList){
            //System.out.println("======nn.value is " +nn.key + " huffmancode is " + nn.huffmanCode);
            if (size < nn.huffmanCode.length())
                size = nn.huffmanCode.length();
        }
        System.out.println(size);
    }

    public static void readFileAgain(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));   //如果是读大文件，设置缓存
            String tempString = null;
            Integer symbol = 0;
            while ((tempString = reader.readLine()) != null) {

                if(symbol == 0){
                    symbol++;
                    continue;
                }
                symbol++;
                pathG.add(Integer.valueOf(tempString));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Integer MIS(Integer i){
        if(i == 0){
            maxIS.put(-1,0);
            maxIS.put(0, 0);
            return 0;
        }
        else if (i == 1){
            maxIS.put(1, pathG.get(0));
            return pathG.get(0);
        }
        else{
            Integer a,b,temp;
            if(maxIS.containsKey(i-2))
                a = maxIS.get(i-2);
            else{
                a = MIS(i-2);
                }
            if(maxIS.containsKey(i-1))
                b = maxIS.get(i-1);
            else
                b = MIS(i-1);

            if(b >= a + pathG.get(i-1)){
                maxIS.put(i,b);
                return b;
            }
            else{
                temp = a+pathG.get(i-1);
                maxIS.put(i, temp);
                return temp;
            }
        }
    }

    //先用DP将所有的值都存到map中,再通过最大独立集的概念去获取结果。
    public static void problem2(){
        String file = "src/main/resources/mwis.txt";
        readFileAgain(file);
        Integer k = pathG.size();
        System.out.println(pathG.size());
        System.out.println("MIS(k) is " + MIS(k));

        while(k >= 1){
            if(maxIS.get(k-1) >= maxIS.get(k-2) + pathG.get(k-1)){
                k--;
            }
            else{
                fvalues.put(k,maxIS.get(k-2) + pathG.get(k-1));
                k-=2;
            }
        }
        System.out.println("fvalues size is " + fvalues.size());
        String result = "";
        if(fvalues.containsKey(1)) result+="1";
        else result +="0";
        if(fvalues.containsKey(2)) result+="1";
        else result +="0";
        if(fvalues.containsKey(3)) result+="1";
        else result +="0";
        if(fvalues.containsKey(4)) result+="1";
        else result +="0";
        if(fvalues.containsKey(17)) result+="1";
        else result +="0";
        if(fvalues.containsKey(117)) result+="1";
        else result +="0";
        if(fvalues.containsKey(517)) result+="1";
        else result +="0";
        if(fvalues.containsKey(997)) result+="1";
        else result +="0";
        System.out.println("result is " + result);

    }

    public static void main(String[] args){
        //testmain();
        problem1();
        //problem2();

    }
}
class Node{
    String value;
    int key;
    String huffmanCode;
    Node lChild;
    Node rChild;
}