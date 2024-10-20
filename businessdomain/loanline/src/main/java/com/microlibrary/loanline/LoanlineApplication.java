package com.microlibrary.loanline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class LoanlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanlineApplication.class, args);
	}
        
  

}
