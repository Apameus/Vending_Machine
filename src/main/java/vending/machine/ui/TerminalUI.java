package vending.machine.ui;

import vending.machine.exception.NotEnoughMoneyException;
import vending.machine.exception.ProductNotFoundException;
import vending.machine.exception.ZeroProductStockException;
import vending.machine.service.AuthorizationService;
import vending.machine.service.ProductService;
import vending.machine.data.Product;

import java.io.Console;
import java.util.Formatter;
import java.util.List;

public final class TerminalUI {

    private final Console console;
    private final ProductService productService;
    private final AuthorizationService authorizationService;

    public TerminalUI(ProductService productService, AuthorizationService authorizationService) {
        this.console = System.console();
        this.productService = productService;
        this.authorizationService = authorizationService;
    }

    public void start() {
        while (true) {
            printProducts(productService.listProducts());
            var action = console.readLine("EXIT or productId: ");
            if (action.equalsIgnoreCase("EXIT")) break;
            if (action.length() == 8) authorizeUser(action);

            int productId;
            try {
                productId = Integer.parseUnsignedInt(action);
            } catch (NumberFormatException _) {
                console.printf("Invalid productId%n");
                continue;
            }
            retrieveProduct(productId);
        }
    }

    private void authorizeUser(String input) {
        int id = Integer.parseInt(input);
        int password = Integer.parseInt(console.readLine("Password: "));
        authorizationService.authorizeUser(id, password);
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

    private void printProducts(List<Product> products) {
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
