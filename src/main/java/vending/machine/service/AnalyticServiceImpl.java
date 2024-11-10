package vending.machine.service;

import vending.machine.data.Sale;
import vending.machine.repository.AnalyticsRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class AnalyticServiceImpl implements AnalyticService {
    private final AnalyticsRepository repository;

    public AnalyticServiceImpl(AnalyticsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Sale> topThreeMostSellingProducts() {
        return repository.getAllSales()
                .stream()
                .sorted(Comparator.comparingInt(Sale::numberOfSales).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public int totalEarnings() {
        return repository.getEarnings().totalEarnings();
    }

    @Override
    public int retrieveAvailableEarnings() {
        int availableEarnings = repository.getEarnings().availableEarnings();
        repository.refreshAvailableEarnings();
        return availableEarnings;
    }
}
