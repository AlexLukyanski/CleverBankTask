package by.clever.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO class for receipts of fulfilled transactions
 */
public class Receipt {
    private final String title;
    private final int id;
    private final LocalDateTime dateTime;
    private final String type;
    private final String senderBankName;
    private final String receiverBankName;
    private final String senderAccountNumber;
    private final String receiverAccountNumber;
    private final BigDecimal amount;

    public Receipt(String title, int id, LocalDateTime dateTime, String type, String senderBankName, String receiverBankName, String senderAccountNumber, String receiverAccountNumber, BigDecimal amount) {
        this.title = title;
        this.id = id;
        this.dateTime = dateTime;
        this.type = type;
        this.senderBankName = senderBankName;
        this.receiverBankName = receiverBankName;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
    }

    public static ReceiptBuilder builder() {
        return new ReceiptBuilder();
    }

    public String getTitle() {
        return this.title;
    }

    public int getId() {
        return this.id;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public String getType() {
        return this.type;
    }

    public String getSenderBankName() {
        return this.senderBankName;
    }

    public String getReceiverBankName() {
        return this.receiverBankName;
    }

    public String getSenderAccountNumber() {
        return this.senderAccountNumber;
    }

    public String getReceiverAccountNumber() {
        return this.receiverAccountNumber;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public static class ReceiptBuilder {
        private String title = "Receipt";
        private int id;
        private LocalDateTime dateTime;
        private String type;
        private String senderBankName;
        private String receiverBankName;
        private String senderAccountNumber;
        private String receiverAccountNumber;
        private BigDecimal amount;

        public ReceiptBuilder() {
        }

        public ReceiptBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ReceiptBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ReceiptBuilder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ReceiptBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ReceiptBuilder senderBankName(String senderBankName) {
            this.senderBankName = senderBankName;
            return this;
        }

        public ReceiptBuilder receiverBankName(String receiverBankName) {
            this.receiverBankName = receiverBankName;
            return this;
        }

        public ReceiptBuilder senderAccountNumber(String senderAccountNumber) {
            this.senderAccountNumber = senderAccountNumber;
            return this;
        }

        public ReceiptBuilder receiverAccountNumber(String receiverAccountNumber) {
            this.receiverAccountNumber = receiverAccountNumber;
            return this;
        }

        public ReceiptBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Receipt build() {
            return new Receipt(this.title, this.id, this.dateTime, this.type, this.senderBankName, this.receiverBankName, this.senderAccountNumber, this.receiverAccountNumber, this.amount);
        }

        public String toString() {
            return "Receipt.ReceiptBuilder(title=" + this.title + ", id=" + this.id + ", dateTime=" + this.dateTime + ", type=" + this.type + ", senderBankName=" + this.senderBankName + ", receiverBankName=" + this.receiverBankName + ", senderAccountNumber=" + this.senderAccountNumber + ", receiverAccountNumber=" + this.receiverAccountNumber + ", amount=" + this.amount + ")";
        }
    }
}