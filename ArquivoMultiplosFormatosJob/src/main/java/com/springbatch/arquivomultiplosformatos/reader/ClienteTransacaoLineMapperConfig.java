package com.springbatch.arquivomultiplosformatos.reader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;

@Configuration
public class ClienteTransacaoLineMapperConfig {

	/**
	 * metodo que vai retornar um linner mapper, dependendo do padrão ele vai escolher 
	 * qual line mapper vai ser aplicado
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public PatternMatchingCompositeLineMapper lineMapper() {
		PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper<>();
		//pegam as linhas e divide em palavras
		lineMapper.setTokenizers(tokenizers());
		//pega essas palavras e mapeiam para um objeto de dominio
		lineMapper.setFieldSetMappers(fieldSetMappers());
		return lineMapper;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, FieldSetMapper> fieldSetMappers() {
		Map<String, FieldSetMapper> fieldSetMappers = new HashMap<>();
		
		fieldSetMappers.put("0*", fieldSetMapper(Cliente.class));
		fieldSetMappers.put("1*", fieldSetMapper(Transacao.class));
		return fieldSetMappers;
	}

	/**
	 * 
	 * @param classe
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private FieldSetMapper fieldSetMapper(Class classe) {
		BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(classe);
		return fieldSetMapper;
	}

	/**
	 * Mapa vai ter uma tokenização do programa que está usando para tokenizar a linha
	 * para transformar em palavras
	 * @return
	 */
	private Map<String, LineTokenizer> tokenizers() {
		Map<String, LineTokenizer> tokenizers = new HashMap<>();
		//o cliente tem tipo zero
		tokenizers.put("0*", clienteLineTokenizer());
		//a transação tem o número 1
		tokenizers.put("1*", transacaoLineTokenizer());
		return tokenizers;
	}

	/**
	 * Vai criar os tokenarers de fato
	 * @return
	 */
	private LineTokenizer clienteLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("nome", "sobrenome", "idade", "email");
		//o zero representa o tipo da linha
		lineTokenizer.setIncludedFields(1, 2, 3, 4);
		return lineTokenizer;
	}
	
	
	/**
	 * Vai criar os tokenarers de fato
	 * @return
	 */
	private LineTokenizer transacaoLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("id", "descricao", "valor");
		lineTokenizer.setIncludedFields(1, 2, 3);
		return lineTokenizer;
	}
}
