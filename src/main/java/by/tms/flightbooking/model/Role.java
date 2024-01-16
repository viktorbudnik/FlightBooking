package by.tms.flightbooking.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Entity
@Table(name ="role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Override
    public String getAuthority() {
        return null;
    }
}
