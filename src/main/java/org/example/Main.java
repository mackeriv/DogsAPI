package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;

public class Main {
    public static void main(String[] args) throws URISyntaxException, InterruptedException {

        URI uri = new URI("https://api.thedogapi.com/v1/images/search?limit=10");
        var client = java.net.http.HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(uri).GET().build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ioex1) {
            ioex1.printStackTrace();
        }

        var parser = new JsonParser();
        var parsed = parser.parse(response.body());

        var dir = new File("output/");
        dir.mkdir();

        for(var items : parsed) {

            String ID = items.get("id");
            URI imageAddress = new URI(items.get("url"));

            System.out.println("\033[0;1m" + "ID: " + "\033[0;m" + ID);
            System.out.println("\033[0;1m" + "URL: " + "\033[0;m" + imageAddress);

            try {
                BufferedImage image = ImageIO.read(imageAddress.toURL());
                ImageIO.write(image, "jpg", new File("output/" + ID +
                        imageAddress.toString().substring(imageAddress.toString().length() - 4)));

                System.out.println("\033[0;1m" + "Image saved!");
                System.out.println("\n");

            } catch (IOException ioex2) {
                ioex2.printStackTrace();
            }
        }
    }
}