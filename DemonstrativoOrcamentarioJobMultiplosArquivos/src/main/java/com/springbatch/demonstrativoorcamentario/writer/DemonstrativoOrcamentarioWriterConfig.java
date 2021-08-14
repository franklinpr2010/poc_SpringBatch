package com.springbatch.demonstrativoorcamentario.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {
	
	
	/**
	 * Para utilizar escrita em vários arquivos
	 * @param demonstrativosOrcamentarios
	 * @param demonstrativoOrcamentarioWriter
	 * @return
	 */
	@StepScope
	@Bean
	public MultiResourceItemWriter<GrupoLancamento> multiDemonstrativoOrcamentarioWriter(
			@Value("#{jobParameters[demonstrativosOrcamentarios]}") Resource demonstrativosOrcamentarios,
			FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter) {
		return new MultiResourceItemWriterBuilder<GrupoLancamento>()
				.name("multiDemonstrativoOrcamentarioWriter")
				.resource(demonstrativosOrcamentarios)
				.delegate(demonstrativoOrcamentarioWriter)
				//Gerar um arquivo com sufixo
				.resourceSuffixCreator(suffixCreator())
				//A cada grupo lançamento vai criar um arquivo, vai gerar 6 arquivos
				//porque tem 6 itens lançamentos
				.itemCountLimitPerResource(1)
				.build();
	}
	
	
	private ResourceSuffixCreator suffixCreator() {
		return new ResourceSuffixCreator() {
			
			/**
			 * Indice dizendo que é o primeiro arquivo
			 */
			@Override
			public String getSuffix(int index) {
				return index + ".txt";
			}
		};
	}


	@StepScope
	@Bean
	public FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentoWriter(
			@Value("#{jobParameters['demonstrativoOrcamentario']}") Resource demonstrativoOrcamentario,
			DemonstrativoOrcamentarioRodape rodapeCallback) {
		return new FlatFileItemWriterBuilder<GrupoLancamento>()
				.name("demonstrativoOrcamentoWriter")
				.resource(demonstrativoOrcamentario)
				//Vai definir a lógica de agregação da linha
				.lineAggregator(lineAggregator())
				.headerCallback(cabecalhoCAllback())
				.footerCallback(rodapeCallback).build();
				
				
				
	}

	private FlatFileHeaderCallback cabecalhoCAllback() {
		
		return new FlatFileHeaderCallback() {
			
			/**
			 * Metodo que vai escrever o cabeçalho
			 */
			@Override
			public void writeHeader(Writer writer) throws IOException {
				 writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
				 writer.append(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t\t HORA: %s", new SimpleDateFormat("HH:MM").format(new Date())));
			     writer.append(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO"));
			     writer.append(String.format("-----------------------------------------------------------------------------"));
			     writer.append(String.format("CODIGO NOME VALOR"));
			     writer.append(String.format("\t Data Descricao Valor"));
			     writer.append(String.format( "----------------------------------------------------------------------------"));
				
			}
		};
	}

	/**
	 * Vai ter que retornar um agregador de linha para transformar o arquivo 
	 * no formato que tem que ser
	 * * @return
	 */
	private LineAggregator<GrupoLancamento> lineAggregator() {
		// TODO Auto-generated method stub
		return new LineAggregator<GrupoLancamento>() {
			@Override
			public String aggregate(GrupoLancamento grupoLancamento) {
				String formatGtupoLancamento = String.format("[%d] %s - %s\n", grupoLancamento.getCodigoNaturezaDespesa(), grupoLancamento.getDescricaoNaturezaDespesa(), NumberFormat.getCurrencyInstance().format(grupoLancamento.getTotal()));
				
				StringBuilder strBuilder = new StringBuilder();
				for (Lancamento lancamento : grupoLancamento.getLancamentos()) {
					strBuilder.append(String.format("\t [%s] %s - %s\n", new
							  SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()),
							  lancamento.getDescricao(),
							  NumberFormat.getCurrencyInstance().format(lancamento.getValor())));

					} 
				String formatLancamento = strBuilder.toString();
				return formatGtupoLancamento + formatLancamento;
				}
			
				
				
				
			
			
		};
	}
	
	
}
