package vending.machine.repository;

import vending.machine.data.Product;

public interface ProductRepository {
    Product findProductById(int productId);

    void updateQuantity(int productId, int quantity);
}
