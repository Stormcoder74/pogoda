package html;

import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.HourlyDataPoint;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static List<WeatherReport> parseDarkskyNet() {
        String apiKey = "0ed7feb0af3ae55251c37e7f0c52a2ad";
        double latitude = 46.616280;
        double longitude = 142.777985;

        List<WeatherReport> wrList = new ArrayList<>();

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

            LocalDate date;
            LocalTime time;
            for (HourlyDataPoint hdp : forecast.getHourly().getData()) {
                date = LocalDateTime.ofInstant(hdp.getTime(), ZoneId.of(forecast.getTimezone())).toLocalDate();
                time = LocalDateTime.ofInstant(hdp.getTime(), ZoneId.of(forecast.getTimezone())).toLocalTime();

                if (time.toString().equals("00:00")
                        || time.toString().equals("06:00")
                        || time.toString().equals("12:00")
                        || time.toString().equals("18:00")) {

                    wrList.add(new WeatherReport(
                            date.toString(),
                            time.toString(),
                            hdp.getSummary(),
                            ((Integer)hdp.getTemperature().intValue()).toString() ,
                            ((Integer)(int)(hdp.getPressure() * 0.750064)).toString(),
                            ((Integer)(int)(hdp.getHumidity() * 100)).toString(),
                            hdp.getWindBearing().toString(),
                            ((Integer)hdp.getWindSpeed().intValue()).toString()));
                }
            }
        } catch (ForecastException e) {
            e.printStackTrace();
        }

        return wrList;
    }
}
