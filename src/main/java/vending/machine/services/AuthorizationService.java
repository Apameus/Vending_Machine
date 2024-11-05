package vending.machine.services;

import vending.machine.exeptions.AuthorizationFailedException;

public interface AuthorizationService {
    void authorizeUser(int userId, int password) throws AuthorizationFailedException;
}
