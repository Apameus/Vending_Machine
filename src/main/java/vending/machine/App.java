package vending.machine;

import vending.machine.repositories.*;
import vending.machine.serializers.AuthorizationSerializer;
import vending.machine.serializers.AuthorizationSerializerImpl;
import vending.machine.serializers.ProductSerializer;
import vending.machine.serializers.ProductSerializerImpl;
import vending.machine.services.*;
import vending.machine.ui.TerminalUI;

import java.io.IOException;

public final class App {

    public static void main(String[] args) throws IOException {
        ProductSerializer productSerializer = new ProductSerializerImpl();
        AuthorizationSerializer authorizationSerializer = new AuthorizationSerializerImpl();

        ProductRepository productRepository = new ProductRepositoryImpl(productSerializer.parseAll());
        AnalyticRepository analyticRepository = new AnalyticRepositoryImpl();
        AuthorizationRepository authorizationRepository = new AuthorizationRepositoryImpl(authorizationSerializer.parseAll());

        ProductService productService = new ProductServiceImpl(productRepository, analyticRepository);
        AnalyticService analyticService = new AnalyticServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl(authorizationRepository);

        TerminalUI terminalUI = new TerminalUI(System.console(), productService, analyticService, authorizationService);
        terminalUI.start();
    }
}
