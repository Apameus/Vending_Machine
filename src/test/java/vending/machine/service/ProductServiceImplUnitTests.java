package vending.machine.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vending.machine.data.Product;
import vending.machine.exception.NotEnoughMoneyException;
import vending.machine.exception.ProductNotFoundException;
import vending.machine.exception.ZeroProductStockException;
import vending.machine.repository.AnalyticsRepository;
import vending.machine.repository.ProductRepository;

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
    @DisplayName("ReturnListedProducts")
    void returnListedProducts() {
        when(productRepository.findAllProducts()).thenReturn(List.of(PRODUCT_WITH_QUANTITY));

        assertThat(productService.listProducts()).containsExactly(PRODUCT_WITH_QUANTITY);
    }

}

