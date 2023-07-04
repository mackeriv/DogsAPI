package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        URI uri = new URI("https://api.thedogapi.com/v1/images/search?limit=10");
        var client = java.net.http.HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(uri).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        var parser = new JsonParser();

        var parsed = parser.parse(response.body());

        for(var items : parsed) {

            String ID = items.get("id");
            URI imageAddress = new URI(items.get("url"));

            System.out.println("ID: " + ID);
            System.out.println("URL: " + imageAddress);

            BufferedImage image = ImageIO.read(imageAddress.toURL());

            ImageIO.write(image, "jpg", new File("C:\\Users\\rafa_\\Desktop\\Java\\Java 8 - Beyond the Basics\\Dogs\\output\\" + ID + imageAddress.toString().substring(imageAddress.toString().length()-4)));

            System.out.println("Image saved!");
            System.out.println("\n");


        }

    }
}