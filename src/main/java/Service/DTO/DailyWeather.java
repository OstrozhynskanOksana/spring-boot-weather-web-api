package Service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DailyWeather {
    private List<String> time;
    @JsonProperty("weather_code")
    private List<Integer> weatherCodes;
    @JsonProperty("temperature_2m_max")
    private List<Double> tempMax;
    @JsonProperty("temperature_2m_min")
    private List<Double> tempMin;
    private List<String> sunrise;
    private List<String> sunset;
    @JsonProperty("uv_index_max")
    private List<Double> uvMax;
    @JsonProperty("rain_sum")
    private List<Double> rainSum;
}
