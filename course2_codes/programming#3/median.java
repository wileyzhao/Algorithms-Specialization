
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by wanli on 02/05/2018.
 * 创建两个堆：最大堆和最小堆（最大堆即父节点大于子节点的堆，反之则是最小堆）；
* 先初始化一个median，比如第一个数字（这个不重要，最终的中位数都还是一样的）
* 每次读入一个数后，我们将它和之前的median大小进行比较，如果大于median，那么就把它插入到最小堆当中；反之，就插入最大堆当中。
* 有了上述结论后，我们还不能保证中位数就在两个根节点中，因为两个堆的大小可能会差的很大，因此每次读入一个数并且插入相应的堆后，我们都要检查两个堆的大小，然后平衡他们的大小（只有在两个堆的大小差异不大于1的情况下， 中位数才是两个根节点中的一个）
* 平衡的具体做法是：如果两个堆的大小差异超过了1，那么就把size较大的那个堆的根节点pop出来，并将其插入到size较小的堆中，并且将那个堆的根节点pop的值赋给median；
* 最后就是计算中位数了，如果最小堆的size比最大堆大1，那么中位数就是最小堆根节点；如果两者大小相等，或者最大堆的size比最小堆大1，那么中位数就是最大堆的根节点。
 */
public class median {
    public static class smallHeap {
        private int size;
        private int[] value;

        public smallHeap(int[] arr) {
            this.size = arr.length;
            this.value = arr;
        }
        public void insert(int elment){
            int[] newData = new int[this.size+1];
            System.arraycopy(this.value, 0, newData, 0, this.size);
            this.size += 1;
            newData[this.size - 1] = elment;
            this.value = newData;
            if(this.size == 1) {
                this.value[0] = elment;
                return;
            }
            int j = this.size-1;
            int temp;
            while (j-1 >= 0){
                if(this.value[(j-1)/2] == this.value[j]){
                    System.out.println("SmallHeap Error! that's same:" + this.value[(j-1)/2] + "--" + this.value[j]);
                    return;
                }
                else if(this.value[(j-1)/2] > this.value[j]){
                    temp = this.value[j];
                    this.value[j] = this.value[(j-1)/2];
                    this.value[(j-1)/2] = temp;
                }
                j = (j-1)/2;
            }
        }
        public int pop(){
            int smallest = this.value[0];
            this.value[0] = this.value[this.size-1];
            this.size -= 1;
            int j = 0;
            int temp = 0;
            while(2*(j+1) < this.size){
                if(this.value[2*(j+1)-1] < this.value[2*(j+1)]) {   //如果左子节点比右子节点小,就比较父节点和左子节点
                    if (this.value[j] > this.value[2 * (j + 1) - 1]) {
                        temp = this.value[j];
                        this.value[j] = this.value[2 * (j + 1) - 1];
                        this.value[2 * (j + 1) - 1] = temp;
                    }
                    j = 2*(j+1)-1;
                }
                else{   //如果左子节点不小于右子节点,就比较父节点和右子节点
                    if(this.value[j] > this.value[2*(j+1)]){
                        temp = this.value[j];
                        this.value[j] = this.value[2*(j+1)];
                        this.value[2*(j+1)] = temp;
                    }
                    j = 2*(j+1);
                }
            }
            return smallest;
        }

    }
    public static class largeHeap {
        private int size;
        private int[] value;

        public largeHeap(int[] arr) {
            this.size = arr.length;
            this.value = arr;
        }

        public void insert(int elment){
            int[] newData = new int[this.size+1];
            System.arraycopy(this.value, 0, newData, 0, this.size);
            this.size += 1;
            newData[this.size - 1] = elment;
            this.value = newData;
            if(this.size == 1) {
                this.value[0] = elment;
                return;
            }

            int j = this.size-1;
            int temp;
            while (j-1 >= 0){
                if(this.value[(j-1)/2] == this.value[j]){
                    System.out.println("LargeHeap Error! that's same:" + this.value[(j-1)/2] + "--" + this.value[j]);
                    return;
                }
                else if(this.value[(j-1)/2] < this.value[j]){
                    temp = this.value[j];
                    this.value[j] = this.value[(j-1)/2];
                    this.value[(j-1)/2] = temp;
                }
                j = (j-1)/2;
            }
        }
        public int pop(){
            int largest = this.value[0];
            this.value[0] = this.value[this.size-1];
            this.size -= 1;
            int j = 0;
            int temp = 0;
            while(2*(j+1) < this.size){
                if(this.value[2*(j+1)-1] > this.value[2*(j+1)]) {   //如果左子节点比右子节点大,就比较父节点和左子节点
                    if (this.value[j] < this.value[2 * (j + 1) - 1]) {
                        temp = this.value[j];
                        this.value[j] = this.value[2 * (j + 1) - 1];
                        this.value[2 * (j + 1) - 1] = temp;
                    }
                    j = 2*(j+1)-1;
                }
                else{   //如果左子节点不大于右子节点,就比较父节点和右子节点
                    if(this.value[j] < this.value[2*(j+1)]){
                        temp = this.value[j];
                        this.value[j] = this.value[2*(j+1)];
                        this.value[2*(j+1)] = temp;
                    }
                    j = 2*(j+1);
                }
            }
            return largest;
        }
    }

    public static void main(String[] args) {
        String filePath = "src/main/resources/Median.txt";
        File file = new File(filePath);
        BufferedReader reader = null;
        int[] smallInit = {};
        int[] largeInit = {};
        smallHeap smallH = new smallHeap(smallInit);
        largeHeap largeH = new largeHeap(largeInit);
        int median = 5000;  //这个初始化的值不重要,不影响之后的结果。
        int sumElement = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int tempVertex = 0;
            int pop = 0;

            while ((tempString = reader.readLine()) != null) {
                tempVertex = Integer.valueOf(tempString.trim());
                if(tempVertex > median){
                    smallH.insert(tempVertex);
                }
                else{
                    largeH.insert(tempVertex);
                }
                if(largeH.size - smallH.size > 1){
                    pop = largeH.pop();
                    smallH.insert(pop);
                    median = pop;
                }
                else if(smallH.size - largeH.size > 1){
                    pop = smallH.pop();
                    largeH.insert(pop);
                    median = pop;
                }

                if(largeH.size == smallH.size + 1){
                    sumElement += largeH.value[0];
                }
                else if(largeH.size == smallH.size - 1){
                    sumElement += smallH.value[0];
                }
                else if(largeH.size == smallH.size ){
                    sumElement += largeH.value[0];
                }
                else
                    System.out.println("EEEEEEERRRRRRROOOOOOORRRRRR!");
            }
            System.out.println(sumElement);
            System.out.println(sumElement%10000);

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
}
