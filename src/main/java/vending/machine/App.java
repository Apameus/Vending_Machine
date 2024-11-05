package vending.machine;

import vending.machine.repositories.*;
import vending.machine.serializers.*;
import vending.machine.services.*;
import vending.machine.ui.TerminalUI;

import java.io.IOException;
import java.nio.file.Path;

public final class App {

    public static void main(String[] args) throws IOException {
        ProductSerializer productSerializer = new ProductSerializerImpl();
        AnalyticSerializer analyticSerializer = new AnalyticSerializerImpl();
        AuthorizationSerializer authorizationSerializer = new AuthorizationSerializerImpl();

        ProductRepository productRepository = new ProductRepositoryImpl(Path.of("src/main/resources/Products.txt"), productSerializer);
        AnalyticRepository analyticRepository = new AnalyticRepositoryImpl(Path.of("src/main/resources/Sales"), Path.of("src/main/resources/TotalEarnings"),analyticSerializer);
        AuthorizationRepository authorizationRepository = new AuthorizationRepositoryImpl(Path.of("src/main/resources/AuthorizedUsers"), authorizationSerializer);

        ProductService productService = new ProductServiceImpl(productRepository, analyticRepository);
        AnalyticService analyticService = new AnalyticServiceImpl(); //EMPTY
        AuthorizationService authorizationService = new AuthorizationServiceImpl(authorizationRepository);

        TerminalUI terminalUI = new TerminalUI(System.console(), productService, analyticService, authorizationService);
        terminalUI.start();
    }
}
