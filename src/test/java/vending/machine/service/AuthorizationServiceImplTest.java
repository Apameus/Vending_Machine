package vending.machine.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vending.machine.data.AuthorizedUser;
import vending.machine.exception.AuthorizationFailedException;
import vending.machine.repository.AuthorizationRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class AuthorizationServiceImplTest {

    private final AuthorizedUser VALID_USER = new AuthorizedUser(20000000,2000);
    private final AuthorizationRepository mockRepository = Mockito.mock(AuthorizationRepository.class);
    private AuthorizationService service ;

    @Test
    @DisplayName("If userId does not exist, throw AuthException")
    void ifUserIdDoesNotExistThrowAuthException() {

        when(mockRepository.findUserById(anyInt())).thenReturn(null);
        service =  new AuthorizationServiceImpl(mockRepository);

        assertThrows(AuthorizationFailedException.class, () -> service.authorizeUser(90909090, 9999));
    }

    @Test
    @DisplayName("If userId is correct but password is wrong")
    void ifUserIdIsCorrectButPasswordIsWrong() {

        when(mockRepository.findUserById(anyInt())).thenReturn(VALID_USER);
        service = new AuthorizationServiceImpl(mockRepository);

        assertThrows(AuthorizationFailedException.class, () -> service.authorizeUser(VALID_USER.id(),9999));
    }

    @Test
    @DisplayName("If correct userID and password, return user")
    void ifCorrectUserIdAndPasswordReturnUser() throws AuthorizationFailedException {

        when(mockRepository.findUserById(VALID_USER.id())).thenReturn(VALID_USER);
        service = new AuthorizationServiceImpl(mockRepository);

        assertThat(service.authorizeUser(VALID_USER.id(), VALID_USER.password())).isEqualTo(VALID_USER);
    }
}