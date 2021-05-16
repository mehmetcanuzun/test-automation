// Mehmetcan Uzun - HepsiBurada CaseStudy Entegrasyon Testi

package hepsiburadaCase;

import java.net.HttpURLConnection;
import java.net.URL;

public class IntegrationCase {
	
	public static void main(String[] args) {
		
		try {			
			URL url = new URL("https://generator.swagger.io/api/swagger.json");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int statusCode = connection.getResponseCode();
            
			if (statusCode == 200) {
				System.out.println("TEST SUCCESS, Code: " + statusCode);
			}
			else {
				System.out.println("TEST FAIL, Code: " + statusCode);
			}
		}
		
		catch(Exception e) {
		}
	}
}