package vending.machine.services;

import vending.machine.data.Product;
import vending.machine.data.ProductWithChange;
import vending.machine.exeptions.MachineOverloadedException;
import vending.machine.exeptions.NotEnoughMoneyException;
import vending.machine.exeptions.ProductNotFoundException;
import vending.machine.exeptions.ZeroStockException;

import java.util.Collection;

public interface ProductService {
    Collection<Product> listProducts();

    void verifyProductAvailability(int productId) throws ProductNotFoundException, ZeroStockException;

    ProductWithChange retrieveProduct(int productId, float money) throws NotEnoughMoneyException;

    void addStock(int productId, Integer quantity) throws ProductNotFoundException, MachineOverloadedException;

    void updatePrice(int productId, Float price) throws ProductNotFoundException;

    void updateId(int productId, Integer newId) throws ProductNotFoundException;

    void updateName(int productId, String name) throws ProductNotFoundException;

    void addProduct(Product product);

    void removeProduct(int productId);
}
