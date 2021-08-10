package com.springbatch.arquivomultiplosformatos.reader;



import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MultiplosArquivosClienteTransacaoReaderConfig {
	
	/**
	 * Para aceitar multiplos arquivos
	 * @param arquivosClientes
	 * @return
	 */
	@Bean
	public MultiResourceItemReader<Object> multiplosArquivosClienteTransacaoReader(
			@Value("#jobParameters['arquivosCliente']") Resource[] arquivosClientes
			, FlatFileItemReader<Object> leituraArquivoMultiplosFormatosReader) {
				return new MultiResourceItemReaderBuilder<>()
						.name("multiplosArquivosClienteTransacaoReader")
						.resources(arquivosClientes)
						.delegate(new ArquivoClienteTransacaoReader(leituraArquivoMultiplosFormatosReader)).build();
		
	}

	
	
}
