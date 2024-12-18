package vending.machine.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vending.machine.data.Product;
import vending.machine.exception.MachineOverloadException;
import vending.machine.exception.NotEnoughMoneyException;
import vending.machine.exception.ProductNotFoundException;
import vending.machine.exception.ZeroProductStockException;
import vending.machine.repository.AnalyticsRepository;
import vending.machine.repository.ProductRepository;
import vending.machine.repository.ProductRepositoryImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplUnitTests {

    static final Product PRODUCT_WITH_ZERO_STOCK = new Product(50, "Mock", 50, 0);
    private final Product PRODUCT_WITH_QUANTITY = new Product(15, "Apple", 50, 5);

    @Mock
    ProductRepository productRepository;
    //= Mockito.mock(ProductRepository.class);

    @Mock
    AnalyticsRepository analyticsRepository;
    //= Mockito.mock(AnalyticsRepository.class);

    @InjectMocks
    ProductServiceImpl productService;
    //= new ProductServiceImpl(productRepository, analyticsRepository);

    @Test
    @DisplayName("When findAvailableProduct productId does not exist, throw Exception")
    void whenFindAvailableProductProductIdDoesNotExistThrowException() {
        when(productRepository.findProductById(anyInt())).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> productService.findAvailableProduct(anyInt()));
    }

    @Test
    @DisplayName("When findAvailableProduct has no available stock, throw Exception")
    void whenFindAvailableProductHasNoAvailableStockThrowException() {
        when(productRepository.findProductById(anyInt())).thenReturn(PRODUCT_WITH_ZERO_STOCK);

        assertThrows(ZeroProductStockException.class, () -> productService.findAvailableProduct(anyInt()));
    }

    @Test
    @DisplayName("When retrieveProduct with not enough money, throw Exception")
    void whenRetrieveProductWithNotEnoughMoneyThrowException() {
        assertThrows(NotEnoughMoneyException.class,
                () -> productService.retrieveProduct(PRODUCT_WITH_QUANTITY, PRODUCT_WITH_QUANTITY.price() - 1));
    }

    @Test
    @DisplayName("When findAvailableProduct has a Product with stock, then return the Product")
    void whenFindAvailableProductHasAProductWithStockThenReturnTheProduct() {
        when(productRepository.findProductById(anyInt())).thenReturn(PRODUCT_WITH_QUANTITY);

        var product = assertDoesNotThrow(() -> productService.findAvailableProduct(anyInt()));

        assertThat(product).isSameAs(PRODUCT_WITH_QUANTITY);
    }

    @Test
    @DisplayName("RetrieveProduct with enough money")
    void retrieveProductWithEnoughMoney() {
        var change = assertDoesNotThrow(() -> productService.retrieveProduct(PRODUCT_WITH_QUANTITY, PRODUCT_WITH_QUANTITY.price() * 2));

        verify(productRepository,  times(1))
                .saveProduct(PRODUCT_WITH_QUANTITY.withQuantity(q -> q - 1));
        verify(analyticsRepository, times(1))
                .increaseSales(PRODUCT_WITH_QUANTITY.id());

        assertThat(change).isEqualTo(PRODUCT_WITH_QUANTITY.price());
    }

    @Test
    @DisplayName("Return listed Products")
    void returnListedProducts() {
        when(productRepository.findAllProducts()).thenReturn(List.of(PRODUCT_WITH_QUANTITY));

        assertThat(productService.listProducts()).containsExactly(PRODUCT_WITH_QUANTITY);
    }

    @Test
    @DisplayName("Add stock to existing product")
    void addStockToExistingProduct(){
        when(productRepository.findProductById(anyInt())).thenReturn(PRODUCT_WITH_QUANTITY);
        assertDoesNotThrow( () -> productService.addStock(1,2));

        Product expectedProduct = PRODUCT_WITH_QUANTITY.withQuantity(PRODUCT_WITH_QUANTITY.quantity() + 2);
        verify(productRepository, times(1))
                .saveProduct(expectedProduct);
    }


    @Test
    @DisplayName("AddStock to product with new quantity over 10, throw Exception")
    void addStockToProductWithNewQuantityOver10ThrowException() {
        when(productRepository.findProductById(anyInt())).thenReturn(PRODUCT_WITH_QUANTITY);
        assertThrows(MachineOverloadException.class, () -> productService.addStock(anyInt(), 6));
    }

    @Test
    @DisplayName("Update price")
    void updatePrice() throws ProductNotFoundException {
        when(productRepository.findProductById(anyInt())).thenReturn(PRODUCT_WITH_QUANTITY);
        productService.updatePrice(anyInt(),10);

        Product expectedProduct = PRODUCT_WITH_QUANTITY.withPrice(10);
        verify(productRepository, times(1)).saveProduct(expectedProduct);
    }

    @Test
    @DisplayName("Update id")
    void updateId() throws ProductNotFoundException {
        when(productRepository.findProductById(anyInt())).thenReturn(PRODUCT_WITH_QUANTITY);
        productService.updateId(anyInt(), 42);

        Product expectedProduct = PRODUCT_WITH_QUANTITY.withId(42);
        verify(productRepository, times(1)).saveProduct(expectedProduct);
    }

    @Test
    @DisplayName("Update name")
    void updateName() throws ProductNotFoundException {
        when(productRepository.findProductById(anyInt())).thenReturn(PRODUCT_WITH_QUANTITY);
        productService.updateName(anyInt(), "GREEK_SALAD");

        Product expectedProduct = PRODUCT_WITH_QUANTITY.withName("GREEK_SALAD");
        verify(productRepository,times(1)).saveProduct(expectedProduct);
    }

    @Test
    @DisplayName("Add Product")
    void addProduct() {
        Product newProduct = new Product(15, "Beef",72,7);
        productService.addProduct(newProduct);

        verify(productRepository, times(1)).saveProduct(newProduct);
    }

    @Test
    @DisplayName("Remove Product")
    void removeProduct() {
        productService.removeProduct(PRODUCT_WITH_QUANTITY.id());

        verify(productRepository, times(1)).removeProduct(PRODUCT_WITH_QUANTITY.id());
    }
}

