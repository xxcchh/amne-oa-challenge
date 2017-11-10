import java.io.*;

/**
 * Created by chen on 10/21/17.
 */
public class BruteForceSolution {

    /* Brute force method with time complexity O(KN),
        where K is the size of window and N is the total number of array
    * @param fileName input file name*/

    public void bruteForceSol(String fileName){

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

    /* Main function to find the difference between increasing subranges and decreasing subranges
    * @param N Positive integers of average home sale price
    * @param K Window size
    * @param number Average home sale price array
    * @param input Input file name*/

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
        for (int i = 0; i <= N-K; i++){
            int currentSubrange = calculateWindow(i, i+K, number);
            sb.append(currentSubrange);
            sb.append("\n");
        }

        sb.deleteCharAt(sb.length()-1);
        writeToFile(sb, input);

    }

    /*Calculate the difference between number of increasing subranges and decreasing subranges
    * @param start Start index of the window
    * @param end End index of the window */

    public int calculateWindow(int start, int end, String[] number){

        int currentSubrangeNum = 0;
        int currentNum = 1;
        int currentDir = 0; // 1 --- means current direction is increasing
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
        String output = input + "_output_bruteforce.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(output));
        bw.write(sb.toString());
        bw.close();
    }

    public static void main(String[] args) {

        String fileName = args[0];
        BruteForceSolution sol = new BruteForceSolution();
        sol.bruteForceSol(fileName);

    }



}
