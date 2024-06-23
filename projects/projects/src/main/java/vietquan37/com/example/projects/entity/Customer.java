package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Pet> pets;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Review> reviews;
    private BigDecimal customer_balance;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Appointment> appointments;


}
