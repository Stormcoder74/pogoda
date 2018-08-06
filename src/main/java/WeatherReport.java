public class WeatherReport {
    private String date;
    private String time;
    private String conditions;
    private String temperature;
    private String pressure;
    private String humidity;
    private String wind;

    public WeatherReport(String date,
                         String time,
                         String conditions,
                         String temperature,
                         String pressure,
                         String humidity,
                         String wind) {
        this.date = date;
        this.time = time;
        this.conditions = conditions;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind = wind;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getConditions() {
        return conditions;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind() {
        return wind;
    }
}
