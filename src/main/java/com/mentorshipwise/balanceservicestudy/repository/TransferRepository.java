package com.mentorshipwise.balanceservicestudy.repository;

import com.mentorshipwise.balanceservicestudy.models.Transfer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransferRepository extends MongoRepository<Transfer, String> {
    Optional<Transfer> findByTransferId(String transferId);

    List<Transfer> findAllBySenderUserIdOrBeneficiaryId(String senderId, String beneficiaryId);
}
