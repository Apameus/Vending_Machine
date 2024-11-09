package vending.machine.repository.dataSource;

import vending.machine.data.AnalyticData;

public interface AnalyticsDataSource {

    AnalyticData load();

    void save(AnalyticData data);

}
