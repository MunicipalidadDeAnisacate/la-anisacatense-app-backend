package sistemaDeAlumbrado.demo.response.localsFormatter;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalsFormatter {

    public static String formatearFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fecha.format(formatter);
    }

    public static String formatearHora(LocalTime hora) {
        if (hora == null) {
            throw new IllegalArgumentException("La hora no puede ser nula");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return hora.format(formatter);
    }

    public static String formatearHora(Time horaSQL) {
        if (horaSQL == null) {
            throw new IllegalArgumentException("La hora no puede ser nula");
        }

        // Convertir java.sql.Time a LocalTime
        LocalTime hora = horaSQL.toLocalTime();

        // Formatear
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return hora.format(formatter);
    }
}
