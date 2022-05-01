package ro.aglitoiu.tema_finala_pcj.UserService.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.aglitoiu.tema_finala_pcj.UserService.DAO.UserRepository;
import ro.aglitoiu.tema_finala_pcj.UserService.Entities.User;

import java.util.Optional;

@Controller
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/")
    private ResponseEntity getAllUsers() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return ResponseEntity.ok(mapper.writeValueAsString(userRepository.findAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) throws JsonProcessingException {
        Optional userOpt= userRepository.findById(id);
        if(userOpt.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            User user = (User)userOpt.get();
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.ok(mapper.writeValueAsString(user));
        }
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody User user){
        try{
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity deleteUser(@PathVariable("id") Long id)
    {
        try{
            userRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update")
    private ResponseEntity updateUser(@RequestBody User user)
    {
        try{
            userRepository.delete(user);
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
