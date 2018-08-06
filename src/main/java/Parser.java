import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static Pattern datePattern;

    static {
        datePattern = Pattern.compile("\\d{2}\\.\\d{2}");
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
        Matcher matcher = datePattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Нет даты";
    }

    public static void main(String[] args) {
        parse();
    }

    public static List<WeatherReport> parse() {
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
}
