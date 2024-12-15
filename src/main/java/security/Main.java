package security;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        int greaterCount = 0; // 1050000 byte'dan büyük olanların sayısı
        int smallerCount = 0; // 1050000 byte'dan küçük olanların sayısı

        for (int i = 0; i < 100; i++) {
            try {
                // API çağrısı
                URL url = new URL("https://random.dog/woof.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // JSON yanıtını oku
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // JSON'dan URL'yi çıkar
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                String imageUrl = jsonObject.get("url").getAsString();

                // Görüntü boyutunu kontrol et
                URL image = new URL(imageUrl);
                HttpURLConnection imageConnection = (HttpURLConnection) image.openConnection();
                imageConnection.setRequestMethod("HEAD");
                int contentLength = imageConnection.getContentLength();

                if (contentLength > 1050000) {
                    greaterCount++;
                } else {
                    smallerCount++;
                }

            } catch (Exception e) {
                System.out.println("Hata oluştu: " + e.getMessage());
            }
        }

        // Sonuçları yazdır
        System.out.println("1050000 byte'dan büyük olanlar: " + greaterCount);
        System.out.println("1050000 byte'dan küçük olanlar: " + smallerCount);

    }
}