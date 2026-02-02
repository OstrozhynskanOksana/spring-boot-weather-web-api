package Service.DTO;

import lombok.Data;

@Data
public class NotificationRules {
    private Double minTemp;
    private Double maxTemp;
    private boolean notifyRain;
    private boolean notifySnow;
}