
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by wanli on 04/05/2018.
 *  因为数据量比较大，所以先分划分区域，再利用hashtable的key查询时间O(1)的特性
 */
public class heap2Sum {
        public static void main(String[] args) {
            String filePath = "src/main/resources/algo1-programming_prob-2sum.txt";
            File file = new File(filePath);
            BufferedReader reader = null;

            Hashtable<Integer,Hashtable<Integer,Boolean>> hashX = new Hashtable<>();
            Hashtable<Integer,Integer> t = new Hashtable<>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            Integer key,value;
            int length = 0;

            while ((tempString = reader.readLine()) != null) {
                length = tempString.length();
                //给的数字太长，只能用long，但可以将数字分成两部分，后四位和之前的数字。然后将key相同的数字的后四位都再加入到新的hashtable中，首先去掉了相同冗余数字
                    key = Integer.valueOf(tempString.substring(0,length-4));    
                    value = Integer.valueOf(tempString.substring(length-4));

                    if(hashX.containsKey(key))
                    {
                        hashX.get(key).put(value,true);

                    }
                    else{
                        Hashtable<Integer, Boolean> hashValue = new Hashtable<Integer, Boolean>();
                        hashValue.put(value, true);
                        hashX.put(key,hashValue);
                    }
            }
            System.out.println("read the file finish!");

            //先筛选出两位数相加在[-10000,10000]的两个数字，这要根据-10000和10000 的数字特性来分析。
            //且尽量使用hasht的containsKey，效率很高。
            for (Integer i : hashX.keySet()){
                //如果后四位之前的数字正好互为相反数,那两数相加一定在[-10000,10000]之间,由此变换成后四位数字的相减。
                if(hashX.containsKey(0 - i)){
                    for (Integer j : hashX.get(i).keySet()){
                        for (Integer k : hashX.get(0-i).keySet()){
                            if(i > 0-i)
                                t.put(j-k,i);
                            else
                                t.put(k-j,i);
                        }
                    }
                }
                //如果后四位之前的数字正负相反且相加为1,那两数判断后四位的大小,确定两数相加是否在[-10000,10000]之间。
                else if (hashX.containsKey(1 - i)) {
                    for (Integer j : hashX.get(i).keySet()){
                        for (Integer k : hashX.get(1-i).keySet()){
                            if(i > 1-i){    //
                                if(j < k){
                                    t.put(10000+j-k,i);
                                }
                            }
                            else{
                                if(j> k)
                                    t.put(10000+k-j,i);
                            }
                        }
                    }

                }
                else if (hashX.containsKey(-1 - i)){
                    for (Integer j : hashX.get(i).keySet()){
                        for (Integer k : hashX.get(-1-i).keySet()){
                            if(i > -1-i){
                                if(j > k){
                                    t.put(j-10000-k,i);
                                }
                            }
                            else{
                                if(j < k)
                                    t.put(k-10000-j,i);
                            }
                        }
                    }
                }
            }
            System.out.println("t is " + t.size());

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
}
