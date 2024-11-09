package vending.machine.repository;

import vending.machine.data.AnalyticData;
import vending.machine.data.Earnings;
import vending.machine.data.Sale;
import vending.machine.repository.dataSource.AnalyticsDataSource;
import java.util.HashMap;
import java.util.List;

public final class AnalyticsRepositoryImpl implements AnalyticsRepository {

                // Product.id, Sale
    private HashMap<Integer, Sale> salesCache;
    private Earnings earnings;

    //TODO: UserMovement (UserId, ReceivedMoney)

    AnalyticsDataSource dataSource;

    public AnalyticsRepositoryImpl(AnalyticsDataSource dataSource) {
        this.dataSource = dataSource;
        salesCache = new HashMap<>();
        earnings = dataSource.load().earnings();
        dataSource.load().sales().forEach(sale -> salesCache.put(sale.productId(), sale));
    }

    @Override
    public void increaseSales(int productId) {
        salesCache.computeIfPresent(productId, (k, sale) -> sale.withNumberOfSales(sale.numberOfSales() + 1));
        dataSource.save(new AnalyticData(earnings, List.copyOf(salesCache.values())));
    }

    @Override
    public void increaseEarnings(int price) {
        earnings = earnings.increasedBy(price);
        dataSource.save(new AnalyticData(earnings, List.copyOf(salesCache.values())));
    }

    @Override
    public List<Sale> getAllSales() {
        return salesCache.values().stream().toList();
    }

    Sale getSale(int productId){
        return salesCache.get(productId);
    }
}
