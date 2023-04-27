package configuracion;

import static configuracion.ConectarDB.DB_URL;

import java.io.FileReader;
import java.io.IOException;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Configuracion {
	 
	public static int puntosPorAcierto;
	public static int bonusRonda;
	public static int bonusFase;
	
	
	
	public static void leerConfiguracion(String archivoConfig) {

		try {
			
			CSVReader csvReader = new CSVReader(new FileReader(archivoConfig));
			String[] fila = null;
			String[] estaFila = null;
			String[] datos = new String[4];
			
			int i = 0;
			while ((fila = csvReader.readNext()) != null) {
				estaFila = fila[0].split(";");
				datos[i] = estaFila[1];
				i=i+1;
			}
	
			DB_URL = datos[0];
			puntosPorAcierto = Integer.parseInt(datos[1]);
			bonusRonda = Integer.parseInt(datos[2]);
			bonusFase = Integer.parseInt(datos[3]);

		} catch (IOException e) {
			System.out.println("Error:" + e);
		} catch (CsvValidationException e) {

			e.printStackTrace();
		}
	}

}
