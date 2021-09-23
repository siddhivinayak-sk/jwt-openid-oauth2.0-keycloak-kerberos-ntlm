package com.sk.kerberos.client;

import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KerberosClientApp {

	static {
		System.setProperty("java.security.krb5.conf",
				Paths
				.get("C:/sandeep/code/source/others/kerbores-server/krb-workdir/krb5.conf")
				.normalize()
				.toAbsolutePath()
				.toString());
		System.setProperty("sun.security.krb5.debug", "true");
		System.setProperty("sun.security.spnego.debug", "true");
		// disable usage of local kerberos ticket cache
		System.setProperty("http.use.global.creds", "false");
	}

	public static void main(String[] args) {
		SpringApplication.run(KerberosClientApp.class, args);
	}
}