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
import java.util.Map;

public class Practica6 {

    public static void main(String[] args) {
        try {
            HashMap<String, String> chuckNorrisJokeMap = obtenerChuckNorrisJoke();
            String textoChiste = obtenerTextoDelChiste(chuckNorrisJokeMap.get("chiste"));
            String urlChiste = chuckNorrisJokeMap.get("url");

            System.out.println("Chiste de Chuck Norris: " + textoChiste);
            System.out.println("URL del chiste: " + urlChiste);
        } catch (IOException e) {
            System.err.println("Error al obtener el chiste de Chuck Norris: " + e.getMessage());
        }
    }

    private static HashMap<String, String> obtenerChuckNorrisJoke() throws IOException {
        String apiUrl = "https://api.chucknorris.io/jokes/random";
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Parsear la respuesta JSON y almacenarla en un HashMap
            HashMap<String, String> chuckNorrisJokeMap = new HashMap<>();
            chuckNorrisJokeMap.put("chiste", response.toString());
            chuckNorrisJokeMap.put("url", obtenerUrlDelChiste(response.toString()));

            return chuckNorrisJokeMap;
        } else {
            throw new IOException("Error al obtener el chiste de Chuck Norris. Código de respuesta: " + responseCode);
        }
    }

    private static String obtenerTextoDelChiste(String respuestaJson) {
        // Parsear la respuesta JSON y obtener el valor del campo "value"
        int startIndex = respuestaJson.indexOf("\"value\":\"") + 9;
        int endIndex = respuestaJson.indexOf("\"}", startIndex);
        return respuestaJson.substring(startIndex, endIndex);
    }

    private static String obtenerUrlDelChiste(String respuestaJson) {
        // Parsear la respuesta JSON y obtener el valor del campo "url"
        int startIndex = respuestaJson.indexOf("\"url\":\"") + 7;
        int endIndex = respuestaJson.indexOf("\",\"value\"", startIndex);
        return respuestaJson.substring(startIndex, endIndex);
    }
}
