package com.bootcamp.microservices.app.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="costumer")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = -7228947235712205698L;

	@Id
	private String id = UUID.randomUUID().toString().substring(0, 10);
	
	@NotBlank(message="El nombre no puede estar vacio")
	private String name;
	
	@NotBlank(message="El apellido no puede estar vacio")
	private String lastName;
	
	@NotBlank(message="tipo de documento no puede estar vacio")
	private String identityType;
	
	@Indexed(unique=true)
	@NotBlank(message="numero de documento no puede estar vacio")
	private String identityNumber;
	
	@NotBlank(message="celular no puede estar vacio")
	private String phoneNumber;
	
	@NotBlank(message="email no puede estar vacio")
	private String email;
	
	@NotBlank(message="Tipo de cliente no puede estar vacio")
	private String customerType;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createAt;
	
	private LocalDateTime modifiedAt;
	
	
}
