package by.clever.bank.controller;

import by.clever.bank.controller.impl.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandFactory {

    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandFactory() {

        commands.put(CommandName.ADD_MONEY_TO_ACCOUNT, new AddMoneyToAccountCommand());
        commands.put(CommandName.WITHDRAW_MONEY_FROM_ACCOUNT, new WithdrawMoneyFromAccountCommand());
        commands.put(CommandName.TRANSFER_MONEY, new TransferMoneyBetweenAccountsCommand());
        commands.put(CommandName.ADD_NEW_BANK, new CreateBankCommand());
        commands.put(CommandName.UPDATE_BANK, new UpdateBankCommand());
        commands.put(CommandName.READ_BANK, new ReadBankCommand());
    }

    public Command getCommand(String name) {

        CommandName commandName = CommandName.valueOf(name);
        return commands.get(commandName);
    }
}
