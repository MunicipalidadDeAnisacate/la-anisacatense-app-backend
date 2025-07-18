package sistemaDeAlumbrado.demo.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sistemaDeAlumbrado.demo.dtos.arreglos.ArregloDto;
import sistemaDeAlumbrado.demo.dtos.arreglos.InformacionArregloDto;
import sistemaDeAlumbrado.demo.entities.*;
import sistemaDeAlumbrado.demo.repositories.ArregloRepository;
import sistemaDeAlumbrado.demo.repositories.TipoReclamoRepository;
import sistemaDeAlumbrado.demo.services.googleStorageService.PhotosStorageService;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArregloService {
    private final ArregloRepository arregloRepository;
    private final TipoReclamoRepository tipoReclamoRepository;
    private final UsuarioEntityService usuarioEntityService;
    private final PosteService posteService;
    private final BarrioService barrioService;
    private final PhotosStorageService photosStorageService;


    public Page<ArregloDto> findAllArreglosXTipo(Integer barrioId,
                                                 Integer tipoId,
                                                 Long tecnicoId,
                                                 Integer fechaDesdeReparacion,
                                                 LocalDate today,
                                                 Pageable pageable){

        LocalDate fechaDesdeSQL = null;
        if (fechaDesdeReparacion != null) {
            if (fechaDesdeReparacion.equals(1)) {
                fechaDesdeSQL = today;
            } else {
                fechaDesdeSQL = today.minusDays(fechaDesdeReparacion);
            }
        }

        return arregloRepository.findAllByFilters(barrioId,
                tipoId,
                tecnicoId,
                fechaDesdeSQL,
                pageable);
    }

    public Arreglo findOneArreglo(Long id){
        return arregloRepository.findById(id).orElse(null);
    }

    public Page<Arreglo> getArreglosByTecnico(final Long tecnicoId, final Pageable pageable){
        UsuarioEntity tecnico = usuarioEntityService.findById(tecnicoId);

        if (!tecnico.getTipoUsuario().getNombreTipo().equals("tecnico")){
            throw new RuntimeException("tecnico not found");
        }

        // busca los arreglos donde el tecnico que busco el arreglo aparezca y los devuelve ordenados por fecha y hora
        return arregloRepository.findArreglosByTecnico1OrTecnico2(tecnico, tecnico, pageable);
    }


    public InformacionArregloDto findOneReparacionInformacion(final Long reparacionId){
        Optional<Arreglo> arregloOpt = arregloRepository.findById(reparacionId);

        if (arregloOpt.isEmpty()){
            throw new RuntimeException("reparacion not found");
        }

        Arreglo arreglo = arregloOpt.get();

        if (arreglo.getFotoArreglo() != null){
            arreglo.setFotoArreglo(this.getUrlPhoto(arreglo.getFotoArreglo()));
        }

        return new InformacionArregloDto(arreglo);
    }


    @Transactional
    public void createArreglo(final Integer tipoReclamoId, final Double latitudReclamo,
                              final Double longitudReclamo, final Long tecnico1Id,
                              final Long tecnico2Id, final LocalDate fechaArreglo,
                              final Time horaArreglo, final String observacionArreglo,
                              final MultipartFile fotoArreglo, final Integer posteId,
                              final Integer idBarrio) throws IOException {

        // busca el barrio
        Barrio barrio = barrioService.findBarrioById(idBarrio);

        // busca el tipo de reclamo
        TipoReclamo tipoReclamo = tipoReclamoRepository.findById(tipoReclamoId)
                .orElseThrow(() -> new RuntimeException("tipoReclamo no encontrado"));

        // busca a los tecnicos
        UsuarioEntity tecnico1 = usuarioEntityService.findById(tecnico1Id);
        if (!usuarioEntityService.esTecnico(tecnico1)){
            throw new RuntimeException("usuario no es tecnico");
        }

        String objectName=null;
        if (fotoArreglo != null){
            objectName = this.loadPhoto(fotoArreglo);
        }

        UsuarioEntity tecnico2 = usuarioEntityService.getTecnicoOrNull(tecnico2Id);

        Poste poste = posteService.getOrNull(posteId);

        Arreglo nuevoArreglo = new Arreglo(tipoReclamo, latitudReclamo, longitudReclamo, tecnico1, tecnico2,
                fechaArreglo, horaArreglo, observacionArreglo, objectName, barrio, poste);

        arregloRepository.save(nuevoArreglo);
    }


    private String loadPhoto(final MultipartFile file) throws IOException {
        return photosStorageService.uploadPhoto(file, "reparaciones");
    }


    private String getUrlPhoto(String objectName){
        return photosStorageService.generateSignedUrl(objectName);
    }
}
