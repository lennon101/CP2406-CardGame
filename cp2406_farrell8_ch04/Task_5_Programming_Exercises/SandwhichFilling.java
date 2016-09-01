/**
 * Created by danelennon on 15/08/2016.
 */
public class SandwhichFilling {
    private String fillingType;
    private int calPerServing;

    public SandwhichFilling(String fillingType, int calPerServing) {
        this.fillingType = fillingType;
        this.calPerServing = calPerServing;
    }

    public String getFillingType() {
        return fillingType;
    }

    public int getCalPerServing() {
        return calPerServing;
    }

    @Override
    public String toString() {
        return "SandwhichFilling{" +
                "fillingType='" + fillingType + '\'' +
                ", calPerServing=" + calPerServing +
                '}';
    }
}
