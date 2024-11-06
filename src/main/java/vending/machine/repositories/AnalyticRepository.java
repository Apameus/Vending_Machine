package vending.machine.repositories;

import vending.machine.data.Sale;

import java.util.List;

public interface AnalyticRepository {
    void increaseEarningsBy(float price);

    void increaseSales(int productId);

    List<Sale> getAllSales();

    Float totalEarnings();

    Float retrieveAvailableEarnings();

    void trackMoneyMovement(Integer userId, Float availableEarnings);

    void refreshAvailableEarnings();
}
