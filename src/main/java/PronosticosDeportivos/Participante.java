package PronosticosDeportivos;

import static configuracion.ConectarDB.DB_URL;
import static configuracion.ConectarDB.PASS;
import static configuracion.ConectarDB.USER;

import java.sql.*;
import java.util.ArrayList;
import PronosticosDeportivos.ResultadoEnum.Resultado;
import excepciones.ExcepcionIntegridadDeDatos;

public class Participante {

	private String nombre;
	private ArrayList<Pronosticos> pronosticos = new ArrayList<Pronosticos>();
	public static ArrayList<Participante> participantes;

	public Participante(String nombre) {
		this.nombre = nombre;

	}

	public ArrayList<Pronosticos> getPronosticos() {
		return this.pronosticos;
	}

	public String getNombre() {
		return nombre;
	}

	public void agregarPronostico(Pronosticos pronostico) {
		pronosticos.add(pronostico);
	}

	// ----------------------------------------------------------------------------------
	// Lee la Base de datos de pronosticos. Crea los participantes y le asigna sus
	// listas de pronosticos

	public static void ArmarListaDeParticipantes(ArrayList<Partido> listaDePartidos) throws ExcepcionIntegridadDeDatos {
		participantes = new ArrayList<Participante>();

		Connection conexion = null;
		Statement consulta = null;
		boolean hayErrores = false;

		try {
			conexion = DriverManager.getConnection(DB_URL, USER, PASS);
			consulta = conexion.createStatement();
			String sql;
			sql = "SELECT * FROM pronosticos.pronosticos";

			ResultSet resultadoConsulta = consulta.executeQuery(sql);

			while (resultadoConsulta.next()) {

				String nombre = resultadoConsulta.getString("Participante");
				Participante esteParticipante = existeParticipante(nombre);

				String idPartido = Integer.toString(resultadoConsulta.getInt("IdPartido"));
				Partido estePartido = null;
				for (Partido partido : listaDePartidos) {
					if (partido.getIdPartido().equals(idPartido)) {
						estePartido = partido;
					}
				}
				if (estePartido == null) {
					System.out.println("Error en pronostico registro numero: " + resultadoConsulta.getRow()+" -El Id de Partido es incorrecto");
					hayErrores = true;
					continue;
				}

				for (Pronosticos pronostico : esteParticipante.getPronosticos()) {
					if (pronostico.getPartido().getIdPartido().equals(idPartido)) {
						System.out.println("Pronostico repetido en registro numero: " + resultadoConsulta.getRow());
						hayErrores = true;
						continue;
					}
				}

				Resultado resultadoEquipo1 = null;

				if (resultadoConsulta.getString("Gana1").equalsIgnoreCase("x")) {
					resultadoEquipo1 = Resultado.GANADOR;

				} else if (resultadoConsulta.getString("Empata").equalsIgnoreCase("x")) {
					resultadoEquipo1 = Resultado.EMPATE;

				} else if (resultadoConsulta.getString("Gana2").equalsIgnoreCase("x")) {
					resultadoEquipo1 = Resultado.PERDEDOR;
				} else {
					System.out.println("Error en pronostico registro numero: " + resultadoConsulta.getRow());
					hayErrores = true;
					continue;
				}

				Pronosticos nuevoPronostico = new Pronosticos(estePartido, resultadoEquipo1);
				esteParticipante.agregarPronostico(nuevoPronostico);
			}

			resultadoConsulta.close();
			consulta.close();
			conexion.close();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			try {
				if (consulta != null)
					consulta.close();
			} catch (SQLException se2) {
			}
			try {
				if (conexion != null)
					conexion.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		if (hayErrores == true) {
			throw new ExcepcionIntegridadDeDatos();
		}

	}

	// -----------------------------------------------------------------------------
	// Verifica si existe un participante, si no existe lo crea

	public static Participante existeParticipante(String nombre) {

		for (Participante participante : Participante.participantes) {
			if (participante.getNombre().equals(nombre)) {
				return participante;
			}
		}

		Participante nuevoParticipante = new Participante(nombre);
		participantes.add(nuevoParticipante);

		return nuevoParticipante;
	}

}
