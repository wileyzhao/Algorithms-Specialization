package sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wanli on 20/05/2018.
 * clustering1: 106
 * clustering2: 6118
 *
 * 1.In this programming problem and the next you’ll code up the clustering algorithm from lecture for computing a max-spacing k-clustering.
 * Download the text file below.
 *  clustering1.txt
 *  This file describes a distance function (equivalently, a complete graph with edge costs). It has the following format:
 *  [number_of_nodes]
 *  [edge 1 node 1] [edge 1 node 2] [edge 1 cost]
 *  [edge 2 node 1] [edge 2 node 2] [edge 2 cost]
 *  …
 *  There is one edge (i,j) for each choice of 1 ≤ i< j ≤ n, where n is the number of nodes.
 *  For example, the third line of the file is “1 3 5250”, indicating that the distance between nodes 1 and 3 (equivalently, the cost of the edge (1,3)) is 5250. You can assume that distances are positive, but you should NOT assume that they are distinct.
 * Your task in this problem is to run the clustering algorithm from lecture on this data set, where the target number k of clusters is set to 4. What is the maximum spacing of a 4-clustering?
 * ADVICE: If you’re not getting the correct answer, try debugging your algorithm using some small test cases. And then post them to the discussion forum!
 *
 * 2.In this question your task is again to run the clustering algorithm from lecture, but on a MUCH bigger graph. So big, in fact, that the distances (i.e., edge costs) are only defined implicitly, rather than being provided as an explicit list.
 * The data set is below.
 * clustering_big.txt
 * The format is:
 * [# of nodes] [# of bits for each node’s label]
 * [first bit of node 1] … [last bit of node 1]
 * [first bit of node 2] … [last bit of node 2]
 * …
 * For example, the third line of the file “0 1 1 0 0 1 1 0 0 1 0 1 1 1 1 1 1 0 1 0 1 1 0 1” denotes the 24 bits associated with node #2.
 * The distance between two nodes u and v in this problem is defined as the Hamming distance— the number of differing bits — between the two nodes’ labels. For example, the Hamming distance between the 24-bit label of node #2 above and the label “0 1 0 0 0 1 0 0 0 1 0 1 1 1 1 1 1 0 1 0 0 1 0 1” is 3 (since they differ in the 3rd, 7th, and 21st bits).
 * The question is: what is the largest value of k such that there is a k-clustering with spacing at least 3? That is, how many clusters are needed to ensure that no pair of nodes with all but 2 bits in common get split into different clusters?
 * NOTE: The graph implicitly defined by the data file is so big that you probably can’t write it out explicitly, let alone sort the edges by cost. So you will have to be a little creative to complete this part of the question. For example, is there some way you can identify the smallest distances without explicitly looking at every pair of nodes?
 */
public class mst_clustering {
    public static List<String> vertexList = new ArrayList<String>();//顶点集
    public static HashMap<String,UnionTree> vertexUnion = new HashMap<>(); //已连接的树集
    public static List<Edge> EdgeQueue = new ArrayList<Edge>();//边集
    public static List<Edge> miniTree = new ArrayList<Edge>();//最小树边集
    public static Integer NUM_clustering = 4;
    public static HashMap<String,ArrayList<String>> hashS1 = new HashMap<>();
    public static HashMap<String,ArrayList<String>> hashS2 = new HashMap<>();
    public static HashMap<String,ArrayList<String>> hashS3 = new HashMap<>();
    public static HashMap<String,Boolean> hashFile = new HashMap<>();

    public static UnionTree find(UnionTree x){
        if (x.parent == x)
            return x;
        else{
            x.parent =  find(x.parent); //路径压缩
            return x.parent;
        }
    }
    public static void union(UnionTree x, UnionTree y){
        UnionTree parentX = find(x);
        UnionTree parentY = find(y);
        if(parentX.equals(parentY))
            return;
        if(parentX.rank > parentY.rank){
            parentY.parent = parentX;
        }
        else if(parentX.rank < parentY.rank)
            parentX.parent = parentY;
        else{
            parentY.parent = parentX;
            parentX.rank += 1;
        }

    }
    public static void makeSet(){
        for(String i : vertexList){
            vertexUnion.put(i, new UnionTree(i,0));
        }
    }

    public static void Kruskal(int sizeVertex){
        List<Edge> EdgeOrder = quickSort(EdgeQueue);
        System.out.println("vertexList.size is "+ vertexList.size());
        System.out.println("EdgeOrder.size is "+ EdgeOrder.size());
        int sum = 0;
        String start,end;
        UnionTree startU, endU;
        makeSet();
        int i = 0;
        for (Edge e : EdgeOrder){
            i++;
            if(miniTree.size()  == vertexList.size() - NUM_clustering){
                break;
            }
            start = e.start;
            end = e.end;
            //System.out.println("Edge is "+ start + "--" + end+"---"+e.key);
            startU = vertexUnion.get(start);
            endU = vertexUnion.get(end);
            if(find(startU) == find(endU)) {
                continue;
            }
            else{
                union(startU, endU);
                sizeVertex -=1;
                sum += e.key;
                miniTree.add(e);
            }
        }
        for (int j = i; j< EdgeOrder.size(); j++){
            start = EdgeOrder.get(j).start;
            end = EdgeOrder.get(j).end;
            startU = vertexUnion.get(start);
            endU = vertexUnion.get(end);
            if(find(startU) == find(endU)) {
                continue;
            }
            else {
                System.out.println("***********Edge is "+ start + "--" + end+"---"+EdgeOrder.get(j).key);
                break;
            }
        }
        System.out.println(">>>>>>>sum is "+ sum);
        System.out.println(">>>>>>>miniTree is "+ miniTree.size());
        System.out.println(">>>>>>>vertexUnion size is "+ vertexUnion.size());

    }

    public static List<Edge> quickSort(List<Edge> EdgeOrder) {
        int n = EdgeOrder.size();
        if (n < 2) {
            return EdgeOrder;
        }
        Edge pivot = EdgeOrder.get(0);
        List<Edge> begin = new ArrayList<>();
        List<Edge> end = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (pivot.key > EdgeOrder.get(i).key)
                begin.add(EdgeOrder.get(i));
            else
                end.add(EdgeOrder.get(i));
        }
        List<Edge> resultEdges = quickSort(begin);
        resultEdges.add(pivot);
        resultEdges.addAll(quickSort(end));
        return resultEdges;
    }

    public static void addEdge(String a, String b, int w) {
        Edge e = new Edge(a, b, w);
        EdgeQueue.add(e);
    }
    public static void readFile(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file), 5 * 1024 * 1024);   //如果是读大文件，设置缓存
            String tempString = null;
            String[] tempEdges = null;
            while ((tempString = reader.readLine()) != null) {
                tempEdges = tempString.split(" ");
                if(tempEdges.length == 3){
                    if(!vertexList.contains(tempEdges[0]))
                        vertexList.add(tempEdges[0]);
                    if(!vertexList.contains(tempEdges[1]))
                        vertexList.add(tempEdges[1]);
                    addEdge(tempEdges[0], tempEdges[1], Integer.valueOf(tempEdges[2]));
                    addEdge(tempEdges[1], tempEdges[0], Integer.valueOf(tempEdges[2]));
                }
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

    public static void readBigFile(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file), 5 * 1024 * 1024);   //如果是读大文件，设置缓存
            String tempString = null;
            String tempEdges = null;
            String s1,s2,s3;
            int dist =0;
            while ((tempString = reader.readLine()) != null) {
                tempEdges = tempString.replaceAll(" ","");
                if(tempEdges.length() == 24) {
                    //将24位截成三段,各为8位,并分别作为key来存储。有利于后面索引
                    s1 = tempEdges.substring(0, 8);
                    s2 = tempEdges.substring(8, 16);
                    s3 = tempEdges.substring(16);
                    if(!hashFile.containsKey(tempEdges)){
                        hashFile.put(tempEdges,true);
                        vertexList.add(tempEdges);
                    }
                    if(hashS1.containsKey(s1))
                        hashS1.get(s1).add(s2+s3);
                    else{
                        hashS1.put(s1, new ArrayList<String>());
                        hashS1.get(s1).add(s2+s3);
                    }
                    if(hashS2.containsKey(s2))
                        hashS2.get(s2).add(s1+s3);
                    else{
                        hashS2.put(s2, new ArrayList<String>());
                        hashS2.get(s2).add(s1+s3);
                    }
                    if(hashS3.containsKey(s3))
                        hashS3.get(s3).add(s1+s2);
                    else{
                        hashS3.put(s3, new ArrayList<String>());
                        hashS3.get(s3).add(s1+s2);
                    }
                }
            }
            System.out.println(hashS1.size());
            System.out.println(hashS2.size());
            System.out.println(hashS3.size());

            for(String ss: hashFile.keySet()) {
                s1 = ss.substring(0, 8);
                s2 = ss.substring(8, 16);
                s3 = ss.substring(16);
                //if(hashS1.containsKey(s1)) {
                    for (String link : hashS1.get(s1)) {
                        if (s2 + s3 == link)
                            continue;
                        dist = getSpace(s2 + s3, link);
                        if (dist < 3 && dist > 0) {
                            addEdge(ss, s1 + link, dist);
                        }
                    }
                //}
                //if(hashS2.containsKey(s2)) {
                    for (String link : hashS2.get(s2)) {
                        if (s1 + s3 == link)
                            continue;
                        dist = getSpace(s1 + s3, link);
                        if (dist < 3 && dist > 0) {
                            addEdge(ss, link.substring(0,8) + s2 + link.substring(8,16), dist);
                        }
                    }
               // }
                //if(hashS3.containsKey(s3)) {
                    for (String link : hashS3.get(s3)) {
                        if (s1 + s2 == link)
                            continue;
                        dist = getSpace(s1 + s2, link);
                        if (dist < 3 && dist > 0) {
                            addEdge(ss, link + s3, dist);
                        }
                    }
                //}
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
        System.out.println("EdgeQueue.size() is :" + EdgeQueue.size());
    }

    /*
    该问题的思路是:顶点有20w,数据量过大,而且相互点的距离都没有直接给出,需要对24位进行比较,无法直接对所有边进行排序并使用kruskal算法
    根据simhash的启发和题目的要求,我们只关心相互距离为1和2的边。我们首先将每个顶点分成三份,分别进行存储。这样利于之后进行计算各边的距离。
    将各边距离为1和2的边都存入EdgeQueue后,就可以根据kruskal算法进行Union-Find计算了。最终得到clustering的数量。
     */
    public static void getClustering(){
        int clustering = hashFile.size();
        String start,end;
        UnionTree startU, endU;
        makeSet();
        for (Edge e : EdgeQueue){
            start = e.start;
            end = e.end;
            startU = vertexUnion.get(start);
            endU = vertexUnion.get(end);
            if(find(startU) == find(endU))
                continue;
            else{
                union(startU, endU);
                clustering--;
                miniTree.add(e);
            }
        }
        System.out.println(">>>>>>> vertexList size is "+ vertexList.size());
        System.out.println(">>>>>>>miniTree size is "+ miniTree.size());
        System.out.println(">>>>>>>clustering is "+ clustering);

    }
    //计算Hamming distance,因为是长String,可直接逐个字符判断,当hamming distance大于2时就退出。
    public static int getSpace(String node1, String node2){
        int dist = 0;
        for(int i = 0; i < node1.length(); i++){
            if(node1.charAt(i) != node2.charAt(i))
                dist++;
            if(dist > 2)
                return dist;
        }
        return dist;
    }

    public static void main(String[] args){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(System.currentTimeMillis()));

        //question#1
//        String file = "src/main/resources/clustering1.txt";
//        readFile(file);
//        System.out.println("Readfile is finished!");
//        Kruskal(vertexList.size());

        //question#2
        String file = "src/main/resources/clustering_big.txt";
        readBigFile(file);
        System.out.println("Readfile is finished!");
        getClustering();
        SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(ddf.format(System.currentTimeMillis()));


    }
}
class Edge {
    String start;
    String end;
    int key;
    Edge(String start, String end, int key) {
        this.start = start;
        this.end  = end;
        this.key = key;
    }
}
class UnionTree{
    String key;
    UnionTree parent;
    int rank;
    UnionTree(String key, int rank){
        this.key = key;
        this.parent = this;
        this.rank = rank;
    }
}
