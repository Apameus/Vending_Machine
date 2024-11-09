package vending.machine.repository;

import vending.machine.data.AuthorizedUser;

public interface AuthorizedRepository {
    AuthorizedUser findUserById(int userId);
}
