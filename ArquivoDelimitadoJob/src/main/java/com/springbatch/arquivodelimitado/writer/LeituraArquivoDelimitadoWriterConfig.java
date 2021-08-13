package com.springbatch.arquivodelimitado.writer;



import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.springbatch.arquivodelimitado.dominio.Cliente;

@Configuration
public class LeituraArquivoDelimitadoWriterConfig {
	@Bean
	public FlatFileItemWriter<Cliente> leituraArquivoDelimitadoWriter(
			@Value("#{jobParameters[arquivoClientesSaida]}") Resource arquivoClientesSaida) {
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("leituraArquivoDelimitadoWriter")
				.resource(arquivoClientesSaida)
				.delimited()
				//Separado por virgula
				.delimiter(",")
				.names("nome", "sobrenome", "idade", "email").build();
		
	}
}
