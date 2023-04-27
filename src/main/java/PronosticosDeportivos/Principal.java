package PronosticosDeportivos;

import java.util.ArrayList;
import java.util.Arrays;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import configuracion.Configuracion;
import excepciones.ExcepcionIntegridadDeDatos;
import puntuacion.Puntuacion;

public class Principal {

	public static void main(String[] args) throws ExcepcionIntegridadDeDatos {

		String archivo;
		String archivoConfig;
		
	if(args.length==0) {
			
		archivo = "Partidos.csv";
		archivoConfig = "Configuracion.csv";	
		}else {
		archivo = args[0];
		archivoConfig = args[1];
		}
		
	if(args.length==1) {
		System.out.println("Debe inclur la ruta del Archivo de Partidos y de Configuracion");
		throw new ExcepcionIntegridadDeDatos();
	}
		
		ArrayList<Partido> listaDePartidos;
		
		try {		
		Configuracion.leerConfiguracion(archivoConfig);
		listaDePartidos = Partido.ArmarListaPartidos(archivo);
		Participante.ArmarListaDeParticipantes(listaDePartidos);
		Puntuacion.imprimirPuntajes();
				      	
	    	
		}catch (ExcepcionIntegridadDeDatos e) {
			
			System.out.println("----------------------------------------------------------------------------------");
			System.out.println("ATENCION: Corrija los errores y vuelva a intentar");
			System.out.println("----------------------------------------------------------------------------------");
		}
	    }
	}


