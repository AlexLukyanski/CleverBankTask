package by.clever.bank.controller.constant;

public final class RequestParam {

    private RequestParam() {

    }

    //FrontController parameter
    public final static String FRONT_CONTROLLER_ATTRIBUTE = "check";

    //Parameters for transactions
    public static final String MONEY_AMOUNT = "MoneyAmount";
    public static final String ACCOUNT_NUMBER = "AccountNumber";
    public static final String SENDER_ACCOUNT_NUMBER = "SenderAccountNumber";
    public static final String RECEIVER_ACCOUNT_NUMBER = "ReceiverAccountNumber";

    //Parameters for CRUD operations with Bank entity
    public static final String NEW_BANK_NAME = "NewBankName";
    public static final String OLD_BANK_NAME = "OldBankName";
    public static final String KEY_TO_BANK_BEAN = "Bank";
    public static final String KEY_TO_BANK_NAME = "BankName";

    //Parameters for CRUD operations with User entity
    public static final String NEW_USER_NAME = "NewUserName";
    public static final String NEW_USER_SURNAME = "NewUserSurname";
    public static final String NEW_USER_PATRONYMIC = "NewUserPatronymic";
    public static final String NEW_USER_DATE_OF_BIRTH = "NewUserDateOfBirth";
    public static final String NEW_USER_PHONE_NUMBER = "NewUserPhoneNumber";
    public static final String NEW_USER_EMAIL = "NewUserEmail";
    public static final String OLD_USER_NAME = "OldUserName";
    public static final String OLD_USER_SURNAME = "OldUserSurname";
    public static final String OLD_USER_PATRONYMIC = "OldUserPatronymic";
    public static final String OLD_USER_DATE_OF_BIRTH = "OldUserDateOfBirth";
    public static final String OLD_USER_PHONE_NUMBER = "OldUserPhoneNumber";
    public static final String OLD_USER_EMAIL = "OldUserEmail";
    public static final String USER_ID = "UserID";
    public static final String KEY_TO_USER_BEAN = "User";


}
