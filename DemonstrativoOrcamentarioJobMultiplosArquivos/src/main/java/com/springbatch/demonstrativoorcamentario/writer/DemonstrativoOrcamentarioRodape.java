package com.springbatch.demonstrativoorcamentario.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.List;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;

@Component
public class DemonstrativoOrcamentarioRodape implements FlatFileFooterCallback {
	
	private double totalGeral = 0.0;
	
	@Override
	public void writeFooter(Writer writer) throws IOException {
	   System.out.println(String.format("\t\t\t\t\t\t\t  Total: %s", NumberFormat.getCurrencyInstance().format(totalGeral)));
	   System.out.println(String.format("\t\t\t\t\t\t\t  Código de Autenticação: %s" , "fkyew6868fewjfhjjewf"));
		
	}
	
	/*
	 * Vai somar os lançamentos antes de escrever o arquivo
	 */
	@BeforeWrite
	public void beforeWrite(List<GrupoLancamento> grupos) {
		for (GrupoLancamento grupoLancamento : grupos) {
			totalGeral+= grupoLancamento.getTotal();
		}
	}
	
	/**
	 * Após o Chunk vai zerar o contador
	 */
	@AfterChunk
	public void afterChunk(ChunkContext context) {
		totalGeral = 0.0;
	}
	
}
