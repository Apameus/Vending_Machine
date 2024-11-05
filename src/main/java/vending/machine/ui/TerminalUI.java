package vending.machine.ui;
import vending.machine.data.ProductWithChange;
import vending.machine.exeptions.AuthorizationFailedException;
import vending.machine.exeptions.NotEnoughMoneyException;
import vending.machine.exeptions.ProductNotFoundException;
import vending.machine.exeptions.ZeroStockException;
import vending.machine.services.AnalyticService;
import vending.machine.services.AuthorizationService;
import vending.machine.services.ProductService;
import java.io.Console;

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
            showProducts();
            String input = console.readLine("EXIT or Product Id: ");
            if (input.equals("EXIT")) break;
            if (authorizationCheck(input)) break;
            buyProduct(input);
        }

    }

    private boolean authorizationCheck(String input) {
        if (input.length() == 8) {
            int password = Integer.parseInt(console.readLine("Enter Password: "));
            try {
                authorizationService.authorizeUser(Integer.parseInt(input), password);
            } catch (AuthorizationFailedException e) {
                console.printf("Authorization Failed!");
            }
            return true;
        }
        return false;
    }

    private void buyProduct(String input) {
        int productId = Integer.parseInt(input);
        try {
            productService.verifyProductAvailability(productId);
        } catch (ProductNotFoundException e) {
            console.printf("Product id doesn't exist!");
            return;
        } catch (ZeroStockException e) {
            console.printf("Product out of stock!");
            return;
        }
        float money = Float.parseFloat(console.readLine("Enter money: "));
        try {
            ProductWithChange productWithChange = productService.retrieveProduct(productId, money);
            console.printf("Your Product: " + productWithChange.product().name() + "%n" +"Your change: " + productWithChange.change());
        } catch (NotEnoughMoneyException e) {
            console.printf("Not enough money!");
        }
    }

    private void showProducts() {
        console.printf("%n" + productService.listProducts().toString());
        console.printf("%n");
    }


}
