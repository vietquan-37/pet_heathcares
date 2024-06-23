package vietquan37.com.example.projects.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import vietquan37.com.example.projects.enumClass.Gender;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetResponse {
    private Integer id;
    private String name;
    private String species;
    private Gender gender;
    private Date birthDate;
    private String imageUrl;

    private String ownerName;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
