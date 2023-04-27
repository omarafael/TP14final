package PronosticosDeportivos;

import PronosticosDeportivos.ResultadoEnum.Resultado;

public class Pronosticos {

	private Partido partido;
	private Resultado resultadoEquipo1;
	
	
	public Pronosticos(Partido partido, Resultado resultadoEquipo1) {
		
		this.partido = partido;
		this.resultadoEquipo1= resultadoEquipo1;
	}


	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Resultado getResultadoEquipo1() {
		return resultadoEquipo1;
	}

	public void setResultadoEquipo1(Resultado resultadoEquipo1) {
		this.resultadoEquipo1 = resultadoEquipo1;
	}
	

	//-----------------------------------------------------------------------
	// verifica si el pronostico de un partido es acertado
	public int Puntos() {
		int puntos=0;
		if (this.resultadoEquipo1.equals(partido.Resultadoequipo1())) {
			puntos=1;
		}
			return puntos;
		}

	
	}
	
	

