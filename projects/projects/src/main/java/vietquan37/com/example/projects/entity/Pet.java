package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;
import vietquan37.com.example.projects.enumClass.Gender;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
    private String name;
    private String species;
    private Gender gender;
    private Date birthDate;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<HospitalizedPet> hospitalizations;
}
