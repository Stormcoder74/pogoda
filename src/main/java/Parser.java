import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
// video 0:24
import java.io.IOException;
import java.net.URL;
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

    // 28.07 Суббота погода сегодня
    // 28.07
    // регулярное выражение \d{2}\.\d{2}
    private static String getDateFromString(String stringDate) {
        Matcher matcher = datePattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Нет даты";
    }

    public static void main(String[] args) {
        Document page = getPage("http://www.pogoda.spb.ru/");
        Element tableWeth = page.select("table[class=wt]").first();
        Elements headers = tableWeth.select("tr[class = wth]");
        Elements values = tableWeth.select("tr[valign = top]");

        print(headers, values);
    }

    private static void print(Elements headers, Elements values) {
        int valueIndex = 0;
        for (Element header : headers) {
            String date = getDateFromString(header.select("th[id = dt]").text());
            System.out.printf("%5s %52s %14s %11s %12s %15s\n",
                    date, "Явления", "Температура", "Давление", "Влажность", "Ветер");

            for (; valueIndex < values.size(); valueIndex++) {
                Elements data = values.get(valueIndex).select("td");
                String time = data.get(0).text();
                System.out.printf("%5s %52s %14s %11s %12s %15s\n",
                        time,
                        data.get(1).text(),
                        data.get(2).text(),
                        data.get(3).text(),
                        data.get(4).text(),
                        data.get(5).text());
                if (time.toLowerCase().equals("ночь")) {
                    valueIndex++;
                    System.out.println();
                    break;
                }
            }
        }
    }
}
