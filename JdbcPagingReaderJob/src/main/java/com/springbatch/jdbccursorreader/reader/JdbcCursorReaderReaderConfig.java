package com.springbatch.jdbccursorreader.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.springbatch.jdbcpagingreader.dominio.Cliente;

/**
 * Exemplo pelo cursor
 * @author fnpa
 *
 */
@Configuration
public class JdbcCursorReaderReaderConfig {
	@Bean
	public JdbcCursorItemReader<Cliente> jdbcPagingReader(
			@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Cliente>()
				.name("jdbcCursorReader")
				//ler o banco de dados da classe DataSource config
				.dataSource(dataSource)
				.sql("select * from cliente")
				.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class)).build();
	}
}
