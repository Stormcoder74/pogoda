package html;

public class WeatherReport {
    private String date;
    private String time;
    private String conditions;
    private String temperature;
    private String pressure;
    private String humidity;
    private String windBearing;
    private String windSpeed;

    WeatherReport(String date,
                  String time,
                  String conditions,
                  String temperature,
                  String pressure,
                  String humidity,
                  String windBearing,
                  String windSpeed) {
        this.date = date;
        this.time = time;
        this.conditions = conditions;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windBearing = windBearing;
        this.windSpeed = windSpeed;
    }

    String getDate() {
        return date;
    }

    String getTime() {
        return time;
    }

    String getConditions() {
        return conditions;
    }

    String getTemperature() {
        return temperature;
    }

    String getPressure() {
        return pressure;
    }

    String getHumidity() {
        return humidity;
    }

    String getWindBearing() {
        return windBearing;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
}
