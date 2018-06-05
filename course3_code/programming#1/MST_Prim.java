package sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by wanli on 15/05/2018.
 * 需要构造无向图,即每个边要构造两次,起点和终点调换后也要添加一次。
 * -3612829
 * 3.In this programming problem you’ll code up Prim’s minimum spanning tree algorithm.
 * Download the text file below.
 * edges.txt
 * This file describes an undirected graph with integer edge costs. It has the format
 * [number_of_nodes] [number_of_edges]
 * [one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]
 * [one_node_of_edge_2] [other_node_of_edge_2] [edge_2_cost]
 * …
 * For example, the third line of the file is “2 3 -8874”, indicating that there is an edge connecting vertex #2 and vertex #3 that has cost -8874.
 * You should NOT assume that edge costs are positive, nor should you assume that they are distinct.
 * Your task is to run Prim’s minimum spanning tree algorithm on this graph. You should report the overall cost of a minimum spanning tree — an integer, which may or may not be negative — in the box below.
 * IMPLEMENTATION NOTES: This graph is small enough that the straightforward O(mn) time implementation of Prim’s algorithm should work fine. OPTIONAL: For those of you seeking an additional challenge, try implementing a heap-based version. The simpler approach, which should already give you a healthy speed-up, is to maintain relevant edges in a heap (with keys = edge costs). The superior approach stores the unprocessed vertices in the heap, as described in lecture. Note this requires a heap that supports deletions, and you’ll probably need to maintain some kind of mapping between vertices and their positions in the heap.
 */
public class MST_Prim {
    public static List<String> vertexList = new ArrayList<String>();//结点集
    public static List<Edge> EdgeQueue = new ArrayList<Edge>();//边集
    public static HashMap<String,Boolean> newVertex = new HashMap<>();//已经 访问过的结点
    public static String START = "500";  //起点随意指定,因为最终结果都一样

    public static void Prim(){
        Integer sum = 0;
        newVertex.put(START, true);
        HashMap<Edge,Boolean> EdgeTemp = new HashMap<>();

        EdgeTemp = addEdgeToTemp(EdgeTemp,START);
        System.out.println("vertexList.size() is " + vertexList.size());
        System.out.println("EdgeQueue.size() is " + EdgeQueue.size());
        System.out.println("EdgeTemp.size() is " + EdgeTemp.size());

        while(newVertex.size() !=  vertexList.size()){
            Edge small = new Edge("X","Y", 100000);
            for (Edge e : EdgeTemp.keySet()){
                if(newVertex.containsKey(e.start)&& newVertex.containsKey(e.end))
                    continue;
                if(small.key > e.key)
                    small =  e;
            }
            sum += small.key;
            newVertex.put(small.end,true);
            //System.out.println("small is " + small.start + "++" + small.end);
            EdgeTemp = addEdgeToTemp(EdgeTemp, small.end);
            EdgeTemp.remove(small);
        }
        System.out.println("sum is " + sum);

    }

    public static HashMap<Edge,Boolean> addEdgeToTemp(HashMap<Edge,Boolean> EdgeTemp,String v){
        for(Edge e : EdgeQueue){
            if(EdgeTemp.containsKey(e))
                continue;
            if(e.start.equals(v)) {
                EdgeTemp.put(e, true);
            }
        }
        return EdgeTemp;
    }

    public static void addEdge(String a, String b, int w) {
        Edge e = new Edge(a, b, w);
        MST_Prim.EdgeQueue.add(e);
    }

    public static void readFile(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file), 5 * 1024 * 1024);   //如果是读大文件，设置缓存
            String tempString = null;
            String[] tempEdges = null;
            Integer last = 0;
            while ((tempString = reader.readLine()) != null) {
                //System.out.println(tempString);
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
    public static void main(String[] args){
        String file = "src/main/resources/edges.txt";
        readFile(file);
        System.out.println("Readfile is finished!");
        Prim();
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
