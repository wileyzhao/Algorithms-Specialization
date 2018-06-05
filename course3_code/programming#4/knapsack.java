package sandbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wanli on 04/06/2018.
 * Problem1:
 * knapsack1.txt
 * This file describes a knapsack instance, and it has the following format:
 * [knapsack_size][number_of_items]
 * [value_1] [weight_1]
 * [value_2] [weight_2]
 * For example, the third line of the file is “50074 659”, indicating that the second item has value 50074 and size 659, respectively.
 * You can assume that all numbers are positive. You should assume that item weights and the knapsack capacity are integers.
 * In the box below, type in the value of the optimal solution.
 * ADVICE: If you’re not getting the correct answer, try debugging your algorithm using some small test cases. And then post them to the discussion forum!
 * 
 * Problem2:
 * knapsack_big.txt
 * This file describes a knapsack instance, and it has the following format:
 * [knapsack_size][number_of_items]
 * [value_1] [weight_1]
 * [value_2] [weight_2]
 * For example, the third line of the file is “50074 834558”, indicating that the second item has value 50074 and size 834558, respectively. As before, you should assume that item weights and the knapsack capacity are integers.
 * This instance is so big that the straightforward iterative implemetation uses an infeasible amount of time and space. So you will have to be creative to compute an optimal solution. One idea is to go back to a recursive implementation, solving subproblems — and, of course, caching the results to avoid redundant work — only on an “as needed” basis. Also, be sure to think about appropriate data structures for storing and looking up solutions to subproblems.
 * In the box below, type in the value of the optimal solution.
 * ADVICE: If you’re not getting the correct answer, try debugging your algorithm using some small test cases. And then post them to the discussion forum!
 */
public class knapsack {
    public static List<Item> itemsList = new ArrayList<>();
    public static HashMap<Integer, Item> itemsPackage = new HashMap<>();
    public static Integer maxWeiht = 0;
    public static Integer[][] packs;
    public static Integer[] packs2;
    public static List<Integer> weightList = new ArrayList<>();

    public static Integer getMax(Integer iA, Integer iB){
        if(iA > iB)
            return iA;
        else
            return iB;
    }
    public static Integer getValue(int num, int weight){
        for(int i =0; i < num; i++){
            packs[i][0] = 0;
        }
        for(int j = 0; j < weight; j++){
            packs[0][j] = 0;
        }

        for(int i = 1; i< num; i++){
            for(int j = 1; j < weight; j++){
                if(itemsList.get(i-1).weight <= j){
                    packs[i][j] = getMax(packs[i-1][j], packs[i-1][j-itemsList.get(i-1).weight]+ itemsList.get(i-1).value);
                }
                else{
                    packs[i][j] = packs[i-1][j];
                }
            }
        }
        return packs[num-1][weight-1];
    }

    /*
    *  通过算法优化,将二维数组packs[num][weight]压缩成packs[weight],虽然时间复杂度仍是O(nW),但空间复杂度降为了O(W)。
     */
    public static Integer getValue2(int num, int weight){
        for(int j = 0; j < weight; j++){
            packs2[j] = 0;
        }
        for(int i = 1; i< num; i++){
            System.out.print("\n");
            for(int j = weight-1; j > itemsList.get(i-1).weight; j--){
                if(itemsList.get(i-1).weight <= j){
                    packs2[j] = getMax(packs2[j], packs2[j-itemsList.get(i-1).weight]+itemsList.get(i-1).value);
                }
                System.out.print(" j = "+ j + " pack = " + packs2[j] + " || ");
            }
        }
        return packs2[weight-1];
    }

    public static void addItems(Integer weight, Integer value){
        Item i = new Item(weight,value);
        itemsList.add(i);

    }

    public static void readFile(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));   //如果是读大文件，设置缓存
            String tempString = null;
            String[] temps = null;
            Integer symbol = 0;
            String temp;
            while ((tempString = reader.readLine()) != null) {
                temps = tempString.trim().split(" ");
                if(symbol == 0){
                    symbol++;
                    maxWeiht = Integer.valueOf(temps[0]);
                    continue;
                }
                //symbol++;
                //temp = temps[1].substring(0,temps[1].length()-3);
                weightList.add(Integer.valueOf(temps[1]));
                addItems(Integer.valueOf(temps[1]),Integer.valueOf(temps[0]));
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
    public static List<Item> quickSort(List<Item> ilist) {
        int n = ilist.size();
        if (n < 2) {
            return ilist;
        }
        Item pivot = ilist.get(0);
        List<Item> begin = new ArrayList<>();
        List<Item> end = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if ((float)pivot.value/pivot.weight < (float)ilist.get(i).value/ilist.get(i).weight)
                begin.add(ilist.get(i));
            else
                end.add(ilist.get(i));
        }
        List<Item> resultItems = quickSort(begin);
        resultItems.add(pivot);
        resultItems.addAll(quickSort(end));
        return resultItems;
    }


    public static List<Integer> quickSortWeight(List<Integer> ilist) {
        int n = ilist.size();
        if (n < 2) {
            return ilist;
        }
        Integer pivot = ilist.get(0);
        List<Integer> begin = new ArrayList<>();
        List<Integer> end = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (pivot > ilist.get(i))
                begin.add(ilist.get(i));
            else
                end.add(ilist.get(i));
        }
        List<Integer> resultItems = quickSortWeight(begin);
        resultItems.add(pivot);
        resultItems.addAll(quickSortWeight(end));
        return resultItems;
    }
    public static void testmain(){
        addItems(12,4);
        addItems(1,2);
        addItems(4,10);
        addItems(3,1);
        addItems(8,2);

        maxWeiht = 20;
        int size = itemsList.size();
        packs = new Integer[size+1][maxWeiht];
        Integer result = getValue(size+1, maxWeiht);
        System.out.println(result);

        for(int i = 0; i< size+1; i++){
            System.out.print("\n" );
            for(int j =0; j< maxWeiht; j++){
                System.out.print(" j = "+ j + " pack = " + packs[i][j] + " || ");
            }
        }

        packs2 = new Integer[maxWeiht];
        Integer result2 = getValue2(size+1, maxWeiht);
        System.out.println("\n" + result2);
        for(int j =0; j< maxWeiht; j++){
            System.out.print(" j = "+ j + " pack = " + packs2[j] + " || ");
        }
    }

    public static void problem1(){
        String file = "src/main/resources/knapsack1.txt";
        readFile(file);
        int size = itemsList.size();
        packs = new Integer[size+1][maxWeiht];
        Integer result = getValue(size+1, maxWeiht);
        System.out.println(result);

    }

    public static void problem2(){
        String file = "src/main/resources/knapsack_big.txt";
        readFile(file);
        int size = itemsList.size();
        System.out.println(size);

        packs2 = new Integer[maxWeiht];
        Integer result2 = getValue2(size+1, maxWeiht);
        System.out.println("\n" + result2);
    }

    public static void main(String[] args){
        testmain();
        //problem1();
        //problem2();
    }
}

class Item{
    Integer weight;
    Integer value;
    Item(Integer weight, Integer value){
        this.weight = weight;
        this.value = value;
    }
}
