package vending.machine.repository.dataSource;

import vending.machine.data.Sale;

import java.util.Collection;

public interface AnalyticsDataSource {
    Collection<Sale> load();
    void save(Collection<Sale> sales);
}
