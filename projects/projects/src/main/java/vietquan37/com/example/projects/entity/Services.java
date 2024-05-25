package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.ServiceTypes;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
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

}
