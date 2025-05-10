package com.mentorshipwise.balanceservicestudy.repository;

import com.mentorshipwise.balanceservicestudy.models.Balance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends MongoRepository<Balance, String> {

    Optional<Balance> findByCurrencyAndUserId(String currency, String userId);

    Optional<Balance> findByIdAndUserId(String balanceId, String userId);

    List<Balance> findByUserId(String userId);
}
