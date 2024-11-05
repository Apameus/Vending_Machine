package vending.machine;

import vending.machine.repositories.AnalyticRepository;
import vending.machine.repositories.AnalyticRepositoryImpl;
import vending.machine.repositories.ProductRepository;
import vending.machine.repositories.ProductRepositoryImpl;
import vending.machine.serializers.ProductSerializer;
import vending.machine.serializers.ProductSerializerImpl;
import vending.machine.services.*;
import vending.machine.ui.TerminalUI;

import java.io.IOException;

public final class App {

    public static void main(String[] args) throws IOException {
        ProductSerializer productSerializer = new ProductSerializerImpl();

        ProductRepository productRepository = new ProductRepositoryImpl(productSerializer.parseAll());
        AnalyticRepository analyticRepository = new AnalyticRepositoryImpl();

        ProductService productService = new ProductServiceImpl(productRepository, analyticRepository);
        AnalyticService analyticService = new AnalyticServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        TerminalUI terminalUI = new TerminalUI(System.console(), productService, analyticService, authorizationService);
        terminalUI.start();
    }
}
