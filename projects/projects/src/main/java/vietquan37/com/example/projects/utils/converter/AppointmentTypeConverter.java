package vietquan37.com.example.projects.utils.converter;

import jakarta.persistence.Converter;
import vietquan37.com.example.projects.enumClass.AppointmentType;
import vietquan37.com.example.projects.utils.EnumListConverter;
@Converter
public class AppointmentTypeConverter extends EnumListConverter<AppointmentType> {
    public AppointmentTypeConverter() {
        super(AppointmentType.class);
    }
}
