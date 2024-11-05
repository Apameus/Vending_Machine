package vending.machine.services;

import vending.machine.data.Product;
import vending.machine.data.ProductWithChange;
import vending.machine.exeptions.NotEnoughMoneyException;
import vending.machine.exeptions.ProductNotFoundException;
import vending.machine.exeptions.ZeroStockException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface ProductService {
    Collection<Product> listProducts();

    void verifyProductAvailability(int productId) throws ProductNotFoundException, ZeroStockException;

    ProductWithChange retrieveProduct(int productId, float money) throws NotEnoughMoneyException;
}
