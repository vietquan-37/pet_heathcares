package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;
import vietquan37.com.example.projects.enumClass.WorkingDay;
import vietquan37.com.example.projects.utils.converter.WorkingDayListConverter;

import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String specialty;
    private String imageUrl;
    private LocalTime startTime;
    private LocalTime endTime;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<HospitalizedPet> hospitalizations;
    @Convert(converter = WorkingDayListConverter.class)
    private List<WorkingDay> workingDay;

}
