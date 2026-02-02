package Service.DTO;

import Service.ContactType;
import lombok.Data;

@Data
public class UsersData {
    private Long id;
    private String userName;
    private ContactType contactType;
    private NotificationRules rules;
}
