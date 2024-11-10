package vending.machine.data;

import vending.machine.exception.CommandDoeNotExistException;

import java.util.Arrays;
import java.util.HashMap;

public enum Command {
    EXIT(00),
    UPDATE_QUANTITY(01),
    UPDATE_PRICE(02),
    UPDATE_ID(03),
    UPDATE_NAME(04),
    ADD_PRODUCT(10),
    REMOVE_PRODUCT(20),
    TOP_THREE_MOST_SELLING_PRODUCTS(30),
    TOTAL_EARNINGS(40),
    RETRIEVE_AVAILABLE_EARNINGS(50);

    final int ID;
    private final HashMap<Integer,Command> COMMAND_MAP = new HashMap();

    public Command getCommandById(int id) throws CommandDoeNotExistException {
        Command command = COMMAND_MAP.get(id);
        if (command == null) throw new CommandDoeNotExistException();
        return command;
    }

    public int id(){return ID;}

    Command(int id) {
        this.ID = id;
    }
//        ID = id;
//        Arrays.stream(Command.values())
//                .forEach(command -> COMMAND_MAP.put(command.ID, command));}
}
