package PronosticosDeportivos;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import PronosticosDeportivos.ResultadoEnum.Resultado;
import excepciones.ExcepcionIntegridadDeDatos;

public class Partido {

	private String idFase;
	private String idRonda;
	private String IdPartido;
	private Equipo equipo1;
	private Equipo equipo2;
	private int golesEquipo1;
	private int golesEquipo2;
	public static ArrayList<Partido> listaDePartidos = new ArrayList<Partido>();

	public Partido(String idFase, String idRonda, String IdPartido, Equipo equipo1, Equipo equipo2,
			int golesEquipo1,int golesEquipo2) {

		this.idFase = idFase;
		this.idRonda = idRonda;
		this.IdPartido = IdPartido;
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.golesEquipo1 = golesEquipo1;
		this.golesEquipo2 = golesEquipo2;
	}

	public String getIdPartido() {
		return IdPartido;
	}

	public Equipo getEquipo1() {
		return equipo1;
	}

	public Equipo getEquipo2() {
		return equipo2;
	}

	public int getGolesEquipo1() {
		return golesEquipo1;
	}

	public int getGolesEquipo2() {
		return golesEquipo2;
	}

	public String getFase() {
		return idFase;
	}
	
	public String getIdRonda() {
		return idRonda;
	}
	
	
	// --------------------------------------------------------------------
	// Lee el archivo de los partidos y genera los equipos, rondas, fases y partidos
	public static ArrayList<Partido> ArmarListaPartidos(String archivo) throws ExcepcionIntegridadDeDatos{
		
		boolean hayErrores = false;
		
		try {

			CSVReader csvReader = new CSVReader(new FileReader(archivo));
			String[] fila = null;
			String[] estaFila = null;
			int numeroFila = 1;
			while ((fila = csvReader.readNext()) != null) {
				if (numeroFila == 1) {
					numeroFila = numeroFila + 1;

				} else {
					
					estaFila = fila[0].split(";");
					
					String fase = estaFila[10];
					String idRonda = estaFila[0];
					String idPartido = estaFila[1];
					if (!estaFila[5].matches("[0-9]+") | !estaFila[6].matches("[0-9]+") ) {
															
						System.out.println("Error en archivo Partidos en fila numero:" + (numeroFila-1) + 
								" La cantidad de goles no es un numero entero");  	
						hayErrores = true;
						numeroFila = numeroFila + 1;
						continue;
					}
						if (!(estaFila.length == 11)) {
							
							System.out.println("Error en archivo Partidos en fila numero:" + (numeroFila-1)+" Cantidad de campos incorrecta");  	
							hayErrores = true;
							numeroFila = numeroFila + 1;	
						
						continue;
					}

					int golesEquipo1 = Integer.parseInt(estaFila[5]);
					int golesEquipo2 = Integer.parseInt(estaFila[6]);

					Equipo equipo1 = Equipo.existeEquipo(estaFila[3], estaFila[4]);
					Equipo equipo2 = Equipo.existeEquipo(estaFila[8], estaFila[9]);

					Partido estePartido = existePartido(fase, idRonda, idPartido, equipo1, equipo2, golesEquipo1, golesEquipo2);
					
					Ronda estaRonda = Ronda.existeRonda(idRonda);
					estaRonda.agregarPartido(estePartido);
					
					Fase estaFase = Fase.existeFase(fase);
					estaFase.agregarRonda(estaRonda);
					numeroFila = numeroFila + 1;
				}
			}
			
			} catch (IOException e) {
			System.out.println("Error:" + e);
		} catch (CsvValidationException e) {
			e.printStackTrace();
		}

		if (hayErrores == true) {
			ExcepcionIntegridadDeDatos e = new ExcepcionIntegridadDeDatos();
			throw e;		
		}
	
		return listaDePartidos;
	}

	// ----------------------------------------------------------------------------
	// verifica si existe un partido dado, si no existe lo crea.
	
	public static Partido existePartido(String idFase, String idRonda, String idPartido, Equipo equipo1,
			Equipo equipo2, int golesEquipo1,int golesEquipo2) {

		for (Partido partido : listaDePartidos) {
			if (partido.getIdPartido().equals(idPartido)) {
				return partido;
			}
		}
		
			Partido nuevoPartido = new Partido(idFase, idRonda, idPartido, equipo1, equipo2, golesEquipo1, golesEquipo2);
			listaDePartidos.add(nuevoPartido);
			return nuevoPartido;
	}

	// --------------------------------------------------------------------
		// Establece el resultado del equipo1 para este partido.
		
		public Resultado Resultadoequipo1() {
			if (this.golesEquipo1 > this.golesEquipo2) {
				return Resultado.GANADOR;

			} else if (this.golesEquipo1 < this.getGolesEquipo2()) {
				return Resultado.PERDEDOR;
			} else
				return Resultado.EMPATE;
		}


}
