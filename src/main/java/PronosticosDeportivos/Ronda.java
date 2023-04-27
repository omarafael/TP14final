package PronosticosDeportivos;

import java.util.ArrayList;

public class Ronda {
	
	public String idRonda;
	public ArrayList<Partido> partidosDeEstaRonda= new ArrayList<Partido>();
	public static ArrayList<Ronda> listaDeRondas= new ArrayList<Ronda>();

	public Ronda(String ronda) {
	
		this.idRonda = ronda;
	}

	public String getRonda() {
		return idRonda;
	}

	public void agregarPartido(Partido partido) {
		partidosDeEstaRonda.add(partido);
	}

	
	
	//-----------------------------------------------------------------------------------
	// Verifica si una ronda existe, si no existe la crea
	public static Ronda existeRonda(String idRonda) {

		for (Ronda ronda : listaDeRondas) {
			if (ronda.getRonda().equals(idRonda)) {
				return ronda;
			}
		}
		
			Ronda nuevaRonda = new Ronda(idRonda);
			listaDeRondas.add(nuevaRonda);
		return nuevaRonda;
	}
	
	
	//-------------------------------------------------------------------------
		// Verifica si un participante acerto todos los pronosticos de una ronda
		
		public boolean rondaCompleta(ArrayList<Pronosticos> pronosticosDeRonda) {
			
			int puntos=0;
			for (Partido partido: partidosDeEstaRonda) {
			
				for (Pronosticos pronostico: pronosticosDeRonda) {
					if (partido.getIdPartido().equals(pronostico.getPartido().getIdPartido())) {
						puntos = puntos + pronostico.Puntos();
					}
				}
			}
			
			if (puntos == partidosDeEstaRonda.size() && !(puntos==0)) {
				return true;
			}else {
				return false;
			}	
		}

}
