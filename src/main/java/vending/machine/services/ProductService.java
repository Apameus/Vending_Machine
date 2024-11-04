package vending.machine.services;

import vending.machine.data.Product;
import vending.machine.exeptions.NotEnoughMoneyException;
import vending.machine.exeptions.ProductNotFoundException;
import vending.machine.exeptions.ZeroStockException;

import java.util.Collection;
import java.util.Map;

public interface ProductService {
    Collection<Product> listProducts();

    void verifyProductAvailability(int productId) throws ProductNotFoundException, ZeroStockException;

    //TODO: Return Product and change (chose the right type)
    // Product , Change
    Map<Product, Float> retrieveProduct(int productId, float money) throws NotEnoughMoneyException;
}
