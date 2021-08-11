package com.springbatch.processadorvalidacao.dominio;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class Cliente {
	@NotNull
	@Size(min=1, max=100)
	@Pattern(regexp = "^([a-zA-Z0-9]+\\s)*[a-zA-Z0-9]+$", message = "Nome inválido")
	private String nome;
	@NotNull
	@Range(min=18, max = 200)
	private Integer idade;
	@Size(min = 1, max = 50)
	@Pattern(regexp = "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b", message = "E-mail inválido")
	private String email;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Cliente{" +
                "nome='" + nome + "'" +
                ", idade='" + idade + "'" +
                ", email='" + email + "'" +
                '}';
	}
}
