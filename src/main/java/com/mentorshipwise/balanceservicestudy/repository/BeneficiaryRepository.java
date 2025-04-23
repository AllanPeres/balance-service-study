package com.mentorshipwise.balanceservicestudy.repository;


import com.mentorshipwise.balanceservicestudy.models.Beneficiary;
import com.mentorshipwise.balanceservicestudy.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficiaryRepository extends MongoRepository<Beneficiary, String> {
    Optional<User> findByName(String email);
}
