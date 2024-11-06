package vending.machine.services;

import vending.machine.data.Product;
import vending.machine.data.ProductWithChange;
import vending.machine.exeptions.MachineOverloadedException;
import vending.machine.exeptions.NotEnoughMoneyException;
import vending.machine.exeptions.ProductNotFoundException;
import vending.machine.exeptions.ZeroStockException;
import vending.machine.repositories.AnalyticRepository;
import vending.machine.repositories.ProductRepository;
import vending.machine.repositories.ProductRepositoryImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;
    private AnalyticRepository analyticRepository;

    public ProductServiceImpl(ProductRepository productRepository, AnalyticRepository analyticRepository) {
        this.productRepository = productRepository;
        this.analyticRepository = analyticRepository;
    }

    @Override
    public Collection<Product> listProducts() {
        return productRepository.findAllProducts();
    }

    @Override
    public void verifyProductAvailability(int productId) throws ProductNotFoundException, ZeroStockException {
        Product product = productRepository.findProductBy(productId);
        if (product == null) throw new ProductNotFoundException();
        else if (product.quantity() == 0) throw new ZeroStockException();
    }

    @Override
    public ProductWithChange retrieveProduct(int productId, float money) throws NotEnoughMoneyException {
        Product product = productRepository.findProductBy(productId);
        if (money < product.price()) throw new NotEnoughMoneyException();

        productRepository.decreaseQuantity(productId);
        analyticRepository.increaseSales(productId);

        float change = money - product.price();
        analyticRepository.increaseTotalEarningsBy(product.price());

        return new ProductWithChange(product, change);
    }

    @Override
    public void addStock(int productId, Integer quantity) throws ProductNotFoundException, MachineOverloadedException {
        Product product = productRepository.findProductBy(productId);
        if (product == null) throw new ProductNotFoundException();
        else if (product.quantity() + quantity > 10) throw new MachineOverloadedException();
        productRepository.updateQuantity(productId, product.quantity() + quantity);
    }

    @Override
    public void updatePrice(int productId, Float price) throws ProductNotFoundException {
        Product product = productRepository.findProductBy(productId);
        if (product == null) throw new ProductNotFoundException();
        productRepository.updatePrice(productId, price);
    }

    @Override
    public void updateId(int productId, Integer newId) throws ProductNotFoundException {
        Product product = productRepository.findProductBy(productId);
        if (product == null) throw new ProductNotFoundException();
        productRepository.updateId(productId, newId);
    }

    @Override
    public void updateName(int productId, String name) throws ProductNotFoundException {
        Product product = productRepository.findProductBy(productId);
        if (product == null) throw new ProductNotFoundException();
        productRepository.updateName(productId, name);
    }

    @Override
    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    @Override
    public void removeProduct(int productId) {
        productRepository.removeProduct(productId);
    }
}
