package vending.machine.data;

import vending.machine.exeptions.CommandDoesntExistException;

import java.util.HashMap;

public enum Command {
    EXIT(00),
    ADD_STOCK(01),
    UPDATE_PRICE(02),
    UPDATE_ID(03),
    UPDATE_NAME(04),
    ADD_PRODUCT(10),
    REMOVE_PRODUCT(20),
    TOP_THREE_MOST_SELLING_PRODUCTS(30),
    TOTAL_EARNINGS(40),
    RETRIEVE_MONEY(50);

    final int ID;
    private static final HashMap<Integer,Command> COMMAND_MAP= new HashMap<>();

    static {
        for (Command command : values()) {
            COMMAND_MAP.put(command.ID, command);
        }
    }

    Command(int ID) {
        this.ID = ID;
    }

    public static Command getById(int ID) throws CommandDoesntExistException {
        Command command = COMMAND_MAP.get(ID);
        if (command == null) throw new CommandDoesntExistException();
        return command;
    }

    public int id() {
        return ID;
    }
}
