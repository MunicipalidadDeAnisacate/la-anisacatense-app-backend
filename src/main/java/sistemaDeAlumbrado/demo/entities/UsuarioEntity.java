package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "usuario")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(name = "contraseña", nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id", nullable = false)
    private TipoUsuario tipoUsuario;

    @ManyToOne
    @JoinColumn(name = "cuadrilla_id")
    private Cuadrilla cuadrilla;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "telefono", nullable = false, unique = true)
    private String telefono;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "barrio_id", nullable = false)
    private Barrio barrio;

    @Column(name = "nombre_calle")
    private String nombreCalle;

    @Column(name = "numero_calle")
    private String numeroCalle;

    @Column(name = "manzana")
    private String manzana;

    @Column(name = "lote")
    private String lote;

    @Column(name = "latitud_domicilio")
    private Double latitudeDomicilio;

    @Column(name = "longitud_domicilio")
    private Double longitudeDomicilio;


    public UsuarioEntity(){
        super();
    }

    public UsuarioEntity(String nombre, String apellido, String mail, String dni, String password,
                         TipoUsuario tipoUsuario, String telefono, LocalDate fechaNacimiento,
                         Barrio barrio, String nombreCalle,
                         String numeroCalle, String manzana, String lote,
                         Double latitudeDomicilio, Double longitudeDomicilio,
                         Cuadrilla cuadrilla){
        super();
        this.dni = dni;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.tipoUsuario = tipoUsuario;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.barrio = barrio;
        this.nombreCalle = nombreCalle;
        this.numeroCalle = numeroCalle;
        this.manzana = manzana;
        this.lote = lote;
        this.latitudeDomicilio = latitudeDomicilio;
        this.longitudeDomicilio = longitudeDomicilio;
        this.cuadrilla = cuadrilla;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.getNombreTipo()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.dni;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
