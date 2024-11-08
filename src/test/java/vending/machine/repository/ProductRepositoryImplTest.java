package vending.machine.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vending.machine.data.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductRepositoryImplTest {

    DataSource dataSource = Mockito.mock(DataSource.class);

    public static final Product BANANA = new Product(1,"banana",5,10);

    @Test
    @DisplayName("Find existing product")
    void findExistingProduct() {
        when(dataSource.load()).thenReturn(List.of(BANANA));
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl(dataSource);

        assertThat(productRepository.findProductById(BANANA.id())).isEqualTo(BANANA);
    }

    @Test
    @DisplayName("Return null if given product id does not exist")
    void returnNullIfGivenProductIdDoesNotExist() {
        when(dataSource.load()).thenReturn(List.of(BANANA));
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl(dataSource);

        assertThat(productRepository.findProductById(123)).isNull();
    }

    @Test
    @DisplayName("If Product Id Exist Update Product")
    void ifProductIdExistUpdateProduct() {
        // product updated in the cache

        when(dataSource.load()).thenReturn(List.of(BANANA));
        ProductRepositoryImpl repository = new ProductRepositoryImpl(dataSource);


        repository.saveProduct(BANANA.withName("Orange"));

        Product updatedProduct = repository.findProductById(BANANA.id());

        assertThat(updatedProduct.name()).isEqualTo("Orange");

        verify(dataSource, times(1)).save(List.of(updatedProduct));
    }

    @Test
    @DisplayName("If ProductId does not exist, add product")
    void ifProductIdDoesNotExistAddProduct() {
       when(dataSource.load()).thenReturn(List.of());
       ProductRepositoryImpl productRepository= new ProductRepositoryImpl(dataSource);

       productRepository.saveProduct(BANANA);

       assertThat(productRepository.findProductById(BANANA.id())).isEqualTo(BANANA);
       verify(dataSource, times(1)).save(List.of(BANANA));
    }

    @Test
    @DisplayName("Find all products")
    void findAllProducts() {
        Product dragonFruit = new Product(14, "DragonFruit", 45, 4);
        when(dataSource.load()).thenReturn(List.of(BANANA,dragonFruit));

        ProductRepositoryImpl repo = new ProductRepositoryImpl(dataSource);

        assertThat(repo.findAllProducts()).containsExactly(BANANA, dragonFruit);
    }

}