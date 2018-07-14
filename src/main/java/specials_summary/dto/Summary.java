package specials_summary.dto;

import java.util.Objects;

public class Summary {
    private BestCashDealSummary bestCashDealSummary;

    public Summary(BestCashDealSummary bestCashDealSummary) {
        this.bestCashDealSummary = bestCashDealSummary;
    }

    public BestCashDealSummary getBestCashDealSummary() {
        return bestCashDealSummary;
    }

    public void setBestCashDealSummary(BestCashDealSummary bestCashDealSummary) {
        this.bestCashDealSummary = bestCashDealSummary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summary summary = (Summary) o;
        return Objects.equals(bestCashDealSummary, summary.bestCashDealSummary);
    }

    @Override
    public int hashCode() {

        return Objects.hash(bestCashDealSummary);
    }

    @Override
    public String toString() {
        return "Summary{" +
                "bestCashDealSummary=" + bestCashDealSummary +
                '}';
    }
}
