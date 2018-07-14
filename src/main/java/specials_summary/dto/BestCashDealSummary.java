package specials_summary.dto;

import java.util.List;
import java.util.Objects;

public class BestCashDealSummary {
    private double value;
    private List<Long> incentiveIds;
    private long vehicleId;

    public BestCashDealSummary(double value, List<Long> incentiveIds, long vehicleId) {
        this.value = value;
        this.incentiveIds = incentiveIds;
        this.vehicleId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestCashDealSummary that = (BestCashDealSummary) o;
        return Double.compare(that.value, value) == 0 &&
                vehicleId == that.vehicleId &&
                Objects.equals(incentiveIds, that.incentiveIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, incentiveIds, vehicleId);
    }

    @Override
    public String toString() {
        return "BestCashDealSummary{" +
                "value=" + value +
                ", incentiveIds=" + incentiveIds +
                ", vehicleId=" + vehicleId +
                '}';
    }
}
