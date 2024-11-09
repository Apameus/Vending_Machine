package vending.machine.service;

import vending.machine.data.AuthorizedUser;
import vending.machine.repository.AuthorizedRepository;

public final class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthorizedRepository authorizedRepository;

    public AuthorizationServiceImpl(AuthorizedRepository authorizedRepository) {
        this.authorizedRepository = authorizedRepository;
    }

    @Override
    public AuthorizedUser authorizeUser(int userId, int password) {
        return null;
    }
}
