package vending.machine.repository;

import vending.machine.data.Sale;

import java.util.List;

public interface AnalyticsRepository {

    void increaseSales(int productId);

    void increaseEarnings(int price);

    List<Sale> getAllSales();
}
