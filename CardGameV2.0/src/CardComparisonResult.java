

/**
 * Created by danelennon on 9/09/2016.
 */
public class CardComparisonResult {
    private boolean hardnessTrumped;
    private boolean gravityTrumped;
    private boolean cleavageTrumped;
    private boolean economicWasTrumped;
    private boolean crustalWasTrumped;

    public CardComparisonResult(boolean hardnessTrumped, boolean gravityTrumped, boolean cleavageTrumped, boolean economicWasTrumped, boolean crustalWasTrumped) {
        this.hardnessTrumped = hardnessTrumped;
        this.gravityTrumped = gravityTrumped;
        this.cleavageTrumped = cleavageTrumped;
        this.economicWasTrumped = economicWasTrumped;
        this.crustalWasTrumped = crustalWasTrumped;
    }

    public boolean hardnessWasTrumped() {
        return hardnessTrumped;
    }

    public boolean gravityWasTrumped() {
        return gravityTrumped;
    }

    public boolean cleavageWasTrumped() {
        return cleavageTrumped;
    }

    public boolean economicValueWasTrumped() {
        return economicWasTrumped;
    }

    public boolean crustalAbundanceWasTrumped() {
        return crustalWasTrumped;
    }
}
