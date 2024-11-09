package vending.machine.repository.dataSource;

import vending.machine.data.AnalyticData;
import vending.machine.data.Earnings;
import vending.machine.data.Sale;
import vending.machine.serializers.AnalyticSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class AnalyticsDataSourceImpl implements AnalyticsDataSource {

    private final Path path;
    private final AnalyticSerializer serializer;

    public AnalyticsDataSourceImpl(Path salesPath, AnalyticSerializer serializer) {
        this.path = salesPath;
        this.serializer = serializer;
    }

    @Override
    public AnalyticData load() {
        List<String> lines = null;
        try {lines = Files.readAllLines(path);}
        catch (IOException e) {throw new RuntimeException(e);}

        Earnings earnings = serializer.parseEarnings(lines.getFirst());
        List<Sale> sales = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            sales.add(serializer.parseSale(lines.get(i)));
        }
        return new AnalyticData(earnings, sales);
    }

    @Override
    public void save(AnalyticData data) {
        List<String> lines = new ArrayList<>();
        lines.add(serializer.serializeEarning(data.earnings()));
        data.sales().forEach(sale -> lines.add(serializer.serializeSale(sale)));
        try {
            Files.write(path,lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
