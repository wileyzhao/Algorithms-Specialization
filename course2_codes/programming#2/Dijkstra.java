package sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wiley on 30/04/2018.
 * the programming assignment #2 on Course 2 in week 2st
 */
public class Dijkstra {
    private static Integer LENGTH = 200;
    private static Integer STARTVERTEX = 1;
    private static Integer INFINITY = 1000000;

    public static void shortPathHeap(HashMap<Integer, HashMap<Integer,Integer>> graph){
        //可以用heap来替代vertexT,并且利用heap的特性来pop出最小值。这样可以将runtime由O(mn)优化成O(mlogn),待实现
    }

    public static void shortPath(HashMap<Integer, HashMap<Integer,Integer>> graph){
        HashMap<Integer,Integer> weights = new HashMap<Integer,Integer>();
        ArrayList<Integer> vertexT = new ArrayList<Integer>();
        //先初始化weights、vertexT,并将直接关联在源点STARTVERTEX的顶点权重放在weights中。
        weights.put(1,0); // 将源点s自己的权重为0加到权重列表中
        vertexT.add(1); //将源点s加到已确定最小路径的顶点集合中
        // HashMap<String,Integer> edge = graph.get(STARTVERTEX);
        for (int i =2; i<= LENGTH; i++){
            if(!graph.get(STARTVERTEX).containsKey(i))
            {
                weights.put(i,INFINITY);
            }
            else
                weights.put(i,graph.get(STARTVERTEX).get(i));
        }

        //加入循环,寻找除了vertesT中顶点外,源点s权重最小的顶点s'。再根据该顶点s'更新和s'直接关联的顶点权重。
        Integer vertex = STARTVERTEX;
        while(vertexT.size() < LENGTH){ //直到所有顶点都在vertexT中才结束循环
            vertex = getTheSmallestVertex(weights,vertexT);
            vertexT.add(vertex);
            for (Integer i : graph.get(vertex).keySet()){
                if (!vertexT.contains(i)){
                    if (weights.get(i) > weights.get(vertex) + graph.get(vertex).get(i)){
                        weights.put(i, weights.get(vertex) + graph.get(vertex).get(i));
                    }
                }
            }
        }
        System.out.println(weights.toString());
    }

    public static Integer getTheSmallestVertex(HashMap<Integer,Integer> weights,ArrayList<Integer> vertexT){
        Integer smallest = INFINITY;
        Integer vertex = STARTVERTEX;
        for(int i =2;i <= LENGTH; i++ ){
            if (!vertexT.contains(i)){
                if( weights.get(i) < smallest) {
                    smallest = weights.get(i);
                    vertex = i;
                }
            }
        }
        return vertex;
    }

    public static void readFile(String filePath, HashMap<Integer, HashMap<Integer,Integer>> graph) {
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            String[] tempEdges = null;
            String[] line = null;
            Integer vertex = null;
            Integer last = 0;
            while ((tempString = reader.readLine()) != null) {
                //System.out.println(tempString);
                line = tempString.split("\t");
                vertex = Integer.valueOf(line[0].trim());
                //ArrayList<String> edges = new ArrayList<String>();
                HashMap<Integer,Integer> edges = new HashMap<Integer, Integer>();
                graph.put(vertex,edges);
                for (int i=1; i < line.length; i++)
                {
                    tempEdges = line[i].trim().split(",");
                    edges.put(Integer.valueOf(tempEdges[0]),Integer.valueOf(tempEdges[1]));

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
        HashMap<Integer, HashMap<Integer,Integer>> graph = new HashMap<Integer, HashMap<Integer, Integer>>();

        String file = "src/main/resources/Dijkstra.txt";
        readFile(file, graph);
        System.out.println("----finish readFile----\n");
        shortPath(graph);

    }
}
