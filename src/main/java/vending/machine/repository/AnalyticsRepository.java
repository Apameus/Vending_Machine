package vending.machine.repository;

public interface AnalyticsRepository {

    void increaseSales(int productId);

    void increaseEarnings(int price);
}
