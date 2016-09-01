import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Created by danelennon on 22/08/2016.
 */
public class Perfect {
    public static void main(String[] args) {
        int END_NUM = 1000;

        for (int i=1; i<END_NUM; ++i){
            //get all the numbers that divide evenly into the current number
            ArrayList factors = new ArrayList();
            for (int j=1;j<i;++j){
                if (i%j==0){
                    factors.add(j);
                }
            }

            //get the sum of all factors
            int sum = 0;
            for (int k=0;k<factors.size();++k){
                sum = sum + (int)factors.get(k);
            }

            //test if the sum of all the factors is equal to the current number
            if (sum == i){
                System.out.println(i + " is a perfect number!");
            }
        }
    }
}
