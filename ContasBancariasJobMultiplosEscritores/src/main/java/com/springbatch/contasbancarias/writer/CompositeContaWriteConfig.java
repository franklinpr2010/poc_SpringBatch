package com.springbatch.contasbancarias.writer;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Conta;


/**
 * Esse é um escritor composto com flatfile e jdbc 
 * @author fnpa
 *
 */
@Configuration
public class CompositeContaWriteConfig {
	@Bean
	public CompositeItemWriter<Conta> compositeContaWriter(FlatFileItemWriter<Conta> fileContaWriter,
			JdbcBatchItemWriter<Conta> jdbcContaWriter) {
		return new CompositeItemWriterBuilder<Conta>()
				.delegates(fileContaWriter, jdbcContaWriter).build();
	}
}
