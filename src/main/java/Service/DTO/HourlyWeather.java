package Service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class HourlyWeather {
    private List<String> time;
    @JsonProperty("temperature_2m")
    private List<Double> temperatures;
    private List<Double> rain;
    private List<Integer> visibility;
    @JsonProperty("wind_speed_10m")
    private List<Double> windSpeeds;
    @JsonProperty("uv_index")
    private List<Double> uvIndexes;
    @JsonProperty("weather_code")
    private List<Integer> weatherCodes;
    @JsonProperty("relative_humidity_2m")
    private List<Integer> humidities;
    @JsonProperty("is_day")
    private List<Integer> isDay;
}
