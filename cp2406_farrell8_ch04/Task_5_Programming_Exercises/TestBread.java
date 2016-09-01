/**
 * Created by danelennon on 15/08/2016.
 */
public class TestBread {
    public static void main(String[] args) {
        Bread loaf1 = new Bread("White",100);
        Bread loaf2 = new Bread("Rye", 120);
        Bread loaf3 = new Bread("Italian hervbs and cheese",150);

        System.out.println("loaf1");
        System.out.println("    breadType: " + loaf1.getBreadType());
        System.out.println("    cal per slice: " + loaf1.getCalPerSlice());
        System.out.println(loaf1.MOTTO);

        System.out.println("loaf2");
        System.out.println("    breadType: " + loaf2.getBreadType());
        System.out.println("    cal per slice: " + loaf2.getCalPerSlice());
        System.out.println(loaf2.MOTTO);

        System.out.println("loaf3");
        System.out.println("    breadType: " + loaf3.getBreadType());
        System.out.println("    cal per slice: " + loaf3.getCalPerSlice());
        System.out.println(loaf3.MOTTO);


        System.out.println("test ToString method for loaf1");
        System.out.println(loaf1);

    }
}
