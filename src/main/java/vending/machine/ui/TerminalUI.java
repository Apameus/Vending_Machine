package vending.machine.ui;

import vending.machine.data.Command;
import vending.machine.exception.*;
import vending.machine.service.AnalyticService;
import vending.machine.service.AuthorizationService;
import vending.machine.service.ProductService;
import vending.machine.data.Product;

import java.io.Console;
import java.util.Formatter;
import java.util.List;

public final class TerminalUI {

    private final Console console;
    private final ProductService productService;
    private final AnalyticService analyticService;
    private final AuthorizationService authorizationService;

    public TerminalUI(ProductService productService, AnalyticService analyticService, AuthorizationService authorizationService) {
        this.console = System.console();
        this.productService = productService;
        this.analyticService = analyticService;
        this.authorizationService = authorizationService;
    }

    public void start() {
        while (true) {
            printProducts();
            var action = console.readLine("EXIT or productId: ");
            if (action.equalsIgnoreCase("EXIT")) break;
            if (falseAuthorizationCheck(action)) break;

            buyProduct(action);
        }
    }

    //TODO: Refactor

    private boolean falseAuthorizationCheck(String input) {
        if (input.length() != 8) return false;
        int id = Integer.parseInt(input);
        int password = Integer.parseInt(console.readLine("Password: "));
        try {
            authorizationService.authorizeUser(id, password);
            console.printf("%n WELCOME %n");
            showCommands();
            Integer cmdId = Integer.parseInt(console.readLine("Give command ID: "));
            commandAction(cmdId, id);
        } catch (AuthorizationFailedException e) {
            console.printf("Authorization Failed!");
        }
        return true;
    }

    private void commandAction(Integer cmdId, int userID) {
        while (cmdId != 00){
            try {
                Command command = Command.getCommandById(cmdId);
                switch (command) {
                    case TOP_THREE_MOST_SELLING_PRODUCTS -> console.printf(analyticService.topThreeMostSellingProducts().toString());
                    case TOTAL_EARNINGS -> console.printf(String.valueOf(analyticService.totalEarnings()));
                    case RETRIEVE_AVAILABLE_EARNINGS -> console.printf(String.valueOf(analyticService.retrieveAvailableEarnings()));
                }
                switch (command){
//                    int productID = Integer.parseInt(console.readLine("Product ID: "));
                    case UPDATE_QUANTITY ->{
                        int productID = Integer.parseInt(console.readLine("Product ID: "));
                        int quantity = Integer.parseInt(console.readLine("Quantity: "));
                        productService.addStock(productID, quantity);
                    }
                    case UPDATE_PRICE -> {
                        int productID = Integer.parseInt(console.readLine("Product ID: "));
                        int price = Integer.parseInt(console.readLine("Price: "));
                        productService.updatePrice(productID, price);
                    }
                    case UPDATE_ID -> {
                        int productID = Integer.parseInt(console.readLine("Product ID: "));
                        int newID = Integer.parseInt(console.readLine("ID: "));
                        productService.updateId(productID, newID);
                    }
                    case UPDATE_NAME -> {
                        int productID = Integer.parseInt(console.readLine("Product ID: "));
                        String name = console.readLine("Name");
                        productService.updateName(productID, name);
                    }
                    case ADD_PRODUCT -> {
                        int productID = Integer.parseInt(console.readLine("Product ID: "));
                        String name = console.readLine("Name: ");
                        int price = Integer.parseInt(console.readLine("Price: "));
                        int quantity = Integer.parseInt(console.readLine("Quantity: "));
                        Product product = new Product(productID, name, price, quantity);
                        productService.addProduct(product);
                    }
                    case REMOVE_PRODUCT -> {
                        int productID = Integer.parseInt(console.readLine("Product ID: "));
                        productService.removeProduct(productID);
                    }
                }
            } catch (CommandDoeNotExistException e) {
                console.printf("Invalid command");
            } catch (MachineOverloadException e) {
                console.printf("Item can't be more than 10");
            } catch (ProductNotFoundException e) {
                console.printf("Product not found");
            }
            cmdId = Integer.parseInt(console.readLine("Give command ID: "));
        }
    }

    private void showCommands() {
        for (Command command : Command.values()){
            console.printf("%n" + command.id() + " ," + command.name());
        } console.printf("%n");
    }

    private void buyProduct(String action) {
        int productId;
        try {
            productId = Integer.parseUnsignedInt(action);
        } catch (NumberFormatException _) {
            console.printf("Invalid productId%n");
            return;
        }
        retrieveProduct(productId);
    }

    void retrieveProduct(int productId) {
        try {
            Product availableProduct = productService.findAvailableProduct(productId);
            int money = Integer.parseInt(console.readLine("Money: "));
            int change = productService.retrieveProduct(availableProduct, money);
            console.printf(availableProduct.name() + ", Change: " + change + "%n");
        } catch (ProductNotFoundException e) {
            console.printf("Product not found!");
        } catch (ZeroProductStockException e) {
            console.printf("Product out of stock!");
        } catch (NotEnoughMoneyException e) {
            console.printf("Not enough money!");
        }catch (NumberFormatException _){
            console.printf("Wrong input!");
        }
        //check_input
    }

    private void printProducts() {
        List<Product> products = productService.listProducts();
        var builder = new StringBuilder();
        var formatter = new Formatter(builder);

        int idWidth = 2, nameWidth = 10, priceWidth = 6, quantityWidth = 2;

        var rowFormat = "%-" + idWidth + "s %-" + nameWidth + "s %-" + priceWidth + "s %-" + quantityWidth + "s%n";
        formatter.format(rowFormat, "ID", "NAME", "PRICE", "QUANTITY");
        formatter.format(rowFormat, "--", "-".repeat(nameWidth), "-".repeat(priceWidth), "-".repeat(quantityWidth));

        products.forEach(product -> formatter.format(rowFormat, product.id(), product.name(), product.price(), product.quantity()));

        System.out.println(builder);
        formatter.close();
    }


}
