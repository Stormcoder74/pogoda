package html;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.List;

public class PageMaker {
    public static String make(List<WeatherReport> reportList) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template thRow = velocityEngine.getTemplate("thRow.tmpl", "UTF-8");
        Template tdRow = velocityEngine.getTemplate("tdRow.tmpl", "UTF-8");
        Template page = velocityEngine.getTemplate("page.tmpl", "UTF-8");
        VelocityContext vContext = new VelocityContext();

        StringBuilder tbody = new StringBuilder();
        String date = "";

        for (WeatherReport wReport: reportList){
            if(!date.equals(wReport.getDate())){
                date = wReport.getDate();
                StringWriter hRowWriter = new StringWriter();
                vContext.put("date", date);
                thRow.merge(vContext, hRowWriter);
                tbody.append(hRowWriter.toString());
            }
            StringWriter dRowWriter = new StringWriter();
            vContext.put("time", wReport.getTime());
            vContext.put("conditions", wReport.getConditions());
            vContext.put("temperature", wReport.getTemperature());
            vContext.put("pressure", wReport.getPressure());
            vContext.put("humidity", wReport.getHumidity());
            vContext.put("wind", wReport.getWind());
            tdRow.merge(vContext, dRowWriter);
            tbody.append(dRowWriter.toString());
        }

        vContext.put("tbody", tbody);
        StringWriter pageWriter = new StringWriter();
        page.merge(vContext, pageWriter);

        return pageWriter.toString();
    }
}
