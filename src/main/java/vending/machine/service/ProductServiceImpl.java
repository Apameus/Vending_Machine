package vending.machine.service;

import vending.machine.data.Product;
import vending.machine.exception.NotEnoughMoneyException;
import vending.machine.exception.ProductNotFoundException;
import vending.machine.exception.ZeroProductStockException;
import vending.machine.repository.AnalyticsRepository;
import vending.machine.repository.ProductRepository;

import java.util.List;

public final class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final AnalyticsRepository analyticsRepository;

    public ProductServiceImpl(ProductRepository productRepository, AnalyticsRepository analyticsRepository) {
        this.productRepository = productRepository;
        this.analyticsRepository = analyticsRepository;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public Product findAvailableProduct(int productId) throws ProductNotFoundException, ZeroProductStockException {
        var product = productRepository.findProductById(productId);
        if (product == null) throw new ProductNotFoundException();
        if (product.quantity() == 0) throw new ZeroProductStockException();
        return product;
    }

    @Override
    public int retrieveProduct(Product product, int money) throws NotEnoughMoneyException {
        if (product.price() > money)
            throw new NotEnoughMoneyException();
        productRepository.saveProduct(product.withQuantity(product.quantity() - 1));
        analyticsRepository.increaseSales(product.id());
        analyticsRepository.increaseEarnings(product.price());
        return money - product.price();
    }

}
