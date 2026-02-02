package Service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentWeather {
    private String time;
    private Integer interval; //інтервал наступного запиту
    @JsonProperty("temperature_2m")
    private Double temperature;
    @JsonProperty("relative_humidity_2m")
    private Integer humidity;
    @JsonProperty("apparent_temperature")
    private Double feelsLike;
    @JsonProperty("is_day")
    private Integer isDay;
    private Double rain;
    @JsonProperty("weather_code")
    private Integer weatherCode;
    @JsonProperty("wind_speed_10m")
    private Double windSpeedInteger;
}