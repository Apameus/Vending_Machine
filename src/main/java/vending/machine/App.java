package vending.machine;

import vending.machine.repository.*;
import vending.machine.repository.dataSource.*;
import vending.machine.serializers.AnalyticSerializer;
import vending.machine.serializers.AuthorizationSerializer;
import vending.machine.serializers.ProductSerializer;
import vending.machine.service.*;
import vending.machine.ui.TerminalUI;

import java.nio.file.Path;

public final class App {

    public static void main(String[] args) {
        ProductSerializer productSerializer = new ProductSerializer();
        AnalyticSerializer analyticSerializer = new AnalyticSerializer();
        AuthorizationSerializer authorizationSerializer = new AuthorizationSerializer();

        ProductDataSource productDataSource = new ProductDataSourceImpl(Path.of("src/main/resources/Products"), productSerializer);
        AnalyticsDataSource analyticsDataSource = new AnalyticsDataSourceImpl(Path.of("src/main/resources/Analytics"), analyticSerializer);
        AuthorizationDataSource authorizationDataSource = new AuthorizationDataSourceImpl(Path.of("src/main/resources/AuthorizedUsers"), authorizationSerializer);

        ProductRepository productRepository = new ProductRepositoryImpl(productDataSource);
        AnalyticsRepository analyticsRepository = new AnalyticsRepositoryImpl(analyticsDataSource);
        AuthorizationRepository authorizationRepository = new AuthorizationRepositoryImpl(authorizationDataSource);

        ProductService productService = new ProductServiceImpl(productRepository, analyticsRepository);
        AuthorizationService authorizationService = new AuthorizationServiceImpl(authorizationRepository);
        AnalyticService analyticService = new AnalyticServiceImpl(analyticsRepository);

        TerminalUI ui = new TerminalUI(productService, analyticService, authorizationService);
        ui.start();
    }
}
