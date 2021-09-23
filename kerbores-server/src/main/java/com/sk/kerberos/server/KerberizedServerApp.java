package com.sk.kerberos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;

@SpringBootApplication
public class KerberizedServerApp {

	static {
		System.setProperty("java.security.krb5.conf",
				Paths
				.get("C:\\sandeep\\code\\source\\others\\kerbores-server\\krb-workdir\\krb51.conf")
				.normalize()
				.toAbsolutePath()
				.toString());
		System.setProperty("sun.security.krb5.debug", "true");
		System.setProperty("sun.security.spnego.debug", "true");
		
	}

	public static void main(String[] args) {

		SpringApplication.run(KerberizedServerApp.class, args);
	}
}