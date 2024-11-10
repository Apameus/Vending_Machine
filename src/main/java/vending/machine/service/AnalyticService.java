package vending.machine.service;

import vending.machine.data.Earnings;
import vending.machine.data.Sale;

import java.util.List;

public interface AnalyticService {

    List<Sale> topThreeMostSellingProducts();
    int totalEarnings();
    int retrieveAvailableEarnings();
}
