package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.springframework.boot.web.servlet.server.Session.SessionTrackingMode.URL;

public class Principal {

    //para leer el valor de la instancia desde el teclado
    private Scanner teclado = new Scanner(System.in);
    //se crea instancia de clase consumo api
    private ConsumoAPI consumoApi = new ConsumoAPI();

    private final String URL_BASE = "https://omdbapi.com/?t=";

    private final String API_KEY = "&apikey=4fc7c187";

    // se crea un conversor de datos para traer el conviertedatos del screenmatch
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu(){
      //  System.out.println("Bienvenido a Screenmatch");
        System.out.println("Escribe el nombre de la serie que deseas buscar:");
      //busca los datos generaloes de la serie
        var nombreSerie = teclado.nextLine();
        //para obtener los datos de la serie desde la url
        var json = consumoApi.obtenerDatos (URL + nombreSerie.replace(" " , "+") + API_KEY);
        //se crea la variable para converit los datos
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Busca los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
        //se crea un for para la busqueda de a listas
        for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
            json = consumoApi.obtenerDatos(URL + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            //convertir los datos
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
      //  temporadas.forEach(System.out::println);
        
        //mostrar solo el titulo de los episodios para las temporadas
        /**
        for (int i = 0; i < datos.totalDeTemporadas() ; i++) {
            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size() ; j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
                
            }
        }*/
            // funciones lamda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

            


        //se imprime el valor de json
       // System.out.println(json);

    }
}

