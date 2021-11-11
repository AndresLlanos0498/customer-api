package com.bootcamp.microservices.app.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.bootcamp.microservices.app.dao.CustomerRepository;
import com.bootcamp.microservices.app.models.Customer;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository pcRepo;
	
	/*@Override
	public Mono<Customer> save(Customer pcustomer) {
		return pcRepo.save(pcustomer);		
	}*/
	
	@Override
    public Mono<ResponseEntity<Map<String, Object>>> saveCustomer(@Validated Customer customer) {
        Map<String, Object> response = new HashMap<>();
        return pcRepo.findByIdentityNumber(customer.getIdentityNumber()).collectList().flatMap( c -> {
            if(c.isEmpty()){
            	customer.setCustomerType("PERSONAL");
                return pcRepo.save(customer).flatMap(cx ->{
                    log.info("usuario agregado");
                    response.put("Usuario", cx);
                    response.put("Message", "Usuario registrado con éxito");
                    return Mono.just(new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK));
                });
            }else{
                log.info("ya esxiste un usuario agregado con el mismo dni");
                response.put("Message", "Existe un usuario con el mismo DNI");
                response.put("Note", "Verifique sus Datos");
                return Mono.just(new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST));
            }
        });
    }
	
	@Override
	public Mono<Void> delete(String id) {
		return pcRepo.deleteById(id);

	}
	
	/*@Override
    public Mono<Void> update(String id, Customer customer) {
        return this.pcRepo.findById(id)
                .flatMap(customer1 -> {
                	customer.setId(id);
                    return save(customer);
                })
                .switchIfEmpty(Mono.empty());
    }*/
	
	@Override
	public Flux<Customer> findAll() {
		return this.pcRepo.findAll();
    }
	
	@Override
	public Mono<Customer> findById(String id) {
		return pcRepo.findById(id);
	}

	@Override
	public Flux<Customer> findByIdentity(String identityNumber) {
		return this.pcRepo.findByIdentityNumber(identityNumber);
	}

	@Override
	public Flux<Customer> findByIdentityType(String identityType) {
		return this.pcRepo.findByIdentityType(identityType);
	}
	
	@Override
	public Flux<Customer> findByCostumerTypes(String type) {
		return this.pcRepo.findByCustomerType(type);
	}
	
}