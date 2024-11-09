package vending.machine.data;

import java.util.Collection;

public record AnalyticData(Earnings earnings, Collection<Sale> sales) {
}
