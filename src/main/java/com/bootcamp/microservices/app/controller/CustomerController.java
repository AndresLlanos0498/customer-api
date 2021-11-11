 package com.bootcamp.microservices.app.controller;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.bootcamp.microservices.app.dao.CustomerRepository;
import com.bootcamp.microservices.app.models.Customer;
import com.bootcamp.microservices.app.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	public static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@GetMapping
	private Flux<Customer> getAll(){
		return customerRepo.findAll();
	}
	
	@GetMapping("/identity/{identityType}")
	private Flux<Customer> getAllIdentityTypes(@PathVariable String identityType){
		return customerRepo.findByIdentityType(identityType);
	}
	
	@GetMapping("/type/{type}")
	private Flux<Customer> getAllIByTypes(@PathVariable String type){
		return customerRepo.findByCustomerType(type);
	}
	
	@GetMapping("/{id}")
	private Mono<Customer> findById(@PathVariable String id){
		log.info("Customer: Service Search By Id Customer");
		return customerRepo.findById(id);
	}
	
	@GetMapping("/by/{identityNumber}")
	private Flux<Customer> findByIdentityNumber(@PathVariable String identityNumber){
		return customerRepo.findByIdentityNumber(identityNumber);
	}
	
	/*@GetMapping("/identity/{identityType}")
	private Mono<ResponseEntity<Customer>> findByIdentityType(@PathVariable String identityType){
		return customer.findByIdentityType(identityType)
				.flatMap(p -> Mono.just(ResponseEntity.ok(p))
						.switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
	}
	
	@GetMapping("/type/{type}")
	private Mono<ResponseEntity<Customer>> findByCustomerType(@PathVariable String type){
		return customer.findByCustomerType(type)
				.flatMap(p -> Mono.just(ResponseEntity.ok(p))
						.switchIfEmpty(Mono.just(ResponseEntity.notFound().build())));
	}*/
	
	@PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> save(@RequestBody @Validated Customer customer){
		if(customer.getCreateAt()==null) {
			customer.setCreateAt(LocalDateTime.now());
		}
        Map<String, Object> respuesta = new HashMap<>();
        return service.saveCustomer(customer).onErrorResume(t->{
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e-> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "EL campo "+fieldError.getField() + " "+fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("errors", list);
                        respuesta.put("timestamp", LocalDateTime.now());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                        
                    });
        });
    }
	
	@DeleteMapping("/delete/{id}")
	private Mono<Void> delete(@PathVariable String id) {
		return customerRepo.deleteById(id);
	}
	
	@PutMapping("/update")
	private Mono<Customer> update(@RequestBody Customer pcustomer){
		if(pcustomer.getModifiedAt()==null) {
			pcustomer.setModifiedAt(LocalDateTime.now());
		}
		return customerRepo.save(pcustomer);
	}

}
