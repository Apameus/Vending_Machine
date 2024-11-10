package vending.machine.service;

import vending.machine.data.Product;
import vending.machine.exception.MachineOverloadException;
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

    @Override
    public void addStock(int productId, int quantity) throws ProductNotFoundException, MachineOverloadException {
        Product product = getProduct(productId);
        if (product.quantity() + quantity >= 10) throw new MachineOverloadException();
        productRepository.saveProduct(product.withQuantity(product.quantity() + 1));
    }

    @Override
    public void updatePrice(int productId, int price) throws ProductNotFoundException {
        Product product = getProduct(productId);
        productRepository.saveProduct(product.withPrice(price));
    }

    @Override
    public void updateId(int productId, int id) throws ProductNotFoundException {
        Product product = getProduct(productId);
        productRepository.saveProduct(product.withId(id));
    }

    @Override
    public void updateName(int productId, String name) throws ProductNotFoundException {
        Product product = getProduct(productId);
        productRepository.saveProduct(product.withName(name));
    }

    @Override
    public void addProduct(Product product) {
        productRepository.saveProduct(product);
    }

    @Override
    public void removeProduct(int productId) {
        productRepository.removeProduct(productId);
    }


    private Product getProduct(int productId) throws ProductNotFoundException {
        Product product = productRepository.findProductById(productId);
        if (product == null) throw new ProductNotFoundException();
        return product;
    }
}
