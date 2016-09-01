/**
 * Created by danelennon on 15/08/2016.
 */
public class Sandwhich {
    private Bread bread;
    private SandwhichFilling sandwhichFilling;
    private int totalCal;

    public Sandwhich(String breadType, int calPerSlice, String fillingType, int calPerServing) {
        this.bread = new Bread(breadType,calPerSlice);
        this.sandwhichFilling = new SandwhichFilling(fillingType,calPerServing);
    }

    public int getTotalCal() {
        totalCal = 2*this.bread.getCalPerSlice() + this.sandwhichFilling.getCalPerServing();
        return totalCal;
    }

    @Override
    public String toString() {
        return "Sandwhich{" +
                "bread=" + bread +
                ", sandwhichFilling=" + sandwhichFilling +
                '}';
    }
}
