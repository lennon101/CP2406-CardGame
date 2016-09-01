// DebugFive4.java
// Outputs highest of four numbers
import javax.swing.*;
public class DebugFive4
{
    public static void main (String args[])
    {

        int one, two, three, four;
        String str, output;
        one = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter an integer"));
        two = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter an integer"));
        three = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter an integer"));
        four = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter an integer"));

        if(one > two && one > three && one > four) {
            output = "Highest is " + one + " stored in var one";
        }else if(two > one && two > three && two > four) {
            output = "Highest is " + two + " stored in var two";
        }else if(three > one && three > two && three > four) {
            output = "Highest is " + three + " stored in var three";
        }else {
            output = "Highest is " + four + " stored in var four";
        }
        JOptionPane.showMessageDialog(null, output);
    }
}


