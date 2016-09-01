/**
 * Created by danelennon on 15/08/2016.
 */
public class TestSandwhich {
    public static void main(String[] args) {
        Sandwhich chickenSanger = new Sandwhich("white",130,"mayo",150);
        Sandwhich steakSanger = new Sandwhich("brown",50,"tom",90);
        Sandwhich snittySanger = new Sandwhich("Rye",30,"pot salad",100);

        System.out.println("chicken: " + chickenSanger);
        System.out.println("total for chicken is: " + chickenSanger.getTotalCal());

        System.out.println("steak: " + steakSanger);
        System.out.println("total for steak is: " + steakSanger.getTotalCal());

        System.out.println("snitty: " + snittySanger);
        System.out.println("total for snitty is: " + snittySanger.getTotalCal());

    }
}
