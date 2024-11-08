package vending.machine;

import vending.machine.repository.*;
import vending.machine.repository.dataSource.ProductDataSource;
import vending.machine.repository.dataSource.ProductDataSourceImpl;
import vending.machine.serializers.ProductSerializer;
import vending.machine.service.ProductServiceImpl;
import vending.machine.ui.TerminalUI;

import java.nio.file.Path;

public final class App {

    public static void main(String[] args) {
        ProductSerializer productSerializer = new ProductSerializer();


        ProductDataSource productDataSource = new ProductDataSourceImpl(Path.of(""), productSerializer);


        ProductRepository productRepository = new ProductRepositoryImpl(productDataSource);
        AnalyticsRepository analyticsRepository = null;


        ProductServiceImpl productService = new ProductServiceImpl(productRepository, analyticsRepository);


        TerminalUI ui = new TerminalUI(productService);
    }
}
