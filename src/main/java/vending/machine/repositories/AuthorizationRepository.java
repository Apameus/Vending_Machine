package vending.machine.repositories;

import vending.machine.data.AuthorizedUser;

public interface AuthorizationRepository {
    AuthorizedUser findUserBy(int userId);
}
