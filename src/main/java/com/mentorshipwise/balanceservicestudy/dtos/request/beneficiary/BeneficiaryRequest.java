package com.mentorshipwise.balanceservicestudy.dtos.request.beneficiary;

import lombok.Data;

public class BeneficiaryRequest {

    @Data
    public static class Create {
        private String beneficiaryId;
        private String currency;
        private String name;
    }
}
