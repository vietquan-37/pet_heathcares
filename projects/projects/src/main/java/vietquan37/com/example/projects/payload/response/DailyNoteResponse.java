package vietquan37.com.example.projects.payload.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyNoteResponse {
    private Integer id;
    private String note;
    private LocalDate date;

}
