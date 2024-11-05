package vending.machine;

import vending.machine.repositories.*;
import vending.machine.serializers.*;
import vending.machine.services.*;
import vending.machine.ui.TerminalUI;

import java.io.IOException;

public final class App {

    public static void main(String[] args) throws IOException {
        ProductSerializer productSerializer = new ProductSerializerImpl();
        AnalyticSerializer analyticSerializer = new AnalyticSerializerImpl();
        AuthorizationSerializer authorizationSerializer = new AuthorizationSerializerImpl();

        ProductRepository productRepository = new ProductRepositoryImpl(productSerializer.parseAll());
        AnalyticRepository analyticRepository = new AnalyticRepositoryImpl(analyticSerializer.parseAllSales(), analyticSerializer.parseAllUserMovement());
        AuthorizationRepository authorizationRepository = new AuthorizationRepositoryImpl(authorizationSerializer.parseAll());

        ProductService productService = new ProductServiceImpl(productRepository, analyticRepository);
        AnalyticService analyticService = new AnalyticServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl(authorizationRepository);

        TerminalUI terminalUI = new TerminalUI(System.console(), productService, analyticService, authorizationService);
        terminalUI.start();
    }
}
