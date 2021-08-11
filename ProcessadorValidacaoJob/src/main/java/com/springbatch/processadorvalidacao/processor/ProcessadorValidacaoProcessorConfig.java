package com.springbatch.processadorvalidacao.processor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.processadorvalidacao.dominio.Cliente;

@Configuration
public class ProcessadorValidacaoProcessorConfig {
	private Set<String> emails = new HashSet<>();
	
	
	
	/**
	 * Aqui vai validar o dado do validador no model
	 * Ex. Cliente tem @NotNull, @Regex
	 * @return
	 * Ele vai estar na chamada do step
	 * No caso abaixo está fazendo um processor composto
	 * @throws Exception 
	 */
	@Bean
	public ItemProcessor<Cliente, Cliente> procesadorValidacaoProcessor() throws Exception {
		BeanValidatingItemProcessor<Cliente> processor = new BeanValidatingItemProcessor<>();
		//Colocando no delegate as duas validações
		return new CompositeItemProcessorBuilder<Cliente, Cliente>().delegates(beanValidatingProcess(), emailValidatingProcessor()).build();
		
	}
	
	/**
	 * Primeiro Validador, valida todos que tem validator no model
	 * @return
	 * @throws Exception 
	 */
	private BeanValidatingItemProcessor<Cliente> beanValidatingProcess() throws Exception {
		BeanValidatingItemProcessor<Cliente> processor = new BeanValidatingItemProcessor();
		//para o batch continuar mesmo que lance uma exception
		processor.setFilter(true);
		processor.afterPropertiesSet();
		return processor;
	}
	
	
	/**
	 * Valida se o email já foi salvo no banco
	 * @return
	 */
	private ValidatingItemProcessor<Cliente> emailValidatingProcessor() {
	    ValidatingItemProcessor<Cliente> processor = new ValidatingItemProcessor<Cliente>();
	    processor.setValidator(validator());
		return processor;
	}

	
	/**
	 * Verificar se o arquivo possui itens duplicados
	 * @return
	 */
	private Validator<? super Cliente> validator() {
		// TODO Auto-generated method stub
		return new Validator<Cliente>() {

			@Override
			public void validate(Cliente value) throws ValidationException {
				if(emails.contains(value.getEmail())) {
					throw new ValidationException(String.format("O cliente %s já foi processado", value.getEmail()));
				}
				emails.add(value.getEmail());
				
			}
		};
	}
}
