package sandbox;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by wiley on 26/04/2018.
 * the programming assignment #1 on Course 2 in week 1st
 * tips:该代码读取和处理的数据量很大，需要在运行的时候修改下jvm的运行参数。主要是Xss,我用的是-Xss50m测试可行,-Xss10m会报StackOverflowError。
 */
public class sccClass {
    private static Integer count = 0;
    //为了优化，把图的dfs与求图的伪拓扑排序分成两个函数
    private static void dfsStack(HashMap<String, ArrayList<String>> graph, HashMap<String, Boolean> visited, Stack<String> topological, String start) {
        if (!visited.containsKey(start)) {
            visited.put(start,true);
            if(graph.get(start) != null) {
                for (String c : graph.get(start)) {
                    if (!visited.containsKey(c)) {
                        dfsStack(graph, visited, topological, c);//递归访问其邻近节点
                    }
                }
            }
            topological.push(start);
        }
    }

    private static void dfs(HashMap<String, ArrayList<String>> graph, HashMap<String, Boolean> visited, String start) {
        if (!visited.containsKey(start)) {
            count++;
            visited.put(start,true);
            if(graph.get(start) != null) {
                for (String c : graph.get(start)) {
                    if (!visited.containsKey(c)) {
                        dfs(graph, visited, c);//递归访问其邻近节点
                    }
                }
            }
        }
    }

    private static void topological_Sorting(HashMap<String, ArrayList<String>> graph, Stack<String> topological, FileWriter fileWriter) {

        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        for (String c : graph.keySet()) {
            if (!visited.containsKey(c)) {
                dfsStack(graph, visited, topological, c);
            }
        }
    }

    private static HashMap<String, ArrayList<String>> reverseGraph(HashMap<String, ArrayList<String>> graph){
        HashMap<String, ArrayList<String>> reversedGraph = new HashMap<String, ArrayList<String>>();

        for (String c : graph.keySet()){
            for (String d : graph.get(c)){
                if(reversedGraph.containsKey(d)){
                    reversedGraph.get(d).add(c);
                }
                else{
                    ArrayList<String> egge = new ArrayList<>();
                    egge.add(c);
                    reversedGraph.put(d,egge);
                }
            }
        }
        return reversedGraph;
    }

    public static void readFile(String filePath, HashMap<String, ArrayList<String>> graph, ArrayList<String> vertexes, FileWriter fileWriter) {
        File file = new File(filePath);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file), 5 * 1024 * 1024);   //如果是读大文件，设置缓存
            String tempString = null;
            String[] tempEdges = null;
            Integer last = 0;
            while ((tempString = reader.readLine()) != null) {
                tempEdges = tempString.split(" ");

                if(last < Integer.valueOf(tempEdges[0])){
                    last = Integer.valueOf(tempEdges[0]);
                    ArrayList<String> line = new ArrayList<String>();
                    line.add(tempEdges[1]);
                    graph.put(tempEdges[0],line);
                }
                else{

                    graph.get(tempEdges[0]).add(tempEdges[1]);
                }
            }
            fileWriter.write(graph.toString()+ "\n");
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
    public static void main(String[] args) {
        try {
            //数据太大，输出太多了，为了调试方便，将debug信息打印到log文件中
            FileWriter fileWriter = new FileWriter("log.log");


            HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();

            ArrayList<String> vertexes = new ArrayList<String>();
            String file = "src/main/resources/SCC.txt";
            readFile(file, graph, vertexes, fileWriter);
            fileWriter.write("------finish readFile------\n");
            System.out.println("----finish readFile----\n");

            Stack<String> topological = new Stack<String>();
            topological_Sorting(graph, topological, fileWriter);
            fileWriter.write(topological.toString() + "\n");
            fileWriter.write("------finish topologic_sort------\n");
            System.out.println("----finish topologic_sort----\n");

            graph = reverseGraph(graph);
            fileWriter.write("------finish reverseGraph------\n");
            System.out.println("----finish reverseGraph----\n");
            fileWriter.write(graph.toString() + "\n");

            HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
            ArrayList<Integer> numbers = new ArrayList<Integer>();
            ArrayList<Integer> new_Numbers = new ArrayList<Integer>();
            fileWriter.write("------start count SCC------\n");
            System.out.println("------start count SCC------");
            String c;
            while (topological.empty() == false) {
                count = 0;
                c = topological.pop();
            
                dfs(graph, visited, c);
                numbers.add(count);
            }
            fileWriter.write("------finish count SCC------\n");
            System.out.println("------finish count SCC------\n");
            fileWriter.write(numbers.toString()+ "\n");
            for(Integer i : numbers){
                if (i > 100){   //数据太多，先简要筛一下。
                    new_Numbers.add(i);
                }
            }
            System.out.println(new_Numbers.toString());
            fileWriter.write(new_Numbers.toString()+ "\n");

            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
