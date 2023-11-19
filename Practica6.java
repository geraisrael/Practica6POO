/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package gerardo.gonzalez.uabc.practica6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Practica6 {

    public static void main(String[] args) {
        try {
            HashMap<String, String> mapaChisteChuckNorris = obtenerChisteChuckNorris();
            String textoChiste = obtenerTextoDelChiste(mapaChisteChuckNorris.get("chiste"));
            String urlChiste = mapaChisteChuckNorris.get("url");

            System.out.println("Chiste de Chuck Norris: " + textoChiste);
            System.out.println("URL del chiste: " + urlChiste);
        } catch (IOException e) {
            System.err.println("Error al obtener el chiste de Chuck Norris: " + e.getMessage());
        }
    }

    private static HashMap<String, String> obtenerChisteChuckNorris() throws IOException {
        String apiUrl = "https://api.chucknorris.io/jokes/random";
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int codigoRespuesta = connection.getResponseCode();
        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
            BufferedReader lector = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder respuesta = new StringBuilder();
            String linea;

            while ((linea = lector.readLine()) != null) {
                respuesta.append(linea);
            }

            lector.close();

            // Parsear la respuesta JSON y almacenarla en un HashMap
            HashMap<String, String> mapaChisteChuckNorris = new HashMap<>();
            mapaChisteChuckNorris.put("chiste", respuesta.toString());
            mapaChisteChuckNorris.put("url", obtenerUrlDelChiste(respuesta.toString()));

            return mapaChisteChuckNorris;
        } else {
            throw new IOException("Error al obtener el chiste de Chuck Norris. Código de respuesta: " + codigoRespuesta);
        }
    }

    private static String obtenerTextoDelChiste(String respuestaJson) {
        // Parsear la respuesta JSON y obtener el valor del campo "value"
        int inicio = respuestaJson.indexOf("\"value\":\"") + 9;
        int fin = respuestaJson.indexOf("\"}", inicio);
        return respuestaJson.substring(inicio, fin);
    }

    private static String obtenerUrlDelChiste(String respuestaJson) {
        // Parsear la respuesta JSON y obtener el valor del campo "url"
        int inicio = respuestaJson.indexOf("\"url\":\"") + 7;
        int fin = respuestaJson.indexOf("\",\"value\"", inicio);
        return respuestaJson.substring(inicio, fin);
    }
}
