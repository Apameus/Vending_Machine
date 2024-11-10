package vending.machine.service;

import vending.machine.data.Product;
import vending.machine.exception.MachineOverloadException;
import vending.machine.exception.NotEnoughMoneyException;
import vending.machine.exception.ProductNotFoundException;
import vending.machine.exception.ZeroProductStockException;

import java.util.List;

public interface ProductService {

    List<Product> listProducts();
    Product findAvailableProduct(int productId) throws ProductNotFoundException, ZeroProductStockException;
    int retrieveProduct(Product product, int money) throws NotEnoughMoneyException;

    void addStock(int productId, int quantity) throws ProductNotFoundException, MachineOverloadException;
    void updatePrice(int productId, int price) throws ProductNotFoundException;
    void updateId(int productId, int id) throws ProductNotFoundException;
    void updateName(int productId, String name) throws ProductNotFoundException;
    void addProduct(Product product);
    void removeProduct(int productId);
}
