package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;
import vietquan37.com.example.projects.enumClass.ServiceTypes;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ServiceTypes type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HospitalizedPetServices> hospitalizedPetServices;

}
