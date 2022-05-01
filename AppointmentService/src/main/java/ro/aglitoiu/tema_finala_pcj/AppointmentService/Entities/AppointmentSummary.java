package ro.aglitoiu.tema_finala_pcj.AppointmentService.Entities;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor @NoArgsConstructor @Builder
@Entity
@Table(name = "appointment_summary", schema = "public")
public class AppointmentSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(unique=true)
    private Long id;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private User mechanic;
    @Getter @Setter
    private String comment;
    @Getter @Setter
    private Double totalCost;
}
