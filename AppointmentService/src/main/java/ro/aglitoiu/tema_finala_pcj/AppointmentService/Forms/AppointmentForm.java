package ro.aglitoiu.tema_finala_pcj.AppointmentService.Forms;

import lombok.*;
import java.time.LocalDateTime;

public class AppointmentForm {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String car;
    @Getter @Setter
    private LocalDateTime startDate;
    @Getter @Setter
    private LocalDateTime endDate;
    @Getter @Setter
    private Long carOwnerId;
}

