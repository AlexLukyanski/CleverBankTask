package by.clever.bank.controller;

import by.clever.bank.controller.impl.*;
import by.clever.bank.controller.impl.create.CreateAccountCommand;
import by.clever.bank.controller.impl.create.CreateBankCommand;
import by.clever.bank.controller.impl.create.CreateTransactionCommand;
import by.clever.bank.controller.impl.create.CreateUserCommand;
import by.clever.bank.controller.impl.delete.DeleteAccountCommand;
import by.clever.bank.controller.impl.delete.DeleteBankCommand;
import by.clever.bank.controller.impl.delete.DeleteTransactionCommand;
import by.clever.bank.controller.impl.delete.DeleteUserCommand;
import by.clever.bank.controller.impl.read.ReadAccountCommand;
import by.clever.bank.controller.impl.read.ReadBankCommand;
import by.clever.bank.controller.impl.read.ReadTransactionCommand;
import by.clever.bank.controller.impl.read.ReadUserCommand;
import by.clever.bank.controller.impl.update.UpdateAccountCommand;
import by.clever.bank.controller.impl.update.UpdateBankCommand;
import by.clever.bank.controller.impl.update.UpdateTransactionCommand;
import by.clever.bank.controller.impl.update.UpdateUserCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for commands
 */
public final class CommandFactory {

    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandFactory() {

        commands.put(CommandName.ADD_MONEY_TO_ACCOUNT, new AddMoneyToAccountCommand());
        commands.put(CommandName.WITHDRAW_MONEY_FROM_ACCOUNT, new WithdrawMoneyFromAccountCommand());
        commands.put(CommandName.TRANSFER_MONEY, new TransferMoneyBetweenAccountsCommand());
        commands.put(CommandName.ADD_NEW_BANK, new CreateBankCommand());
        commands.put(CommandName.UPDATE_BANK, new UpdateBankCommand());
        commands.put(CommandName.READ_BANK, new ReadBankCommand());
        commands.put(CommandName.DELETE_BANK, new DeleteBankCommand());
        commands.put(CommandName.ADD_NEW_USER, new CreateUserCommand());
        commands.put(CommandName.UPDATE_USER, new UpdateUserCommand());
        commands.put(CommandName.READ_USER, new ReadUserCommand());
        commands.put(CommandName.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandName.ADD_NEW_ACCOUNT, new CreateAccountCommand());
        commands.put(CommandName.UPDATE_ACCOUNT, new UpdateAccountCommand());
        commands.put(CommandName.READ_ACCOUNT, new ReadAccountCommand());
        commands.put(CommandName.DELETE_ACCOUNT, new DeleteAccountCommand());
        commands.put(CommandName.ADD_NEW_TRANSACTION, new CreateTransactionCommand());
        commands.put(CommandName.UPDATE_TRANSACTION, new UpdateTransactionCommand());
        commands.put(CommandName.READ_TRANSACTION, new ReadTransactionCommand());
        commands.put(CommandName.DELETE_TRANSACTION, new DeleteTransactionCommand());
    }

    /**
     *
     * @param name
     * @return specified command
     */
    public Command getCommand(String name) {

        CommandName commandName = CommandName.valueOf(name);
        return commands.get(commandName);
    }
}
