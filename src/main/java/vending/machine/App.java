package vending.machine;

import vending.machine.repository.*;
import vending.machine.repository.dataSource.AnalyticsDataSource;
import vending.machine.repository.dataSource.AnalyticsDataSourceImpl;
import vending.machine.repository.dataSource.ProductDataSource;
import vending.machine.repository.dataSource.ProductDataSourceImpl;
import vending.machine.serializers.AnalyticSerializer;
import vending.machine.serializers.ProductSerializer;
import vending.machine.service.ProductServiceImpl;
import vending.machine.ui.TerminalUI;

import java.nio.file.Path;

public final class App {

    public static void main(String[] args) {
        ProductSerializer productSerializer = new ProductSerializer();
        AnalyticSerializer analyticSerializer = new AnalyticSerializer();


        ProductDataSource productDataSource = new ProductDataSourceImpl(Path.of("src/main/java/vending/machine/resources/Products"), productSerializer);
        AnalyticsDataSource analyticsDataSource = new AnalyticsDataSourceImpl(Path.of("src/main/java/vending/machine/resources/Sales"), analyticSerializer);

        ProductRepository productRepository = new ProductRepositoryImpl(productDataSource);
        AnalyticsRepository analyticsRepository = new AnalyticsRepositoryImpl(analyticsDataSource);


        ProductServiceImpl productService = new ProductServiceImpl(productRepository, analyticsRepository);


        TerminalUI ui = new TerminalUI(productService);
        ui.start();
    }
}
