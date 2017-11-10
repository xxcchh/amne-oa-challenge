import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chen on 10/21/17.
 */
public class LinearTimeSolution {

    /*
    * Current result in the window
    * */
    int currentSubrangeNum = 0;
    /*
    * Current length of increasing/decreasing/flat array
    * */
    int currentNum = 1;
    /*
    * Current direction of array
    * 1 --- means current direction is increasing
      0 --- means current direction is flat
      -1 --- means current direction is decreasing
      2 --- is the default value at beginning
    * */
    int currentDir = 2;
    /*
    * Current index of the status list
    * */
    int currentQueueIndex = 0;
    /*
    * Status list
    * */
    List<int[]> queue = new LinkedList<>();

     /* Optimized linear time method with time complexity O(N),
        where N is the total number of array
    * @param fileName input file name*/

    public void linearTimeSol(String fileName){

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader sb = new BufferedReader(fileReader);
            String[] numberStr = sb.readLine().split(" ");
            int N = Integer.valueOf(numberStr[0]);
            int K = Integer.valueOf(numberStr[1]);
            String[] val = sb.readLine().split(" ");
            findDiff(N, K, val, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void findDiff(int N, int K, String[] number, String input) throws IOException {

        StringBuilder sb = new StringBuilder();

        // If we only have 1 element
        if (N == 1){
            sb.append(0);
            writeToFile(sb, input);
            return;
        }

        // If the window size is not less than the total number
        if (K >= N){
            int currentSubrange = calculateWindow(0, N, number);
            sb.append(currentSubrange);
            writeToFile(sb, input);
            return;
        }

        // If the window size is smaller than the total number
        for (int i = 1; i < N; i++){

            if (i < K){
                calculateTrend(i, number);
            }else{
                sb.append(currentSubrangeNum);
                sb.append("\n");
                calculateTrend(i, number);
                pollQueue();
            }

        }

        sb.append(currentSubrangeNum);
        writeToFile(sb, input);

    }

    /*Calculate the difference between increasing subrange and decreasing subrange
    * @param i Index of current price in array
    * @param number Price array */

    public void calculateTrend(int i, String[] number){

        int cur = Integer.valueOf(number[i]);
        int pre = Integer.valueOf(number[i-1]);

        if (cur > pre){
            if (currentDir == 1){
                currentNum ++;
            }else if (currentDir != 2){
                currentNum = 1;
                currentQueueIndex ++;
            }
            currentDir = 1;
            addQueue();
            currentSubrangeNum += currentNum;
        }else if (cur < pre){
            if (currentDir == -1){
                currentNum ++;
            }else if (currentDir != 2){
                currentNum = 1;
                currentQueueIndex ++;
            }
            currentDir = -1;
            addQueue();
            currentSubrangeNum -= currentNum;
        }else if (cur == pre){
            if (currentDir == 0){
                currentNum ++;
            }else if (currentDir != 2){
                currentNum = 1;
                currentQueueIndex ++;
            }
            currentDir = 0;
            addQueue();
        }

    }

    /*Add status to status list
    */

    public void addQueue(){

        if (queue.size() < currentQueueIndex + 1){
            queue.add(new int[]{currentDir, currentNum});
        }else{
            int[] cur = queue.get(currentQueueIndex);
            cur[0] = currentDir;
            cur[1] = currentNum;
        }

    }

    /*Poll the head of status list
    */

    public void pollQueue(){

        int[] cur = queue.get(0);
        int curDir = cur[0];
        int curNum = cur[1];
        if (currentQueueIndex == 0){
            currentNum --;
        }
        if (curDir == 1){
            currentSubrangeNum -= curNum;
            cur[1] --;
        }else if (curDir == -1){
            currentSubrangeNum += curNum;
            cur[1] --;
        }else {
            cur[1] --;
        }

        if (cur[1] == 0){
            queue.remove(0);
            currentQueueIndex --;
        }

    }

    /*Calculate the difference between the number of increasing subranges and decreasing subranges
    * @param start Start index of the window
    * @param end End index of the window */

    public int calculateWindow(int start, int end, String[] number){

        int currentSubrangeNum = 0;
        int currentNum = 1;
        int currentDir = 0;
        // 1 --- means current direction is increasing
        // 0 --- means current direction is flat
        // -1 --- means current direction is decreasing

        for (int i = start+1; i < end; i++){
            int cur = Integer.valueOf(number[i]);
            int pre = Integer.valueOf(number[i-1]);
            if (cur > pre){
                if (currentDir == 1){
                    currentNum ++;
                }else{
                    currentNum = 1;
                    currentDir = 1;
                }
                currentSubrangeNum += currentNum;
            } else if (cur < pre) {
                if (currentDir == -1){
                    currentNum ++;
                }else{
                    currentNum = 1;
                    currentDir = -1;
                }
                currentSubrangeNum -= currentNum;
            }else{
                if (currentDir == 0){
                    currentNum ++;
                }else{
                    currentNum = 1;
                    currentDir = 0;
                }
            }
        }
        return currentSubrangeNum;
    }

    /* Write the result string to file
    *  @param sb The result strings that we need to write to the output file.
    *  @param input The input file name
    */

    public void writeToFile(StringBuilder sb, String input) throws IOException {
        String output = input + "_output_linear.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
        bw.write(sb.toString());
        bw.close();
    }



    public static void main(String[] args) {

        String fileName = args[0];
        LinearTimeSolution sol = new LinearTimeSolution();
        sol.linearTimeSol(fileName);

    }




}
