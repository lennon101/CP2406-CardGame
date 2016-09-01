import java.util.Scanner;

/**
 * Created by danelennon on 22/08/2016.
 */
public class CountByAnything {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number to count by: ");
        int num = input.nextInt();
        System.out.print("Enter the number to finish at: ");
        int endNum = input.nextInt();

        int count = 0;
        for (int i = num; i <=endNum; i=i+3){
            ++count;
            System.out.print(i + "\t");
            if (count % 10== 0) System.out.println();
        }

    }
}
