

/**
 * Created by danelennon on 9/09/2016.
 */
public class CardComparisonResult {
    boolean hardnessTrumped;
    boolean gravityTrumped;
    boolean cleavageTrumped;
    boolean economicWasTrumped;
    boolean crustalWasTrumped;

    public CardComparisonResult(boolean hardnessTrumped, boolean gravityTrumped, boolean cleavageTrumped, boolean economicWasTrumped, boolean crustalWasTrumped) {
        this.hardnessTrumped = hardnessTrumped;
        this.gravityTrumped = gravityTrumped;
        this.cleavageTrumped = cleavageTrumped;
        this.economicWasTrumped = economicWasTrumped;
        this.crustalWasTrumped = crustalWasTrumped;
    }

    boolean hardnessWasTrumped() {
        return hardnessTrumped;
    }

    boolean gravityWasTrumped() {
        return gravityTrumped;
    }

    boolean cleavageWasTrumped() {
        return cleavageTrumped;
    }

    boolean economicValueWasTrumped() {
        return economicWasTrumped;
    }

    boolean crustalAbundanceWasTrumped() {
        return crustalWasTrumped;
    }
}
