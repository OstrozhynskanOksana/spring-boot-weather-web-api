package Entity;

import Service.DTO.NotificationRules;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users_data")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    private String phone;

    private String userName;

    @ManyToOne
    @JoinColumn (name = "notifications_id")
    private Notifications notifications;

    @ManyToOne
    @JoinColumn (name = "current_location_id")
    private Location currentLocation;


}
