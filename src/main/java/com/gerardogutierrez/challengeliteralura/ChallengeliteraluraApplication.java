package com.gerardogutierrez.challengeliteralura;

import com.gerardogutierrez.challengeliteralura.principal.Principal;
import com.gerardogutierrez.challengeliteralura.repository.IAutorLibroRepository;
import com.gerardogutierrez.challengeliteralura.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeliteraluraApplication implements CommandLineRunner {
	@Autowired
	private ILibroRepository repositoryLibro;
	@Autowired
	private IAutorLibroRepository repositoryAutor;

	public static void main(String[] args) {
		SpringApplication
				.run(ChallengeliteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryLibro, repositoryAutor);
		principal.muestraElMenu();
	}
}