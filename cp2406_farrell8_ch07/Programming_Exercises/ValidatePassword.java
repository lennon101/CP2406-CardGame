import java.util.Scanner;

/**
 * Created by danelennon on 29/08/2016.
 */
public class ValidatePassword {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a password that contains at least: \n2 Uppercase\n2 Lowercase\n2 digits.\n>>");

        String password = input.next();

        while (!isValid(password)){
            System.out.println("Password is invalid, try again");
            System.out.print("Enter a password that contains at least: \n2 Uppercase\n2 Lowercase\n2 digits.\n>>");

            password = input.next();
        }
    }

    public static boolean isValid(String password){
        int numUpper = 0;
        int numLower = 0;
        int numDigits = 0;

        int ascci;
        for (int i = 0;i<password.length();++i){
            ascci = (int) password.charAt(i);
            if (ascci > 64 && ascci<91){
                ++numUpper;
            }else if (ascci>96 && ascci<123){
                ++numLower;
            }else if (ascci>47 && ascci<58){
                ++numDigits;
            }
        }

        if (numUpper >= 2 && numLower >= 2 && numDigits >= 2){
            return true;
        }else{
            return false;
        }
    }
}
