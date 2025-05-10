package com.mentorshipwise.balanceservicestudy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "beneficiary")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Beneficiary {

    @Id
    private String id;
    
    private String beneficiaryId;

    private String ownerId;

    private String beneficiaryBalanceId;

    private String name;

    private Instant createdAt;
}
