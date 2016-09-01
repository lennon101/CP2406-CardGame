/**
 * Created by danelennon on 15/08/2016.
 */
public class Bread {
    private String breadType;
    private int calPerSlice;
    public static final String MOTTO = "The staff of life";

    public Bread(String breadType, int calPerSlice){
        this.breadType = breadType;
        this.calPerSlice = calPerSlice;
    }


    public int getCalPerSlice() {
        return calPerSlice;
    }

    public String getBreadType() {
        return breadType;
    }

    @Override
    public String toString() {
        return "Bread{" +
                "breadType='" + breadType + '\'' +
                ", calPerSlice=" + calPerSlice +
                '}';
    }

}
