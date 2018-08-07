import html.PageMaker;
import html.Parser;
import html.WeatherReport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.List;

public class Controller {
    @FXML
    public WebView webView;

    public void clickButton(ActionEvent actionEvent) {
        List<WeatherReport> reportList = Parser.parse();

        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(PageMaker.make(reportList));
    }


}
