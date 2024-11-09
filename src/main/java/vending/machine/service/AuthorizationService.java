package vending.machine.service;

import vending.machine.data.AuthorizedUser;
import vending.machine.exception.AuthorizationFailedException;

public interface AuthorizationService {
    AuthorizedUser authorizeUser(int userId, int password) throws AuthorizationFailedException;
}
