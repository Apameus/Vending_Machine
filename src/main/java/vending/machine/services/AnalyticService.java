package vending.machine.services;

import vending.machine.data.Sale;

import java.util.List;

public interface AnalyticService {
    List<Sale> topThreeMostSellingProducts();

    Float totalEarnings();

    Float retrieveMoney(Integer userId);
}
