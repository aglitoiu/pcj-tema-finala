package ro.aglitoiu.tema_finala_pcj.AppointmentService.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.Entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
