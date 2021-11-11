package com.bootcamp.microservices.app.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.bootcamp.microservices.app.models.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

	//public Mono<Customer> save(Customer pcustomer);
	
	//Mono<ResponseEntity<Map<String, Object>>> saveCustomer(Customer costomer);

	public Mono<Void> delete(String id);

	/*public Mono<Void> update(String id, Customer customer);*/

	public Flux<Customer> findAll();
	
	public Flux<Customer> findByIdentityType(String identityType);
	
	public Flux<Customer> findByCostumerTypes(String type);

	public Mono<Customer> findById(String id);

	public Flux<Customer> findByIdentity(String identityNumber);

	Mono<ResponseEntity<Map<String, Object>>> saveCustomer(Customer customer);

}
