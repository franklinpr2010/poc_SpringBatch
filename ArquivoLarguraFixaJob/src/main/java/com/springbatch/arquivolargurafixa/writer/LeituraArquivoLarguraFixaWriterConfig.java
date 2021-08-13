package com.springbatch.arquivolargurafixa.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.springbatch.arquivolargurafixa.dominio.Cliente;
/**
 * Escrita e arquivo flat
 * @author fnpa
 *
 */

@Configuration
public class LeituraArquivoLarguraFixaWriterConfig {

	@StepScope //como está obtendo dados do jobs parameters, tem que colocar o @stepscope
	@Bean
	public FlatFileItemWriter<Cliente> escritaArquivoLarguraFixaWriter(@Value("#{jobParameters['arquivoClienteSaida']}") Resource arquivoClienteSaida) {
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("escritaArquivoLarguraFixaWriter")
				.resource(arquivoClienteSaida)
				//na saida tem que ser format, ou seja criar o arquivo de saida
				.formatted()
				.format("%-9s %-9s %-2s %-19")
				.names(new String[] {"nome", "sobrenome", "idade", "email"})
				.build();
		
	}
}
