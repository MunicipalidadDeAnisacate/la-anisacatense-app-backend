package sistemaDeAlumbrado.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.dtos.ResultadoEleccionDto;
import sistemaDeAlumbrado.demo.dtos.proyectos.ProyectoEnConsultaDto;
import sistemaDeAlumbrado.demo.entities.ConsultaCiudadana;
import sistemaDeAlumbrado.demo.entities.ConsultaXProyecto;
import sistemaDeAlumbrado.demo.entities.Proyecto;
import sistemaDeAlumbrado.demo.entities.PreferenciaConsultaCiudadana;
import sistemaDeAlumbrado.demo.repositories.PreferenciaRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PreferenciaService {
    private final PreferenciaRepository preferenciaRepository;
    private final ConsultaCiudadanaService consultaCiudadanaService;
    private final ProyectoService proyectoService;
    private final UsuarioEntityService usuarioEntityService;
    private final SimpMessagingTemplate messagingTemplate;


    @Transactional
    public void addPreferenciaToConsulta(Long consultaId,
                                  Long proyectoId,
                                  Long usuarioId,
                                  LocalDate fechaPref,
                                  Time horaPref) {
        ConsultaCiudadana consulta = consultaCiudadanaService.findById(consultaId);

        LocalDateTime ahora = LocalDateTime.of(fechaPref, horaPref.toLocalTime());

        if (ahora.isBefore(consulta.getFechaHoraInicio()) || ahora.isAfter(consulta.getFechaHoraCierre())) {
            throw new IllegalStateException("La consulta no está activa");
        }

        boolean pertenece = consulta.getProyectos().stream()
                .anyMatch(ep -> ep.getProyecto().getId().equals(proyectoId));
        if (!pertenece) {
            throw new IllegalArgumentException("Proyecto no pertenece a esta consulta ciudadana");
        }

        boolean yaParticipo = preferenciaRepository.existsByConsultaCiudadanaIdAndUsuarioId(consultaId, usuarioId);
        if (yaParticipo) {
            throw new IllegalStateException("Usuario ya eligio una preferencia en esta consulta");
        }

        PreferenciaConsultaCiudadana preferencia = new PreferenciaConsultaCiudadana();
        preferencia.setConsultaCiudadana(consulta);
        preferencia.setProyecto(proyectoService.getReferenceById(proyectoId));
        preferencia.setUsuario(usuarioEntityService.getReferenceById(usuarioId));
        preferencia.setFechaPrefrencia(fechaPref);
        preferencia.setHoraPreferencia(horaPref);

        preferenciaRepository.save(preferencia);

        if (consulta.getMostrarRecuento()){
            this.sendPreferencia(consulta);
        }
    }


    private void sendPreferencia(ConsultaCiudadana consulta){

        List<ProyectoEnConsultaDto> proyectos = this.calcularPreferenciasXProyecto(consulta);

        ResultadoEleccionDto resultado = ResultadoEleccionDto.builder()
                .consultaId(consulta.getId())
                .proyectos(proyectos)
                .build();

        messagingTemplate.convertAndSend(
                "/topic/consulta/" + consulta.getId(), resultado
        );
    }


    private List<ProyectoEnConsultaDto> calcularPreferenciasXProyecto(ConsultaCiudadana consulta){
        List<ProyectoEnConsultaDto> proyectoEnConsultaDtos = new ArrayList<>();

        List<Proyecto> proyectos = consulta.getProyectos()
                .stream()
                .map(ConsultaXProyecto::getProyecto)
                .toList();


        for (Proyecto proyecto : proyectos){
            Long preferencias = preferenciaRepository.countPreferenciasDeProyectoEnConsulta(proyecto.getId(), consulta.getId());
            proyectoEnConsultaDtos.add(new ProyectoEnConsultaDto(proyecto.getId(), preferencias));
        }

        return proyectoEnConsultaDtos;
    }
}
