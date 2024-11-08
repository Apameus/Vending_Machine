package vending.machine.repository.dataSource;

import vending.machine.data.Sale;
import vending.machine.serializers.AnalyticSerializer;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public final class AnalyticsDataSourceImpl implements AnalyticsDataSource {
    private final Path path;

    private final AnalyticSerializer serializer;

    public AnalyticsDataSourceImpl(Path path, AnalyticSerializer serializer) {
        this.path = path;
        this.serializer = serializer;
    }

    @Override
    public Collection<Sale> load() {
        return List.of();
    }

    @Override
    public void save(Collection<Sale> sales) {

    }
}
