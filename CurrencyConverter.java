import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/a8482158794b51ed0bab0f9f/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fromCurrency = "USD";

        boolean running = true;
        while (running) {
            System.out.println("Selecione uma moeda para converter para dólar:");
            System.out.println("1. Euro");
            System.out.println("2. Libra Esterlina");
            System.out.println("3. Iene Japonês");
            System.out.println("4. Dólar Canadense");
            System.out.println("5. Dólar Australiano");
            System.out.println("6. Franco Suíço");
            System.out.println("0. Parar o programa");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    convertCurrency(fromCurrency, "EUR");
                    break;
                case 2:
                    convertCurrency(fromCurrency, "GBP");
                    break;
                case 3:
                    convertCurrency(fromCurrency, "JPY");
                    break;
                case 4:
                    convertCurrency(fromCurrency, "CAD");
                    break;
                case 5:
                    convertCurrency(fromCurrency, "AUD");
                    break;
                case 6:
                    convertCurrency(fromCurrency, "CHF");
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    public static void convertCurrency(String fromCurrency, String toCurrency) {
        try {
            URL url = new URL(API_URL + fromCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");
            double conversionRate = rates.get(toCurrency).getAsDouble();

            System.out.println("1 " + fromCurrency + " = " + conversionRate + " " + toCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
