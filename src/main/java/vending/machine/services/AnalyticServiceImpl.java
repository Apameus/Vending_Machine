package vending.machine.services;

import vending.machine.data.Sale;
import vending.machine.repositories.AnalyticRepository;

import java.util.List;
import java.util.stream.Collectors;

public final class AnalyticServiceImpl implements AnalyticService{
    private final AnalyticRepository analyticRepository;

    public AnalyticServiceImpl(AnalyticRepository analyticRepository) {
        this.analyticRepository = analyticRepository;
    }

    @Override
    public List<Sale> topThreeMostSellingProducts() {
        List<Sale> allSales = analyticRepository.getAllSales();
        return allSales.stream()
                .sorted((s1, s2) -> Integer.compare(s2.numberOfSales(), s1.numberOfSales())) // Sort in descending order
                .limit(3) // Limit to the top 3
                .collect(Collectors.toList()); // Collect as List
    }

    @Override
    public Float totalEarnings() {
        return analyticRepository.totalEarnings();
    }

    @Override
    public Float retrieveMoney(Integer userId) {
        Float availableEarnings = analyticRepository.retrieveAvailableEarnings();
        analyticRepository.trackMoneyMovement(userId, availableEarnings);
        analyticRepository.resetAvailableEarnings();
        return availableEarnings;
    }

}
