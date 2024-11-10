package vending.machine.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vending.machine.data.Earnings;
import vending.machine.data.Sale;
import vending.machine.repository.AnalyticsRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AnalyticServiceImplTest {
    private AnalyticsRepository mockRepo = Mockito.mock(AnalyticsRepository.class);
    private AnalyticService service;

    @Test
    @DisplayName("Get top three most selling products")
    void getTopThreeMostSellingProducts() {
        Sale sale1 = new Sale(10, 45);
        Sale sale2 = new Sale(11, 123);
        Sale sale3 = new Sale(12, 423);
        Sale sale4 = new Sale(13, 541);

        when(mockRepo.getAllSales()).thenReturn(List.of(sale1,sale2,sale3,sale4));
        service = new AnalyticServiceImpl(mockRepo);

        List<Sale> expectedSalesList = List.of(sale4, sale3, sale2);

        assertThat(service.topThreeMostSellingProducts()).isEqualTo(expectedSalesList);
    }

    @Test
    @DisplayName("Get totalEarnings")
    void getTotalEarnings() {
        when(mockRepo.getEarnings()).thenReturn(new Earnings(1000,10));
        service = new AnalyticServiceImpl(mockRepo);

        assertThat(service.totalEarnings()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Get availableEarnings")
    void getAvailableEarnings() {
        when(mockRepo.getEarnings()).thenReturn(new Earnings(1000,10));
        service = new AnalyticServiceImpl(mockRepo);

        assertThat(service.retrieveAvailableEarnings()).isEqualTo(10);
        verify(mockRepo, times(1)).refreshAvailableEarnings();
    }

}