package by.clever.bank.controller;

import by.clever.bank.controller.impl.AddMoneyToAccountCommand;

import java.util.HashMap;
import java.util.Map;

public final class CommandFactory {

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandFactory() {

        commands.put(CommandName.ADD_MONEY_TO_ACCOUNT, new AddMoneyToAccountCommand());
    }

    public Command getCommand(String name) {

        CommandName commandName = CommandName.valueOf(name);
        return commands.get(commandName);
    }
}
