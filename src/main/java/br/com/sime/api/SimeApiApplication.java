package br.com.sime.api;

import br.com.sime.api.config.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SimeApiApplication {
	public static void main(String[] args) {
		// Carrega variáveis de ambiente do arquivo .env
		EnvLoader.loadEnv();
		SpringApplication.run(SimeApiApplication.class, args);
	}
}
