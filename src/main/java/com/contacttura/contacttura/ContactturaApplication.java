package com.contacttura.contacttura;

import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.contacttura.contacttura.model.Contactura;
import com.contacttura.contacttura.repository.ContacturaRepository;

@SpringBootApplication
public class ContactturaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactturaApplication.class, args);
	}

		@Bean
		CommandLineRunner init(ContacturaRepository repository) {
			return args -> {
				// opcional
				repository.deleteAll();
				LongStream.range(1,  10)
				.mapToObj(i -> {
					Contactura c = new Contactura();
					c.setName("Contactura User"  + i);
					c.setEmail("contactira" + i + "@gmail.com");
					c.setPhone("(081) 9" +  i + i + i + i + "-" + i + i + i + i);
					return c;
				})
				.map(m -> repository.save(m))
				.forEach(System.out::println);
			};
		}

}
