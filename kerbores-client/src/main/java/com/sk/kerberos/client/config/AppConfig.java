package com.sk.kerberos.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(KerberosConfig.class)
public class AppConfig {

}