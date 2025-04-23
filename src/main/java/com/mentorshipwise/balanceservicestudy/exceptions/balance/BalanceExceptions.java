package com.mentorshipwise.balanceservicestudy.exceptions.balance;

import java.math.BigDecimal;

public class BalanceExceptions {

    public static class BalanceIsEmpty extends RuntimeException {
        public BalanceIsEmpty() {
            super("Balance is Empty");
        }
    }

    public static class BalanceAlreadyExistsException extends RuntimeException {
        public BalanceAlreadyExistsException(String currency) {
            super("Balance already exists for currency: " + currency);
        }
    }

    public static class BalanceNotFoundException extends RuntimeException {
        public BalanceNotFoundException(String currency) {
            super(currency == null || currency.isBlank()
                    ? "Not possible to find the balance"
                    : String.format("Not possible to find the %s balance", currency));
        }
    }

    public static class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String currency, BigDecimal amount) {
            super(String.format("Insufficient funds: attempted to spend %s from %s balance", amount, currency));
        }
    }

    public static class MinimumAmountException extends RuntimeException {
        public MinimumAmountException(String currency, BigDecimal minAmount) {
            super(String.format("The minimum amount for %s is %s", currency, minAmount));
        }
    }

    public static class BalanceNotEmptyException extends RuntimeException {
        public BalanceNotEmptyException() {
            super("Balance must be empty to be closed");
        }
    }
}
