package by.clever.bank.controller;

import by.clever.bank.controller.impl.AddMoneyToAccountCommand;
import by.clever.bank.controller.impl.TransferMoneyBetweenAccounts;
import by.clever.bank.controller.impl.WithdrawMoneyFromAccount;

import java.util.HashMap;
import java.util.Map;

public final class CommandFactory {

    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandFactory() {

        commands.put(CommandName.ADD_MONEY_TO_ACCOUNT, new AddMoneyToAccountCommand());
        commands.put(CommandName.WITHDRAW_MONEY_FROM_ACCOUNT, new WithdrawMoneyFromAccount());
        commands.put(CommandName.TRANSFER_MONEY, new TransferMoneyBetweenAccounts());
    }

    public Command getCommand(String name) {

        CommandName commandName = CommandName.valueOf(name);
        return commands.get(commandName);
    }
}
