package vending.machine.service;

import vending.machine.data.AuthorizedUser;
import vending.machine.exception.AuthorizationFailedException;
import vending.machine.repository.AuthorizationRepository;

public final class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    public AuthorizationServiceImpl(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    @Override
    public AuthorizedUser authorizeUser(int userId, int password) throws AuthorizationFailedException {
        AuthorizedUser user = authorizationRepository.findUserById(userId);
        if (user == null || user.password() != password) throw new AuthorizationFailedException();
        return user;
    }
}
