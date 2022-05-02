package ro.aglitoiu.tema_finala_pcj.AppointmentService.Forms;

import lombok.Getter;
import lombok.Setter;

public class AppointmentSummaryForm {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private Long appointmentId;
    @Getter @Setter
    private Long mechanicId;
    @Getter @Setter
    private String comment;
    @Getter @Setter
    private Double totalCost;
}

