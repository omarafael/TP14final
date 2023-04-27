package puntuacion;

import java.util.ArrayList;
import java.util.Collections;

import PronosticosDeportivos.Equipo;
import PronosticosDeportivos.Fase;
import PronosticosDeportivos.Participante;
import PronosticosDeportivos.Partido;
import PronosticosDeportivos.Pronosticos;
import PronosticosDeportivos.Ronda;
import configuracion.Configuracion;

public class Puntuacion {

	// ----------------------------------------------------------------------
	// Cuenta el total de aciertos de un participante
	
	public static int ContarAciertos(Participante participante) {

		int totalAciertos = 0;
		for (Pronosticos pronostico : participante.getPronosticos()) {
			totalAciertos = totalAciertos + pronostico.Puntos();
		}

		return totalAciertos;
	}
	// ----------------------------------------------------------------------
	// Cuenta la cantidad de equipos con todos los pronosticos de una fase acertados por un participante

	public static int BonusEquipoFase(Participante participante) {

		int extraEquipoFase = 0;
		for (Equipo equipo : Equipo.listaEquipos) {
			for (Fase fase : Fase.listaDeFases) {
				if (equipoPerfectoFase(participante, fase, equipo)) {
					extraEquipoFase = extraEquipoFase + 1;
				}
			}
		}
		return extraEquipoFase;
	}

	// ----------------------------------------------------------------------
	// Verifica si todos los pronosticos de un participante para un equipo en una fase fueron acertados

	public static boolean equipoPerfectoFase(Participante participante, Fase fase, Equipo equipo) {

		ArrayList<Partido> partidosDeEsteEquipo = new ArrayList<Partido>();
		for (Partido partido : Partido.listaDePartidos) {
			if (partido.getFase().equals(fase.idFase)
					&& (partido.getEquipo1().equals(equipo) | partido.getEquipo2().equals(equipo))) {
				partidosDeEsteEquipo.add(partido);
			}
		}

		int aciertos = 0;
		for (Partido partido : partidosDeEsteEquipo) {
			for (Pronosticos pronostico : participante.getPronosticos()) {

				if (pronostico.getPartido().equals(partido)) {
					aciertos = aciertos + pronostico.Puntos();
				}
			}
		}
		if (aciertos == partidosDeEsteEquipo.size() && !(aciertos == 0)) {
			return true;
		} else {
			return false;
		}
	}

	// ----------------------------------------------------------------------
	// Cuenta cuantas rondas completas acertó un participante

	public static int BonusRonda(Participante participante) {
		int rondasCompletas = 0;
		for (Ronda ronda : Ronda.listaDeRondas) {
			if (ronda.rondaCompleta(participante.getPronosticos())) {
				rondasCompletas = rondasCompletas + 1;
			}
		}
		return rondasCompletas;
	}
	// ----------------------------------------------------------------------
	//Calcula los puntajes totales de cada participante

	public static ArrayList<String[]> obtenerPuntajes() {

		ArrayList<String[]> listaPuntajes = new ArrayList<String[]>();

		for (Participante participante : Participante.participantes) {

			int rondasCompletas = BonusRonda(participante);
			int extraEquipoFase = BonusEquipoFase(participante);
			int aciertos = ContarAciertos(participante);

			int puntos = aciertos * Configuracion.puntosPorAcierto + rondasCompletas * Configuracion.bonusRonda
					+ extraEquipoFase * Configuracion.bonusFase;

			String[] puntajes = { participante.getNombre(), Integer.toString(puntos), Integer.toString(aciertos),
					Integer.toString(rondasCompletas), Integer.toString(extraEquipoFase) };

			listaPuntajes.add(puntajes);
		}

		Collections.sort(listaPuntajes, (x, y) -> y[1].compareTo(x[1]));

		return listaPuntajes;

	}

	// -----------------------------------------------------------
	// Imprime por consola el ganador y la tabla de posiciones

	public static void imprimirPuntajes() {

		ArrayList<String[]> puntajes = Puntuacion.obtenerPuntajes();
		System.out.println("**************************************");
		System.out.println("       GANADOR: " + puntajes.get(0)[0]);
		System.out.println("**************************************");
		System.out.println("Tabla de posiciones:");
		for (String[] puntaje : puntajes) {

			System.out.println(puntaje[0] + "  -> Puntos:" + puntaje[1] + " (Aciertos:" + puntaje[2] + " ("
					+ Integer.parseInt(puntaje[2]) * Configuracion.puntosPorAcierto + " puntos)"
					+ "-> Rondas Perfectas:" + puntaje[3] + " Bonus: "
					+ Integer.parseInt(puntaje[3]) * Configuracion.bonusRonda + "  -> Equipo perfecto en fase:"
					+ puntaje[4] + " Bonus: " + Integer.parseInt(puntaje[4]) * Configuracion.bonusFase + ")");

		}
	}
}
