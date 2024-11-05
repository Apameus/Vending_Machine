package vending.machine;

import vending.machine.repositories.AnalyticRepository;
import vending.machine.repositories.AnalyticRepositoryImpl;
import vending.machine.repositories.ProductRepository;
import vending.machine.repositories.ProductRepositoryImpl;
import vending.machine.services.*;
import vending.machine.ui.TerminalUI;

public final class App {

    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepositoryImpl();
        AnalyticRepository analyticRepository = new AnalyticRepositoryImpl();

        ProductService productService = new ProductServiceImpl(productRepository, analyticRepository);
        AnalyticService analyticService = new AnalyticServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        TerminalUI terminalUI = new TerminalUI(System.console(), productService, analyticService, authorizationService);
        terminalUI.start();
    }
}
