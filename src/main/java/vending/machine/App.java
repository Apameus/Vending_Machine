package vending.machine;

import vending.machine.repository.*;
import vending.machine.serializers.ProductSerializer;
import vending.machine.service.ProductServiceImpl;
import vending.machine.ui.TerminalUI;

import java.nio.file.Path;
import java.util.List;

public final class App {

    public static void main(String[] args) {
        DataSource dataSource = null;

        ProductSerializer productSerializer = new ProductSerializer();

        ProductRepository productRepository = new ProductRepositoryImpl(dataSource);
        AnalyticsRepository analyticsRepository = null;

        ProductServiceImpl productService = new ProductServiceImpl(productRepository, analyticsRepository);

        TerminalUI ui = new TerminalUI(productService);
    }
}
