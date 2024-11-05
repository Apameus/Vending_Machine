package vending.machine.ui;

import vending.machine.data.Product;
import vending.machine.exeptions.NotEnoughMoneyException;
import vending.machine.exeptions.ProductNotFoundException;
import vending.machine.exeptions.ZeroStockException;
import vending.machine.services.AnalyticService;
import vending.machine.services.AuthorizationService;
import vending.machine.services.ProductService;

import java.io.Console;
import java.util.Collection;

public final class TerminalUI {
    private Console console;
    private ProductService productService;
    private AnalyticService analyticService;
    private AuthorizationService authorizationService;

    public TerminalUI(Console console, ProductService productService, AnalyticService analyticService, AuthorizationService authorizationService) {
        this.productService = productService;
        this.analyticService = analyticService;
        this.authorizationService = authorizationService;
        this.console = console;
    }

    public void start(){
        while (true){
            console.printf(productService.listProducts().toString());
            String input = console.readLine("EXIT or Product Id: ");
            if (input.equals("EXIT")) break;
            int productId = Integer.parseInt(input);
            try {
                productService.verifyProductAvailability(productId);
            } catch (ProductNotFoundException e) {
                console.printf("Product id doesn't exist!");
            } catch (ZeroStockException e) {
                console.printf("Product out of stock!");
            }
            float money = Float.parseFloat(console.readLine("Enter money: "));
            try {
                productService.retrieveProduct(productId, money);
            } catch (NotEnoughMoneyException e) {
                console.printf("Not enough money!");
            }
        }

    }


}
