package com.mentorshipwise.balanceservicestudy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

// TODO: create spending limit
// TODO: Create entitties: Limit aggregation, make profile/user config, service to check limits
// TODO: migration DV or a service that would insert all in the DB (because of MOngon), be carefull
@Document(collection = "transfer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transfer {

    @Id
    private String id;

    private String transferId;

    private String senderUserId;
    private String senderBalanceId;

    private String beneficiaryId;
    private String beneficiaryBalanceId;

    private String currency;
    private BigDecimal amount;

    private String description;

    private Instant createdAt;
}
