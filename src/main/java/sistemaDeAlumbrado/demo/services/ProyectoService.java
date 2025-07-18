package sistemaDeAlumbrado.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sistemaDeAlumbrado.demo.dtos.proyectos.InfoProyectoDto;
import sistemaDeAlumbrado.demo.dtos.proyectos.ProyectoEnConsultaDto;
import sistemaDeAlumbrado.demo.entities.Proyecto;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;
import sistemaDeAlumbrado.demo.repositories.ProyectoRepository;
import sistemaDeAlumbrado.demo.services.googleStorageService.FilesStorageService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProyectoService {
    private final ProyectoRepository proyectoRepository;
    private final UsuarioEntityService usuarioEntityService;
    private final FilesStorageService filesStorageService;


    public Page<Proyecto> getAll(Pageable pageable){
        return proyectoRepository.findAll(pageable);
    }

    public Proyecto findById(Long id){
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto not found"));
    }

    public Proyecto getReferenceById(Long id){
        return proyectoRepository.getReferenceById(id);
    }


    public List<Proyecto> findAllByIdIn(List<Long> ids){
        List<Proyecto> proyectos = new ArrayList<>();

        for (Long id : ids){
            proyectos.add(proyectoRepository.getReferenceById(id));
        }

        return proyectos;
    }


    public InfoProyectoDto getProyecto(Long id){
        Proyecto proyecto = this.findById(id);

        if (proyecto.getObjectName() != null){
            proyecto.setObjectName(filesStorageService.generateSignedUrl(proyecto.getObjectName()));
        }

        return new InfoProyectoDto(proyecto);
    }


    public List<ProyectoEnConsultaDto> getProyectosDeConsulta(final Long consultaId){
        List<ProyectoEnConsultaDto> proyectos = proyectoRepository
                .findProyectosEnConsultaDtoByConsulta(consultaId);

        proyectos.forEach(p -> {
            if ( p.getObjectName() != null ) {
                p.setArchivoUrl(filesStorageService.generateSignedUrl(p.getObjectName()));
            }
        });

        return proyectos;
    }


    @Transactional
    public void createProyecto(final String titulo,
                               final String descripcion,
                               final MultipartFile archivo,
                               final Long usuarioId) throws IOException {

        UsuarioEntity usuario = usuarioEntityService.findById(usuarioId);

        String objectName = null;
        if (archivo != null) {
           objectName = filesStorageService.uploadFile(archivo);
        }

        Proyecto proyecto = new Proyecto(titulo, descripcion, objectName, usuario);

        proyectoRepository.save(proyecto);
    }


    @Transactional
    public void deleteProyecto(Long proyectoId, LocalDate today) {
        boolean bloqueado = proyectoRepository
                .existsProyectoInActiveOrFutureElection(proyectoId);

        if (bloqueado) {
            throw new IllegalStateException(
                    "No se puede eliminar: proyecto asignado a consulta"
            );
        }

        this.eliminarArchivoAsociado(proyectoId);

        proyectoRepository.deleteById(proyectoId);
    }


    private void eliminarArchivoAsociado(Long proyectoId){
        Proyecto proyecto = this.getReferenceById(proyectoId);

        if (proyecto.getObjectName() != null){
            boolean archivoEliminado = filesStorageService.deleteFileByUrl(proyecto.getObjectName());
            if (!archivoEliminado){
                throw new RuntimeException("Problemas con la eliminacion del archivo");
            }
        }
    }

}
