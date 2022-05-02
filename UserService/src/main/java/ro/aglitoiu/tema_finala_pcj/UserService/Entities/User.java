package ro.aglitoiu.tema_finala_pcj.UserService.Entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor @NoArgsConstructor @Builder
@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(unique=true)
    private Long id;
    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String lastName;
    @Getter @Setter @Column(unique=true)
    private String email;
    @Getter @Setter
    private String address;
    @Getter @Setter
    private String type;
}