package window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Controller {
    @FXML
    public WebView web;

    public void clickButton(ActionEvent actionEvent) {
        WebEngine webEngine = web.getEngine();
        File file = new File("example.html");
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("Local URL: " + url.toString());
        webEngine.load(url.toString());
    }

    public void mouseMove(MouseEvent event) {
        WebEngine webEngine = web.getEngine();
        File file = new File("example.html");
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("Local URL: " + url.toString());
        webEngine.load(url.toString());
    }
}
