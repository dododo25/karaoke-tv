package com.dododo.kt;

import com.dododo.kt.holder.SessionsHolder;
import com.dododo.kt.holder.TokensHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class KaraokeTVApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaraokeTVApplication.class, args);
	}

	@Bean
	public SessionsHolder sessionsHolder() {
		return new SessionsHolder();
	}

	@Bean
	public TokensHolder tokensHolder() throws NoSuchAlgorithmException {
		return new TokensHolder();
	}
}
