

/**
 * Created by danelennon on 9/09/2016.
 */
public class Range {
    private final double lower;
    private final double higher;

    //if there is no range, make upper and lower the same
    public Range(double range) {
        this.lower = range;
        this.higher = range;
    }

    public Range(double lower, double higher) {
        this.lower = lower;
        this.higher = higher;
    }

    double lower() {
        return lower;
    }

    double higher() {
        return higher;
    }

    boolean isHigherThan(Range other) {
        return this.higher() > other.higher();
    }

    @Override
    public String toString() {
        return lower + "-" + higher;
    }
}
