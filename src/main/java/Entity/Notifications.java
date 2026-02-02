package Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.Notification;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notifications_rules")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "min_temperature")
    private Double minTemp;

    @Column(name = "max_temperature")
    private Double maxTemp;

    private boolean notifyRain;

    private boolean notifySnow;

@OneToMany(mappedBy = "notifications")
    private List<Users> users;


}
