package PronosticosDeportivos;



import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import PronosticosDeportivos.ResultadoEnum.Resultado;
import puntuacion.Puntuacion;

class TestRonda {
	
	private Participante participante1 = new Participante("David");
	private Ronda ronda1 = new Ronda("1");
	private Ronda ronda2= new Ronda("2");
 

	
	void setup (){
		ArrayList<Equipo>equipos = new ArrayList<Equipo>();
		Equipo equipo1 = new Equipo("Argentina","Seleccion");
		equipos.add(equipo1);
		Equipo equipo2 = new Equipo("Belgica","Seleccion");
		equipos.add(equipo2);
		Equipo equipo3 = new Equipo("Uruguay","Seleccion");
		equipos.add(equipo3);
		Equipo equipo4 = new Equipo("Chile","Seleccion");
		equipos.add(equipo4);
		
		ArrayList<Partido>partidos = new ArrayList<Partido>();
		Partido partido1 = new Partido("1","1","1",equipo1,equipo2,2,1);
		partidos.add(partido1);
		Partido partido2 = new Partido("1","1","2",equipo3,equipo4,2,3);
		partidos.add(partido2);
		Partido partido3 = new Partido("1","1","3",equipo1,equipo3,2,2);
		partidos.add(partido3);
		Partido partido4 = new Partido("1","1","4",equipo4,equipo2,3,1);
		partidos.add(partido4);
		Partido partido5 = new Partido("1","2","5",equipo1,equipo4,2,1);
		partidos.add(partido5);
		Partido partido6 = new Partido("1","2","6",equipo2,equipo4,2,4);
		partidos.add(partido6);
		
	
		
		ronda1.agregarPartido(partido1);
		ronda1.agregarPartido(partido2);
		ronda1.agregarPartido(partido3);
		ronda1.agregarPartido(partido4);
		ronda2.agregarPartido(partido5);
		ronda2.agregarPartido(partido6);
		
		participante1.agregarPronostico(new Pronosticos(partido1,Resultado.GANADOR));
		participante1.agregarPronostico(new Pronosticos(partido2,Resultado.EMPATE));
		participante1.agregarPronostico(new Pronosticos(partido3,Resultado.EMPATE));
		participante1.agregarPronostico(new Pronosticos(partido4,Resultado.PERDEDOR));
		participante1.agregarPronostico(new Pronosticos(partido5,Resultado.GANADOR));
		participante1.agregarPronostico(new Pronosticos(partido6,Resultado.PERDEDOR));
	
	}
	
	@Test
	void testContarAciertos (){
		setup();
		int puntos = Puntuacion.ContarAciertos(participante1);
		
			Assertions.assertEquals(puntos, 4);
}
	@Test
	void testRonda1Completa (){
		setup();
		boolean completa = ronda1.rondaCompleta(participante1.getPronosticos());
		
			Assertions.assertEquals(completa, false);
}
	@Test
	void testRonda2Completa (){
		setup();
		boolean completa = ronda2.rondaCompleta(participante1.getPronosticos());
		
			Assertions.assertEquals(completa, true);
}
	

}
