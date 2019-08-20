package io.rainrobot.wake.rest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan(basePackages = {"io.rainrobot.wake.core",
							"io.rainrobot.wake.rest"})
public class  JpaConfig {

}

