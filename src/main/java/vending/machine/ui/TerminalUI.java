package vending.machine.ui;

import vending.machine.service.ProductService;
import vending.machine.data.Product;

import java.io.Console;
import java.util.Formatter;
import java.util.List;

public final class TerminalUI {

    private final Console console;
    private final ProductService productService;

    public TerminalUI(ProductService productService) {
        this.console = System.console();
        this.productService = productService;
    }

    public void start() {
        while (true) {
            printProducts(productService.listProducts());
            var action = console.readLine("EXIT or productId: ");
            if (action.equalsIgnoreCase("EXIT")) break;
            int productId;
            try {
                productId = Integer.parseUnsignedInt(action);
            } catch (NumberFormatException _) {
                console.printf("Invalid productId%n");
                continue;
            }
            retreiveProduct(productId);
        }
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

    void retreiveProduct(int productId) {

    }


}
