package com.mentorshipwise.balanceservicestudy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "balance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Balance {

    @Id
    private String id;

    private String userId;

    private String currency;

    private BigDecimal available;

    private Instant createdAt;

    private Instant updatedAt;
}
