package vending.machine.services;

import vending.machine.data.AuthorizedUser;
import vending.machine.exeptions.AuthorizationFailedException;
import vending.machine.repositories.AuthorizationRepository;

public final class AuthorizationServiceImpl implements AuthorizationService{
    private AuthorizationRepository authorizationRepository;

    public AuthorizationServiceImpl(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    @Override
    public void authorizeUser(int userId, int password) throws AuthorizationFailedException {
        AuthorizedUser user = authorizationRepository.findUserBy(userId);
        if (user == null) throw new AuthorizationFailedException();
    }
}
