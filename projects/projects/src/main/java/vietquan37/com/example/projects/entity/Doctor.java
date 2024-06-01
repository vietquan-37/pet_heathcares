package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.WorkingDay;
import vietquan37.com.example.projects.utils.converter.WorkingDayListConverter;


import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String specialty;
    private String imageUrl;
    @OneToOne
    private User user;
    private LocalTime start_time;
    private LocalTime end_time;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<HospitalizedPet> hospitalizations;
    @Convert(converter = WorkingDayListConverter.class)
    private List<WorkingDay> workingDay;

}
