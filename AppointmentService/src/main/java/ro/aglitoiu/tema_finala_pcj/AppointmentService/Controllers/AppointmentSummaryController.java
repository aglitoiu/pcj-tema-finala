package ro.aglitoiu.tema_finala_pcj.AppointmentService.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.DAO.AppointmentRepository;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.DAO.AppointmentSummaryRepository;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.Entities.Appointment;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.Entities.AppointmentSummary;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.Entities.User;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.Forms.AppointmentSummaryForm;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/appointment-sum")
public class AppointmentSummaryController {
    private final AppointmentSummaryRepository appointmentSummaryRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentSummaryController(AppointmentSummaryRepository appointmentSummaryRepository, AppointmentRepository appointmentRepository) {
        this.appointmentSummaryRepository = appointmentSummaryRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/")
    private ResponseEntity getAllSummaries() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return ResponseEntity.ok(mapper.writeValueAsString(appointmentSummaryRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getSummary(@PathVariable Long id) throws JsonProcessingException {
        Optional summaryOpt = appointmentSummaryRepository.findById(id);
        if (summaryOpt.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            AppointmentSummary summary = (AppointmentSummary) summaryOpt.get();
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return ResponseEntity.ok(mapper.writeValueAsString(summary));
        }
    }

    @PostMapping("/create")
    public ResponseEntity createSummary(@RequestBody AppointmentSummaryForm summaryForm) {

        AppointmentSummary summary = new AppointmentSummary();
        summary.setId(summaryForm.getId());
        summary.setComment(summaryForm.getComment());
        summary.setTotalCost(summaryForm.getTotalCost());
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", summaryForm.getMechanicId());
        try {
            Appointment appointment = appointmentRepository.getById(summaryForm.getAppointmentId());
            ResponseEntity<User> responseEntity =
                    new RestTemplate().getForEntity(
                            "http://localhost:8080/userservice/{userId}", User.class, uriVariables);
            User mechanic = responseEntity.getBody();
            summary.setAppointment(appointment);
            summary.setMechanic(mechanic);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        try {
            appointmentSummaryRepository.save(summary);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity deleteSummary(@PathVariable("id") Long id) {
        try {
            appointmentSummaryRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update")
    private ResponseEntity updateSummary(@RequestBody AppointmentSummary summary) {
        try {
            appointmentSummaryRepository.delete(summary);
            appointmentSummaryRepository.save(summary);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
