package ro.aglitoiu.tema_finala_pcj.AppointmentService.Entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Builder
@Entity
@Table(name = "appointment", schema = "public")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(unique=true)
    private Long id;
    @Getter @Setter
    private String car;
    @Getter @Setter
    private LocalDateTime startDate;
    @Getter @Setter
    private LocalDateTime endDate;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "car_owner_id")
    private User carOwner;
}
