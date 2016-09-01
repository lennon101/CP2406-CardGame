// DebugSix3.java
// Prompt user for value to start
// Value must be between 1 and 20 inclusive
// At command line, count down to blastoff
// With a brief pause between each displayed value
import com.sun.xml.internal.ws.api.model.ExceptionType;

import javax.swing.*;
import java.util.concurrent.TimeUnit;
public class DebugSix3
{
    public static void main(String[] args) {
        String userNumString;
        int userNum, val;
        final int MIN = 1;
        final int MAX = 20;
        userNumString = JOptionPane.showInputDialog(null,
                "Enter a number between "  + MIN + " and " + MAX +  " inclusive");
        userNum = Integer.parseInt(userNumString);
        while(userNum < MIN || userNum > MAX) {
            userNumString = JOptionPane.showInputDialog(null,
                    "Number out of range" +
                            "\nEnter a number between " + MIN + " and " + MAX + " inclusive");
            userNum = Integer.parseInt(userNumString);
        }

        System.out.println("your chose: " + userNum);

        for (val = userNum; val > 0; --val) {
            System.out.print(val + "  ");
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e){
                System.out.println(e);
            }
        }
        System.out.println("Blastoff!");
    }
}






