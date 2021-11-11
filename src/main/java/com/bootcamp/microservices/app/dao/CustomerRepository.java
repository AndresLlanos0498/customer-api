package com.bootcamp.microservices.app.dao;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp.microservices.app.models.Customer;

import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String>{
	
	public Flux<Customer> findByIdentityNumber(String identityNumber);

	public Flux<Customer> findByIdentityType(String identityType);

	public Flux<Customer> findByCustomerType(String type);
	
}
