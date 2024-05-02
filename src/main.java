import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.Scanner;


public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        HttpClient httpClient = HttpClient.newHttpClient();
        String apiKey = "443e56a79c361f14fcf3c4c5";
        String url = "https://api.exchangerate-api.com/v4/latest/USD";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("apikey", apiKey)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                JsonParser parser = new JsonParser();
                JsonObject jsonResponse = parser.parse(response.body()).getAsJsonObject();
                JsonObject rates = jsonResponse.getAsJsonObject("rates");

                boolean running = true;
                while (running) {
                    System.out.println("*************************************************");
                    System.out.println("Sea bienvenido/a al Conversor de Monedas");
                    System.out.println("1) Dólar -> Peso argentino");
                    System.out.println("2) Peso argentino -> Dólar");
                    System.out.println("3) Dólar -> Real brasileño");
                    System.out.println("4) Real brasileño -> Dólar");
                    System.out.println("5) Dólar -> Peso colombiano");
                    System.out.println("6) Peso colombiano -> Dólar");
                    System.out.println("7) Salir");
                    System.out.println("Elija una opción válida:");

                    int option = scanner.nextInt();
                    double amount, result;

                    switch (option) {
                        case 1:
                            System.out.println("Introduzca la cantidad de dólares:");
                            amount = scanner.nextDouble();
                            result = convertDollarToARS(amount, rates);
                            System.out.println("El equivalente en pesos argentinos es: " + result);
                            break;
                        case 2:
                            System.out.println("Introduzca la cantidad de pesos argentinos:");
                            amount = scanner.nextDouble();
                            result = convertARSToDollar(amount, rates);
                            System.out.println("El equivalente en dólares es: " + result);
                            break;
                        case 3:
                            System.out.println("Introduzca la cantidad de dólares:");
                            amount = scanner.nextDouble();
                            result = convertDollarToBRL(amount, rates);
                            System.out.println("El equivalente en reales brasileños es: " + result);
                            break;
                        case 4:
                            System.out.println("Introduzca la cantidad de reales brasileños:");
                            amount = scanner.nextDouble();
                            result = convertBRLToDollar(amount, rates);
                            System.out.println("El equivalente en dólares es: " + result);
                            break;
                        case 5:
                            System.out.println("Introduzca la cantidad de dólares:");
                            amount = scanner.nextDouble();
                            result = convertDollarToCOP(amount, rates);
                            System.out.println("El equivalente en pesos colombianos es: " + result);
                            break;
                        case 6:
                            System.out.println("Introduzca la cantidad de pesos colombianos:");
                            amount = scanner.nextDouble();
                            result = convertCOPToDollar(amount, rates);
                            System.out.println("El equivalente en dólares es: " + result);
                            break;
                        case 7:
                            running = false;
                            System.out.println("¡Hasta luego!");
                            break;
                        default:
                            System.out.println("Opción no válida. Por favor, elija una opción del 1 al 7.");
                    }
                }
            } else {
                System.out.println("Error al realizar la solicitud: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para convertir dólares a pesos argentinos
    public static double convertDollarToARS(double dollars, JsonObject rates) {
        double exchangeRateARS = rates.get("ARS").getAsDouble();
        return dollars * exchangeRateARS;
    }

    // Método para convertir pesos argentinos a dólares
    public static double convertARSToDollar(double pesos, JsonObject rates) {
        double exchangeRateARS = rates.get("ARS").getAsDouble();
        return pesos / exchangeRateARS;
    }

    // Método para convertir dólares a reales brasileños
    public static double convertDollarToBRL(double dollars, JsonObject rates) {
        double exchangeRateBRL = rates.get("BRL").getAsDouble();
        return dollars * exchangeRateBRL;
    }

    // Método para convertir reales brasileños a dólares
    public static double convertBRLToDollar(double reais, JsonObject rates) {
        double exchangeRateBRL = rates.get("BRL").getAsDouble();
        return reais / exchangeRateBRL;
    }

    // Método para convertir dólares a pesos colombianos
    public static double convertDollarToCOP(double dollars, JsonObject rates) {
        double exchangeRateCOP = rates.get("COP").getAsDouble();
        return dollars * exchangeRateCOP;
    }

    // Método para convertir pesos colombianos a dólares
    public static double convertCOPToDollar(double pesos, JsonObject rates) {
        double exchangeRateCOP = rates.get("COP").getAsDouble();
        return pesos / exchangeRateCOP;
    }

}
