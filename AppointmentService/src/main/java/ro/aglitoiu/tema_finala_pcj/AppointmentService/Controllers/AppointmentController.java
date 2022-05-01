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
import org.springframework.web.util.UriComponents;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.DAO.AppointmentRepository;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.Entities.Appointment;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.Entities.User;
import ro.aglitoiu.tema_finala_pcj.AppointmentService.Forms.AppointmentForm;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    @GetMapping("/")
    private ResponseEntity getAllAppointments() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return ResponseEntity.ok(mapper.writeValueAsString(appointmentRepository.findAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity getAppointment(@PathVariable Long id) throws JsonProcessingException {
        Optional appointmentOpt= appointmentRepository.findById(id);
        if(appointmentOpt.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            Appointment appointment = (Appointment)appointmentOpt.get();
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return ResponseEntity.ok(mapper.writeValueAsString(appointment));
        }
    }

    @PostMapping("/create")
    public ResponseEntity createAppointment(@RequestBody AppointmentForm appointmentForm){
        Appointment appointment = new Appointment();
        appointment.setId(appointmentForm.getId());
        appointment.setCar(appointmentForm.getCar());
        appointment.setStartDate(appointmentForm.getStartDate());
        appointment.setEndDate(appointmentForm.getEndDate());
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("userId", appointmentForm.getCarOwnerId());
        try {
            ResponseEntity<User> responseEntity =
                    new RestTemplate().getForEntity(
                            "http://localhost:8080/userservice/{userId}", User.class, uriVariables);

            User carOwner = responseEntity.getBody();
            appointment.setCarOwner(carOwner);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        try{
            appointmentRepository.save(appointment);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity deleteAppointment(@PathVariable("id") Long id)
    {
        try{
            appointmentRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update")
    private ResponseEntity updateAppointment(@RequestBody Appointment appointment)
    {
        try{
            // Not doing same approach as create, keeping in mind update would directly feed User entity as json, not as Id
            appointmentRepository.delete(appointment);
            appointmentRepository.save(appointment);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
