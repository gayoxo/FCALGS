import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 */

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String Pathfile="test2.dot";
		if (args.length>2)
			Pathfile=args[2];
		
		else
			Arrays.toString(args);
		
		ArrayList<String> listatotal=new ArrayList<String>();
		
		String texto="";
		
		try
		{
		FileReader lector=new FileReader(Pathfile);

		BufferedReader contenido=new BufferedReader(lector);

		while((texto=contenido.readLine())!=null)
		{
	//	System.out.println(texto);
		listatotal.add(texto);
		}
		
		contenido.close();
		
		File folder = new File("/opt/fcalgs/");
		folder.mkdirs();
		
		int anterior = -1; 
		
		ArrayList<String> BufferExcel=new ArrayList<String>();
		BufferExcel.add("ITERATION;VALUE");
		
		for (int i = 1; i <= listatotal.size(); i++) {
			
			File archivo = new File("/opt/fcalgs/"+i+".dot");
			BufferedWriter bw;
			 if(archivo.exists()) {
		            bw = new BufferedWriter(new FileWriter(archivo));
		        } else {
		            bw = new BufferedWriter(new FileWriter(archivo));
		        }
			
			 
			
			 
			for (int j = 0; j < i; j++) 
		        bw.write(listatotal.get(j)+"\n");

			
			
			  bw.close();
			  
			 Process theProcess = null;
			 BufferedReader inStream = null;
			 
			 try
		      {
		          theProcess = 
		        		  Runtime.getRuntime().exec("/pcbo-amai/pcbo-windows-i686-static.exe /opt/fcalgs/"+i+".dot"
		      +" /opt/fcalgs/"+i+"res.dot"
		        		  );
		          
		      //		          theProcess.waitFor();
		      }
		      catch(IOException e)
		      {
		         System.err.println("Error en el método exec()");
		         e.printStackTrace();
		      }
		        
			 try
				{
			 
			 inStream = new BufferedReader(
                     new InputStreamReader( theProcess.getInputStream() ));

			 @SuppressWarnings("unused")
			String texto3="";
			while((texto3=inStream.readLine())!=null);
			
			 
			 
			 ArrayList<String> listatotal2=new ArrayList<String>();
				
				String texto2="";
				
				
					
				File archivo2 = new File("/opt/fcalgs/"+i+"res.dot");	
				
				FileReader lector2=new FileReader(archivo2);

				BufferedReader contenido2=new BufferedReader(lector2);

				while((texto=contenido2.readLine())!=null)
					listatotal2.add(texto2);
				
				contenido2.close();
				
				if (listatotal2.size()<anterior)
		        	 break;
		         else
		        	 anterior=listatotal2.size();
		         
		         BufferExcel.add(i+";"+listatotal2.size());
		         System.out.println(anterior);
     
		         
		         File archivo3 = new File("/opt/fcalgs/"+(i-1)+"res.dot");	
		         if (archivo3.exists())
		        	 archivo3.delete();
		         
		         archivo.delete();
		         
		      }
		      catch(IOException e)
		      {
		         System.err.println("Error en inStream.readLine()");
		         e.printStackTrace();
		      }  
			
		}
		
		

		
		String rutaArchivo = "/opt/fcalgs/Result"+System.nanoTime()+".xlsx";
	     File archivoXLS = new File(rutaArchivo);
	     if(archivoXLS.exists()) archivoXLS.delete();
	     archivoXLS.createNewFile();
	     Workbook libro = new XSSFWorkbook();
	     DataFormat format = libro.createDataFormat();
	     CellStyle style = libro.createCellStyle();
	     style.setDataFormat(format.getFormat("0"));
	     FileOutputStream archivofin = new FileOutputStream(archivoXLS);
	     Sheet hoja=libro.createSheet();   
	     int row=0;
	     for (int j = 0; j < BufferExcel.size(); j++) {
	    	 String linea=BufferExcel.get(j);
	    	 Row fila = hoja.createRow(row++);
	    	 String[] filaS = linea.split(";");
	    	 int cell = 0;
	    	 for (int r = 0; r < filaS.length; r++) {
	    		 String string=filaS[r];
	    		 Cell celda = fila.createCell(cell++);
	    		 if (r!=0&&j!=0)
	    			 {
	    			 celda.setCellValue(Long.parseLong(string.trim()));
	    		 	 celda.setCellStyle(style);
	    			 }
	    		 else
	    			 celda.setCellValue(string.trim());
			}

		} 
	     libro.write(archivofin);
	     archivofin.close();
	     
	     System.out.println("FIN");
		
		}

		catch(Exception e)
		{
			e.printStackTrace();
		System.out.println("Error al leer");
		}
		

	}

}
