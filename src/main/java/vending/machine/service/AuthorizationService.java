package vending.machine.service;

import vending.machine.data.AuthorizedUser;

public interface AuthorizationService {
    AuthorizedUser authorizeUser(int userId, int password);
}
