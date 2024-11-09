package vending.machine.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vending.machine.data.AnalyticData;
import vending.machine.data.Earnings;
import vending.machine.data.Sale;
import vending.machine.repository.dataSource.AnalyticsDataSource;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalyticsRepositoryImplTest {

    final AnalyticsDataSource mockSource = Mockito.mock(AnalyticsDataSource.class);

    final Sale SALE = new Sale(1,15);
    Earnings earnings = new Earnings(1000,100);

    AnalyticData mockData = new AnalyticData(earnings, List.of(SALE));


    @Test
    @DisplayName("Get all sales")
    void getAllSales() {
        when(mockSource.load()).thenReturn(mockData);
        AnalyticsRepositoryImpl repository = new AnalyticsRepositoryImpl(mockSource);

        assertThat(repository.getAllSales()).isEqualTo(List.of(SALE));
    }

    @Test
    @DisplayName("Increase sales of product")
    void increaseSalesOfProduct() {
        when(mockSource.load()).thenReturn(mockData);
        AnalyticsRepositoryImpl repository = new AnalyticsRepositoryImpl(mockSource);

        repository.increaseSales(SALE.productId());

        Sale expectedSale = SALE.withNumberOfSales(SALE.numberOfSales() + 1);
        assertThat(repository.getSale(SALE.productId())).isEqualTo(expectedSale);

        // verify that saved function was called
        AnalyticData updatedMockData = new AnalyticData(earnings, List.of(expectedSale));
        verify(mockSource, times(1)).save(updatedMockData);
    }
}