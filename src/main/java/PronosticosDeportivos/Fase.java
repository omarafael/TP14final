package PronosticosDeportivos;

import java.util.ArrayList;

public class Fase {
	
	public String idFase;
	public ArrayList<Ronda> rondasDeEstaFase = new ArrayList<Ronda>();
	public static ArrayList<Fase> listaDeFases = new ArrayList<Fase>();

	public Fase(String idFase) {
	
		this.idFase = idFase;
	}

	public String getFase() {
		return idFase;
	}

	public void agregarRonda(Ronda ronda) {
		rondasDeEstaFase.add(ronda);
	}
	
	
	//-----------------------------------------------------------------------------------
	// Verifica si existe la fase, si no existe la crea
	public static Fase existeFase(String idFase) {

		for (Fase fase : listaDeFases) {
			if (fase.getFase().equals(idFase)) {
				return fase;
			}
		}
		
			Fase nuevaFase = new Fase(idFase);
			listaDeFases.add(nuevaFase);
		return nuevaFase;
	}
	
	//------------------------------------------------------------------------------------
	// verifica si un participante acerto todos los pronosticos de un equipo en una fase
	
  public static boolean equipoPerfectoFase (Participante participante, Fase fase, Equipo equipo) {
	  
	  ArrayList<Partido> partidosDeEsteEquipo = new ArrayList<Partido>();
	  
		  for (Partido partido: Partido.listaDePartidos) {
			  if(partido.getFase().equals(fase.idFase) && (partido.getEquipo1().equals(equipo) | partido.getEquipo2().equals(equipo))) {
				  partidosDeEsteEquipo.add(partido);
			  }
		  }
		

	  int aciertos = 0;
	  for (Partido partido:partidosDeEsteEquipo) {
		  for (Pronosticos pronostico: participante.getPronosticos()) {
			 
			  if (pronostico.getPartido().equals(partido)) {
				  aciertos = aciertos + pronostico.Puntos();
			  }
		  }
	  }
	  if (aciertos == partidosDeEsteEquipo.size() && !(aciertos==0)) {
		return true;
	  }else {
		  return false;
	  }	  
  }
}
