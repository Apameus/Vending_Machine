package vending.machine.repository;

import vending.machine.data.AuthorizedUser;

public interface AuthorizationRepository {
    AuthorizedUser findUserById(int userId);
}
