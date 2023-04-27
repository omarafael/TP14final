package PronosticosDeportivos;

import java.util.ArrayList;

public class Equipo {

	private String nombre;
	private String descripcion;
	public static ArrayList<Equipo> listaEquipos = new ArrayList<Equipo>();

	public Equipo(String nombre, String descripcion) {

		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	// -------------------------------------------------------------------------------
	// Verifica si un equipo existe, si no existe lo crea

	public static Equipo existeEquipo(String nombreEquipo, String descripcion) {

		for (Equipo equipo : listaEquipos) {
			if (equipo.getNombre().equals(nombreEquipo)) {
				return equipo;
			}
		}

		Equipo esteEquipo = new Equipo(nombreEquipo, descripcion);
		listaEquipos.add(esteEquipo);

		return esteEquipo;
	}
}
