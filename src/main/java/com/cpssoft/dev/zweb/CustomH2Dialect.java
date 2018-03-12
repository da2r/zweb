package com.cpssoft.dev.zweb;

import java.sql.Types;

import org.hibernate.dialect.H2Dialect;

public class CustomH2Dialect extends H2Dialect {

	public CustomH2Dialect() {
		super();
		
		// registerColumnType( Types.VARCHAR, "varchar($l)" );
		registerColumnType( Types.VARCHAR, "varchar" );
	}
}
