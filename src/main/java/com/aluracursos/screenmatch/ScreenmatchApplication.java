package com.aluracursos.screenmatch;


import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obtenerDatos("https://omdbapi.com/?t=game+of+thrones&apikey=4fc7c187");
		//	System.out.println("Este hola mundo viene desde el CommandLineRunner con Spring Boot!");
	//	var json = consumoAPI.obtenerDatos("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);

		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);

        //convertir los datos del episodio a un objeto DatosEpisodio
        json= consumoAPI.obtenerDatos("https://omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=4fc7c187");
        DatosEpisodio episodios = conversor.obtenerDatos(json, DatosEpisodio.class);
        System.out.println(episodios);

		//crear lista
		List<DatosTemporadas> temporadas = new ArrayList<>();
		//se crea un for para la busqueda de a listas
		for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
			json= consumoAPI.obtenerDatos("https://omdbapi.com/?t=game+of+thrones&Season="+i+"&apikey=4fc7c187");
			//convertir los datos
			var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
			temporadas.add(datosTemporadas);
		}
		temporadas.forEach(System.out::println);
	}
}
