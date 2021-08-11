package com.springbatch.processadorclassifier.processor;



import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.processadorvalidacao.dominio.Cliente;

/**
 * Validador com classificador depende do objeto lido, vamos supor um cliente
 * com linhas diferentes, dependendo do objeto vai criar regras especificas daquele objeto
 * 
 * EX: 
 * 0,João,Silva,32,joao@test.com 
 * 1,t1c1,Cadeado,50.80
 * 1,t2c1,TV,1500 
 * 0,Maria,Silva,30,maria@test.com 
 * 1,t1c2,Geladeira duplex,2000
 * 1,t2c2,Mesa,4500 
 * 1,t3c2,Sofá,1099,99 
 * 0,José,Silva,20,jose@test.com
 * 1,t1c3,Comida no Ifood,500.45 
 * 0,Cliente,Sem Transações,20,semtransacoes@test.com
 * 
 * @author fnpa
 *
 */
@Configuration
public class ProcessadorClassifierProcessorConfig {
	
	@Bean
	public ItemProcessor processadorClassifierProcessor() {
		return  new ClassifierCompositeItemProcessorBuilder<>()
				.classifier(classifier()).build();
	}

	private Classifier<? super Object, ItemProcessor<?, ? extends Object>> classifier() {
		return new Classifier<Object, ItemProcessor<?,? extends Object>>() {

			@Override
			public ItemProcessor<?, ? extends Object> classify(Object object) {
				if(object instanceof Cliente) {
					return new ClienteProcessor();
				} else {
					return new TransacaoProcessor();
				}
			}
		};
	}

}
