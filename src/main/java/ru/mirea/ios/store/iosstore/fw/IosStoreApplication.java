package ru.mirea.ios.store.iosstore.fw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.mirea.ios.store.iosstore"})
@EnableMongoRepositories(basePackages = "ru.mirea.ios.store.iosstore.adapter.persistence")
public class IosStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(IosStoreApplication.class, args);
	}

}
