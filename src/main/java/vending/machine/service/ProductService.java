package vending.machine.service;

import vending.machine.data.Product;
import vending.machine.exception.NotEnoughMoneyException;
import vending.machine.exception.ProductNotFoundException;
import vending.machine.exception.ZeroProductStockException;

import java.util.List;

public interface ProductService {
    List<Product> listProducts();

    Product findAvailableProduct(int productId) throws ProductNotFoundException, ZeroProductStockException;

    int retrieveProduct(Product product, int money) throws NotEnoughMoneyException;
}
