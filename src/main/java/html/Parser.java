package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.HourlyDataPoint;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static Pattern dateSpbRuPattern;
    private static Pattern dateDarkskyNetPattern;
    private static Pattern timeDarkskyNetPattern;

    static {
        dateSpbRuPattern = Pattern.compile("\\d{2}\\.\\d{2}");
        dateDarkskyNetPattern = Pattern.compile("\\d{2}\\.\\d{2}");
        timeDarkskyNetPattern = Pattern.compile("\\d{2}\\.\\d{2}");

    }

    private static Document getPage(String url) {
        Document page = null;
        try {
            page = Jsoup.parse(new URL(url), 3000);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось получить страницу");
        }
        return page;
    }

    private static String getDateFromString(String stringDate) {
        Matcher matcher = dateSpbRuPattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Нет даты";
    }

    public static List<WeatherReport> parseSpbRu() {
        Document page = getPage("http://www.pogoda.spb.ru/");
        Element tableWeath = page.select("table[class=wt]").first();
        Elements headers = tableWeath.select("tr[class = wth]");
        Elements values = tableWeath.select("tr[valign = top]");

        List<WeatherReport> reportList = new ArrayList<>();
        int valueIndex = 0;

        for (Element header : headers) {
            String date = getDateFromString(header.select("th[id = dt]").text());

            for (; valueIndex < values.size(); valueIndex++) {
                Elements data = values.get(valueIndex).select("td");
                String time = data.get(0).text();

                reportList.add(new WeatherReport(
                        date,
                        time,
                        data.get(1).text(),
                        data.get(2).text(),
                        data.get(3).text(),
                        data.get(4).text(),
                        data.get(5).text()
                ));

                if (time.toLowerCase().equals("ночь")) {
                    valueIndex++;
                    break;
                }
            }
        }

        return reportList;
    }

    public static List<WeatherReport> parseDarkskyNet(){
        String apiKey = "0ed7feb0af3ae55251c37e7f0c52a2ad";
        double latitude = 46.616280;
        double longitude = 142.777985;

        ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey(apiKey))
                .location(new GeoCoordinates(
                        new Longitude(longitude),
                        new Latitude(latitude)))
                .language(ForecastRequestBuilder.Language.ru)
                .units(ForecastRequestBuilder.Units.si)
                .exclude(ForecastRequestBuilder.Block.minutely)
                .extendHourly()
                .build();

        DarkSkyJacksonClient client = new DarkSkyJacksonClient();
        try {
            Forecast forecast = client.forecast(request);

            for (HourlyDataPoint hdp: forecast.getHourly().getData()){
                if(hdp.getTime().)
            }
        } catch (ForecastException e) {
            e.printStackTrace();
        }


        List<WeatherReport> wrList = new ArrayList<>();




        return wrList;
    }
}
