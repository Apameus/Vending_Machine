package vending.machine.repositories;

public interface AnalyticRepository {
    void increaseTotalEarningsBy(float price);

    void increaseSales(int productId);
}
