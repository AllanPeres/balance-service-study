package com.mentorshipwise.balanceservicestudy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    // to enable @CreatedDate and @LastModifiedDate
}
