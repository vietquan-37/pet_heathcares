package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.CageStatus;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int CageNumber;
    @Enumerated(EnumType.STRING)
    private CageStatus cageStatus;
    @OneToMany(mappedBy = "cage", cascade = CascadeType.ALL)
    private List<HospitalizedPet> hospitalizations;
}
