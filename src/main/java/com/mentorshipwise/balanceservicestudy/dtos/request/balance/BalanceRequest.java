package com.mentorshipwise.balanceservicestudy.dtos.request.balance;

import lombok.Data;

import java.math.BigDecimal;

public class BalanceRequest {

    @Data
    public static class Create {
        private String userId;
        private String currency;
    }

    @Data
    public static class Add {
        private String userId;
        private String currency;
        private BigDecimal amount;
    }

    @Data
    public static class Spend {
        private String userId;
        private String currency;
        private BigDecimal amount;
    }

    @Data
    public static class Send {
        private String userId;
        private String beneficiaryId;
        private String currency;
        private BigDecimal amount;
        private String transferId;
        private String description;
    }
}
