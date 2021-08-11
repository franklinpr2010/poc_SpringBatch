package com.springbatch.processadorclassifier.processor;

import org.springframework.batch.item.ItemProcessor;

import com.springbatch.processadorvalidacao.dominio.Cliente;
import com.springbatch.processadorvalidacao.dominio.Transacao;

public class TransacaoProcessor implements ItemProcessor<Transacao, Transacao>{
	//Processamento da regra de negócio
	@Override
	public Transacao process(Transacao item) throws Exception {
		System.out.println(String.format("\nAplicando as regras de negócio na transação %s", item.getDescricao()));
		return item;
	}



}
