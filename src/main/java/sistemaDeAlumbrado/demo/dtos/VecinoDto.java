package sistemaDeAlumbrado.demo.dtos;

import lombok.Data;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Data
public class VecinoDto {
    private String dni;
    private String nombre;
    private String apellido;
    private String mail;
    private String telefono;
    private String nombreCalle;
    private String numeroCalle;
    private String manzana;
    private String lote;
    private LocalDate fechaReclamo;
    private Time horaReclamo;
    private String nombreBarrio;


    public VecinoDto (final UsuarioEntity usuario,
                      final LocalDate fechaReclamo,
                      final Time horaReclamo){
        this.dni = usuario.getDni();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.mail = usuario.getMail();
        this.telefono = usuario.getTelefono();
        this.nombreCalle = usuario.getNombreCalle();
        this.numeroCalle = usuario.getNumeroCalle();
        this.manzana = usuario.getManzana();
        this.lote = usuario.getLote();
        this.fechaReclamo = fechaReclamo;
        this.horaReclamo = horaReclamo;
        this.nombreBarrio = usuario.getBarrio().getNombre();
    }

    public VecinoDto (Object[] data){
        this.dni = (String) data[7];
        this.nombre = (String) data[8];
        this.apellido = (String) data[9];
        this.mail = (String) data[10];
        this.telefono = (String) data[11];
        this.nombreCalle = (String) data[12];
        this.numeroCalle = (String) data[13];
        this.manzana = (String) data[14];
        this.lote = (String) data[15];
        this.fechaReclamo = ((Date) data[16]).toLocalDate();
        this.horaReclamo = ((Time) data[17]);
    }
}
