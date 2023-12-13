package ru.pin120.demoSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;

@SpringBootApplication
public class DemoSpringApplication {
//	private static Connection connection;

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringApplication.class, args);
//		DBHelper.connect();
	}

//	@Override
//	protected void finalize() throws Throwable {
//		DBHelper.disconnect();
//		super.finalize();
//	}
	//static public Connection getConnection(){return connection;}
	

}
