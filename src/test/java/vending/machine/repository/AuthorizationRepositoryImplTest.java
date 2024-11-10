package vending.machine.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vending.machine.data.AuthorizedUser;
import vending.machine.repository.dataSource.AuthorizationDataSource;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class AuthorizationRepositoryImplTest {

    AuthorizationDataSource mockData = Mockito.mock(AuthorizationDataSource.class);
    AuthorizationRepository repository;
    private final AuthorizedUser VALID_USER = new AuthorizedUser(44444444,44440);

    @Test
    @DisplayName("Find user by existing id")
    void findUserByExistingId() {
        when(mockData.load()).thenReturn(Collections.singleton(VALID_USER));
        repository = new AuthorizationRepositoryImpl(mockData);

        AuthorizedUser expectedUser = repository.findUserById(VALID_USER.id());
        assertThat(expectedUser).isEqualTo(VALID_USER);
    }

//    @Test
//    @DisplayName("Try to find user by non existing id")
//    void tryToFindUserByNonExistingId() {
//        when(mockData.load()).thenReturn(null);
//        repository = new AuthorizationRepositoryImpl(mockData);
//
//
//        assertThrows(NullPointerException.class,() -> repository.findUserById(333));
//    }
}