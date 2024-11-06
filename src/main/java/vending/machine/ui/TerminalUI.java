package vending.machine.ui;
import vending.machine.data.Command;
import vending.machine.data.Product;
import vending.machine.data.ProductWithChange;
import vending.machine.exeptions.*;
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

    private boolean authorizationCheck(String userId) {
        if (userId.length() == 8) {
            int password = Integer.parseInt(console.readLine("Enter Password: "));
            try {
                authorizationService.authorizeUser(Integer.parseInt(userId), password);
                console.printf("%n WELCOME %n");
                Integer cmdId = Integer.parseInt(console.readLine("Give command ID: "));
                commandAction(cmdId, Integer.valueOf(userId));
            } catch (AuthorizationFailedException e) {
                console.printf("Authorization Failed!");
            }
            return true;
        }
        return false;
    }

    private void commandAction(Integer cmdId, Integer userId) {
        //TODO: CHECK if input cmdID is valid
        while (cmdId!=00){
            try {
                Command command = Command.getById(cmdId);
                switch (command){
                    int productId = Integer.parseInt(console.readLine("Give product id: "));
                    case UPDATE_QUANTITY -> {
                        Integer quantity = Integer.valueOf(console.readLine("Quantity: "));
                        productService.updateQuantity(productId, quantity);
                    }
                    case UPDATE_PRICE -> {
                        Float price = Float.valueOf(console.readLine("Price: "));
                        productService.updatePrice(productId, price);
                    }
                    case UPDATE_ID -> {
                        Integer newId = Integer.valueOf(console.readLine("ID: "));
                        productService.updateId(productId, newId);
                    }
                    case UPDATE_NAME -> {
                        String name = console.readLine("Name: ");
                        productService.updateName(productId, name);
                    }
                    case ADD_PRODUCT -> {
                        Integer newId = Integer.valueOf(console.readLine("ID: "));
                        String name = console.readLine("Name: ");
                        Float price = Float.valueOf(console.readLine("Price: "));
                        Integer quantity = Integer.valueOf(console.readLine("Quantity: "));
                        Product product = new Product(newId, name, price, quantity);
                        productService.addProduct(product);
                    }
                    case REMOVE_PRODUCT -> productService.removeProduct(productId);
                    case TOP_THREE_MOST_SELLING_PRODUCTS -> productService.topThreeMostSellingProducts();
                    case TOTAL_EARNINGS -> productService.totalEarnings();
                    case RETRIEVE_MONEY -> productService.retrieveMoney(userId);
                }
                cmdId = Integer.parseInt(console.readLine("Give command ID: "));
            } catch (CommandDoesntExistException e) {
                console.printf("Command ID doesn't exist");
            }
        }
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
