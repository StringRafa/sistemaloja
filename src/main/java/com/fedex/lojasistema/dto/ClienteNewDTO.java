package com.fedex.lojasistema.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fedex.lojasistema.services.validation.ClienteInsert;

import lombok.Data;
import lombok.NoArgsConstructor;

@ClienteInsert
@Data
@NoArgsConstructor
public class ClienteNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String cpfOuCnpj;

	private Integer tipo;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String senha;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String telefone1;

	private String telefone2;
	private String telefone3;


}
