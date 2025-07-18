package sistemaDeAlumbrado.demo.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.dtos.consultasCiudadanas.InformacionConsultaActivaDto;
import sistemaDeAlumbrado.demo.dtos.proyectos.ProyectoEnConsultaDto;
import sistemaDeAlumbrado.demo.entities.ConsultaCiudadana;
import sistemaDeAlumbrado.demo.entities.ConsultaXProyecto;
import sistemaDeAlumbrado.demo.entities.Proyecto;
import sistemaDeAlumbrado.demo.repositories.ConsultaCiudadanaRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ConsultaCiudadanaService {
    private final ConsultaCiudadanaRepository consultaRepository;
    private final ProyectoService proyectoService;


    public Page<ConsultaCiudadana> findAll(Pageable pageable) {
        return consultaRepository.findAll(pageable);
    }


    public ConsultaCiudadana findById(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta ciudadana no encontrada"));
    }


    public List<ConsultaCiudadana> findConsultasActivas(LocalDate today, LocalTime nowLocalTime){
        Time now = Time.valueOf(nowLocalTime);
        return consultaRepository.findAllConsultasDisponibles(today, now);
    }


    public InformacionConsultaActivaDto findInformacionConsultaCiudadana(final Long id){
        ConsultaCiudadana consulta = findById(id);

        InformacionConsultaActivaDto informacionConsulta = new InformacionConsultaActivaDto(consulta);

        List<ProyectoEnConsultaDto> proyectos = proyectoService.getProyectosDeConsulta(id);
        informacionConsulta.addProyectos(proyectos);

        return informacionConsulta;
    }


    public InformacionConsultaActivaDto findConsultaActiva(Long consultaId, Long usuarioId){
        ConsultaCiudadana consulta = findById(consultaId);

        InformacionConsultaActivaDto informacionConsulta = new InformacionConsultaActivaDto(consulta);

        Long proyectoIDVotado = consultaRepository.proyectoIDPreferido(consultaId, usuarioId);
        informacionConsulta.setProyectoIdVotado(proyectoIDVotado);

        List<ProyectoEnConsultaDto> proyectos = proyectoService.getProyectosDeConsulta(consultaId);

        if (!consulta.getMostrarRecuento()){
            proyectos.forEach(p -> p.setCantidad(null));
        }

        informacionConsulta.addProyectos(proyectos);

        return informacionConsulta;
    }


    @Transactional
    public void createConsulta(String titulo,
                               String descripcion,
                               LocalDate fechaInicio,
                               LocalTime horaInicio,
                               LocalDate fechaCierre,
                               LocalTime horaCierre,
                               Boolean mostrarRecuento,
                               List<Long> proyectosId) {

        ConsultaCiudadana consulta = new ConsultaCiudadana(
                titulo,
                descripcion,
                fechaInicio,
                Time.valueOf(horaInicio),
                fechaCierre,
                Time.valueOf(horaCierre),
                mostrarRecuento
        );
        consultaRepository.save(consulta);

        List<Proyecto> proyectos = proyectoService.findAllByIdIn(proyectosId);

        // Crear enlaces usando referencias gestionadas
        proyectos.forEach(proyecto -> {
            ConsultaXProyecto enlace = new ConsultaXProyecto();
            enlace.setConsultaCiudadana(consulta);
            enlace.setProyecto(proyecto);
            consulta.addProyecto(enlace);
        });
        // 3. Al salvar la elección, por cascade ALL también se persisten los enlaces
    }

    @Transactional
    public void patchMostrarRecuentoConsulta(final Long id){
        ConsultaCiudadana consulta = findById(id);

        Boolean actual = consulta.getMostrarRecuento();
        consulta.setMostrarRecuento(!actual);

        consultaRepository.save(consulta);
    }

    @Transactional
    public void patchCerrarConsulta(final Long id,
                                    final LocalDate today,
                                    final LocalTime now){
        ConsultaCiudadana consulta = findById(id);

        consulta.setHoraCierre(Time.valueOf(now));
        consulta.setFechaCierre(today);

        consultaRepository.save(consulta);
    }
}

