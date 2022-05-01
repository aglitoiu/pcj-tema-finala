package ro.aglitoiu.tema_finala_pcj.UserService.Classes;

import ro.aglitoiu.tema_finala_pcj.UserService.Entities.User;

import java.time.LocalDateTime;

public class Appointment {
    String id;
    String car;
    LocalDateTime startDate;
    LocalDateTime endDate;
    User carOwner;
}
