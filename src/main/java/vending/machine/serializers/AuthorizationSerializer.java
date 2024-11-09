package vending.machine.serializers;

import vending.machine.data.AuthorizedUser;

public final class AuthorizationSerializer {

    public AuthorizedUser parseUser(String line){
        String[] values = line.split(",");
        return new AuthorizedUser(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }

    public String serialize(AuthorizedUser user){
        return user.id() + "," + user.password();
    }
}
