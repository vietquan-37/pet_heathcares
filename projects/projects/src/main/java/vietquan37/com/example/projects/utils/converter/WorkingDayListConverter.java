package vietquan37.com.example.projects.utils.converter;
import jakarta.persistence.Converter;
import vietquan37.com.example.projects.enumClass.WorkingDay;
import vietquan37.com.example.projects.utils.EnumListConverter;

@Converter
public class WorkingDayListConverter extends EnumListConverter<WorkingDay> {
    public WorkingDayListConverter() {
        super(WorkingDay.class);
    }
}