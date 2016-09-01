/**
 * Created by danelennon on 15/08/2016.
 */
public class FormLetterWriter {
    public static void main(String[] args) {
        displaySalutation("Dane Lennon");
        displaySalutation("Dane","Lennon");
    }

    public static void displaySalutation(String name){
        System.out.println("Dear Mr. or Ms. " + name);
        System.out.println("Thank you for your recent order");
    }

    public static void displaySalutation(String firstName, String lastName){
        System.out.println(String.format("Dear %s %s ", firstName, lastName));
        System.out.println("Thank you for your recent order");
    }
}
