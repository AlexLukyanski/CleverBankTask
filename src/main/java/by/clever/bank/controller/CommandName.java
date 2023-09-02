package by.clever.bank.controller;

/**
 * Contains list of all commands
 */
public enum CommandName {
    ADD_MONEY_TO_ACCOUNT, WITHDRAW_MONEY_FROM_ACCOUNT, TRANSFER_MONEY,

    //CRUD for Bank entity
    ADD_NEW_BANK, UPDATE_BANK, READ_BANK, DELETE_BANK,

    //CRUD for User entity
    ADD_NEW_USER, UPDATE_USER, READ_USER, DELETE_USER,

    //CRUD for User entity
    ADD_NEW_ACCOUNT, UPDATE_ACCOUNT, READ_ACCOUNT, DELETE_ACCOUNT,

    //CRUD for Transaction entity
    ADD_NEW_TRANSACTION, UPDATE_TRANSACTION, READ_TRANSACTION, DELETE_TRANSACTION
}
