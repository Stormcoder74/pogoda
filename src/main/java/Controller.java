import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Controller {
    @FXML
    public WebView webView;

    public void clickButtonOld(ActionEvent actionEvent) {
        WebEngine webEngine = webView.getEngine();
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

    public void clickButton(ActionEvent actionEvent) {
        List<WeatherReport> reportList = Parser.parse();
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template thRow = velocityEngine.getTemplate("thRow.tmpl", "UTF-8");
        Template tdRow = velocityEngine.getTemplate("tdRow.tmpl", "UTF-8");

        StringBuilder tbody = new StringBuilder();

        String date = "";
        for (WeatherReport wr: reportList){
            if(!date.equals(wr.getDate())){
                date = wr.getDate();
                VelocityContext hvc = new VelocityContext();
                StringWriter hsw = new StringWriter();
                hvc.put("date", date);
                thRow.merge(hvc, hsw);
                tbody.append(hsw.toString());
            }
            VelocityContext dvc = new VelocityContext();
            StringWriter dsw = new StringWriter();
            dvc.put("time", wr.getTime());
            dvc.put("conditions", wr.getConditions());
            dvc.put("temperature", wr.getTemperature());
            dvc.put("pressure", wr.getPressure());
            dvc.put("humidity", wr.getHumidity());
            dvc.put("wind", wr.getWind());
            tdRow.merge(dvc, dsw);
            tbody.append(dsw.toString());
        }

        Template page = velocityEngine.getTemplate("page.tmpl", "UTF-8");
        VelocityContext pageVC = new VelocityContext();
        pageVC.put("tbody", tbody);
        StringWriter pageWriter = new StringWriter();
        page.merge(pageVC, pageWriter);


        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(pageWriter.toString());
//        webEngine.load(pageWriter.toString());
    }
}
