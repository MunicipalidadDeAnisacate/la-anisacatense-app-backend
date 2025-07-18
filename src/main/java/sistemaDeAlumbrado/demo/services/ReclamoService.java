package sistemaDeAlumbrado.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sistemaDeAlumbrado.demo.dtos.*;
import sistemaDeAlumbrado.demo.dtos.solicitudes.ReclamoViveroItemDto;
import sistemaDeAlumbrado.demo.entities.Animal;
import sistemaDeAlumbrado.demo.entities.Barrio;
import sistemaDeAlumbrado.demo.entities.EstadoReclamo;
import sistemaDeAlumbrado.demo.entities.Poste;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.entities.ReclamoXTipoReparacion;
import sistemaDeAlumbrado.demo.entities.ReclamoXUsuario;
import sistemaDeAlumbrado.demo.entities.SubTipoReclamo;
import sistemaDeAlumbrado.demo.entities.TipoReparacion;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;
import sistemaDeAlumbrado.demo.repositories.ReclamoRepository;
import sistemaDeAlumbrado.demo.repositories.ReclamoXTipoReparacionRepository;
import sistemaDeAlumbrado.demo.repositories.TipoReparacionRepository;
import sistemaDeAlumbrado.demo.services.googleStorageService.PhotosStorageService;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReclamoService {
    private final ReclamoRepository reclamoRepository;
    private final TipoReparacionRepository tipoReparacionRepository;
    private final ReclamoXTipoReparacionRepository reclamoXTipoReparacionRepository;
    private final SubTipoReclamoService subTipoReclamoService;
    private final EstadoReclamoService estadoReclamoService;
    private final ReclamoXUsuarioService reclamoXUsuarioService;
    private final PosteService posteService;
    private final BarrioService barrioService;
    private final AnimalService animalService;
    private final UsuarioEntityService usuarioEntityService;
    private final ApplicationEventPublisher eventPublisher;
    private final TipoReparacionXReclamoService tipoReparacionXReclamoService;
    private final PhotosStorageService photosStorageService;


    public Page<ReclamoItemListDto> findAllReclamos(
            Integer barrioId,
            Integer tipoReclamoId,
            Integer subTipoId,
            Integer estadoId,
            Long tecnicoId,
            Integer fechaDesdeReclamo,
            LocalDate today,
            Integer fechaDesdeReparacion,
            Pageable pageable
    ) {
        LocalDate fechaDesdeReclamoSQL = null;
        if (fechaDesdeReclamo != null) {
            if (fechaDesdeReclamo.equals(1)) {
                fechaDesdeReclamoSQL = today;
            } else {
                fechaDesdeReclamoSQL = today.minusDays(fechaDesdeReclamo);
            }
        }

        LocalDate fechaDesdeReparacionSQL = null;
        if (fechaDesdeReparacion != null){
            if (fechaDesdeReparacion.equals(1)) {
                fechaDesdeReparacionSQL = today;
            } else {
                fechaDesdeReparacionSQL = today.minusDays(fechaDesdeReparacion);
            }
        }

        Page<Object[]> results = reclamoRepository.findAllReclamos(barrioId, tipoReclamoId,
                subTipoId, estadoId, tecnicoId, fechaDesdeReclamoSQL, fechaDesdeReparacionSQL, pageable);

        List<ReclamoItemListDto> reclamoItems = results.stream()
                .map(ReclamoItemListDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(reclamoItems, pageable, results.getTotalElements());
    }


    public Page<ReclamoViveroItemDto> findAllReclamosViveroXSubTipo(final Integer subTipoReclamoId,
                                                                    final Pageable pageable){
        return reclamoRepository.findAllReclamosViveroXSubTipo(subTipoReclamoId, pageable);
    }


    public ReclamoItemListDto findOneReclamo(final Long reclamoId){

        List<Object[]> results = reclamoRepository.findByIdReclamoItemList(reclamoId);

        if (results.isEmpty()){
            return null;
        }

        return new ReclamoItemListDto(results.get(0));
    }


    public Page<MisReclamosDto> findMyClaims2(Long idVecino, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        List<Object[]> reclamos = reclamoRepository.findReclamoVecino(idVecino, pageSize, offset);
        long totalElements = reclamoRepository.countByVecino(idVecino);

        List<MisReclamosDto> reclamosDto = reclamos.stream()
                .map(reclamo -> new MisReclamosDto(
                        ((Number) reclamo[0]).longValue(),
                        reclamo[1] != null ? (String) reclamo[1] : null,
                        (String) reclamo[2],
                        (String) reclamo[3],
                        reclamo[4] != null ? (String) reclamo[4] : null,
                        reclamo[5] != null ? ((Date) reclamo[5]).toLocalDate() : null,
                        reclamo[6] != null ? ((Time) reclamo[6]) : null,
                        (String) reclamo[7],
                        reclamo[8] != null ? (Double) reclamo[8] : null,
                        reclamo[9] != null ? (Double) reclamo[9] : null,
                        (String) reclamo[10],
                        reclamo[11] != null ? ((Date) reclamo[11]).toLocalDate() : null,
                        reclamo[12] != null ? ((Time) reclamo[12]) : null
                ))
                .toList();

        return new PageImpl<>(reclamosDto, pageable, totalElements);
    }


    public Page<Reclamo> findMisSolicitudesReparadas(final Long tecnicoId, final Pageable pageable) {
        UsuarioEntity tecnico = usuarioEntityService.findById(tecnicoId);

        if (!tecnico.getTipoUsuario().getNombreTipo().equals("tecnico")) {
            return new PageImpl<>(Collections.emptyList());
        }

        return reclamoRepository.findReclamosByTecnico1OrTecnico2(tecnico, tecnico, pageable);
    }


    public List<PosteDto> findMyActiveLuminariaClaims(Long vecinoId, Integer subTipoReclamoId){
        List<Object[]> resultNuevo = reclamoRepository.findLuminaria(vecinoId, subTipoReclamoId);

        return resultNuevo.stream()
                .map(poste -> {
                    return new PosteDto(
                            ((Number) poste[0]).intValue(),
                            (String) poste[1],
                            ((Number) poste[4]).intValue(),
                            (Double) poste[2],
                            (Double) poste[3] );
                })
                .toList();
    }


    public List<PosteDto> findMyLuminariaClaimsTecnico(final Integer subTipoReclamoId) {
        List<Object[]> postesSQL = reclamoRepository.findPostesReclamadosBySubTipoAndActivos(subTipoReclamoId);

        List<PosteDto> postes = postesSQL
                .stream()
                .map(PosteDto::new)
                .toList();


        for (PosteDto posteDto : postes) {
            if (posteDto.getEstadoPoste().equals(2)) {
                List<Object[]> vecinosReclamaronSQL = reclamoRepository.findReclamosDeLuminariaByPoste(posteDto.getIdPoste());

                Long nroReclamo = 0L;
                for (Object[] vecino : vecinosReclamaronSQL) {
                    nroReclamo = ((Number) vecino[5]).longValue();
                }

                Set<InfoReclamoLuminariaUsuarioDto> vecinosReclamaronDtoSet = vecinosReclamaronSQL
                                                                                    .stream()
                                                                                    .map(InfoReclamoLuminariaUsuarioDto::new)
                                                                                    .collect(Collectors.toSet());

                posteDto.setInformacionReclamo(nroReclamo, vecinosReclamaronDtoSet);
            }
        }

        return postes;
    }


    @Transactional
    public void createReclamo(final Long idVecino,
                              final Integer idPoste,
                              final Integer idAnimal,
                              final Integer idSubTipoReclamo,
                              final Integer idBarrio,
                              final MultipartFile fileFotoReclamo,
                              final Time horaReclamo,
                              final LocalDate fechaReclamo,
                              final Double latitud,
                              final Double longitud,
                              final String observacionReclamo) throws IOException {

        UsuarioEntity usuario = usuarioEntityService.findById(idVecino);

        if (!usuarioEntityService.esVecino(usuario) && !usuarioEntityService.esTecnico(usuario)){
            throw new RuntimeException("no es vecino ni tecnico");
        }

        SubTipoReclamo subTipoReclamo = subTipoReclamoService.findById(idSubTipoReclamo);

        EstadoReclamo estadoActivo = estadoReclamoService.findEstadoActivo();
        
        String objectName = null;
        if (fileFotoReclamo != null){
            objectName = this.loadPhoto(fileFotoReclamo, "solicitudVecino");
        }

        Poste poste = posteService.getOrNull(idPoste);

        if (idPoste != null){
            Optional<Reclamo> reclamoViejo = this.findReclamoDeLuminariaSinId(estadoActivo, subTipoReclamo, poste);
            if (reclamoViejo.isPresent()) {
                this.agregarVecinoAReclamo(reclamoViejo.get(), usuario, fechaReclamo, horaReclamo);
                return;
            }
        }

        Animal animal = animalService.getOrNull(idAnimal);

        Barrio barrio = barrioService.getOrNull(idBarrio);

        Reclamo nuevoReclamo = new Reclamo(subTipoReclamo, estadoActivo, barrio, latitud, longitud,
                 poste, animal, observacionReclamo, objectName);

        Reclamo reclamoGuardado = reclamoRepository.save(nuevoReclamo);

        this.agregarVecinoAReclamo(reclamoGuardado, usuario, fechaReclamo, horaReclamo);
    }


    @Transactional
    protected void agregarVecinoAReclamo(final Reclamo reclamo,
                                       final UsuarioEntity vecino,
                                       final LocalDate fechaReclamo,
                                       final Time horaReclamo){
        ReclamoXUsuario reclamoXUsuario = new ReclamoXUsuario(reclamo, vecino, fechaReclamo, horaReclamo);
        reclamoXUsuarioService.save(reclamoXUsuario);
    }


    private Optional<Reclamo> findReclamoDeLuminariaSinId(final EstadoReclamo estadoActivo,
                                              final SubTipoReclamo subTipoReclamo,
                                              final Poste poste){
        return reclamoRepository.findReclamoByEstadoReclamoAndSubTipoReclamoAndPosteLuz(
                estadoActivo, subTipoReclamo, poste
        );
    }


    @Transactional
    public void finalizeClaim(final Long idReclamo,
                              final Integer idSubTipoReclamo,
                              final Integer idPoste,
                              final Long tecnico1Id,
                              final Long tecnico2Id,
                              final LocalDate fechaArregloP,
                              final Time horaArregloP,
                              final List<Integer> tiposReparacionesIds,
                              final String observacionArreglo,
                              final MultipartFile fileFotoArreglo) throws IOException {


        UsuarioEntity tecnico1 = usuarioEntityService.findById(tecnico1Id);

        if (!usuarioEntityService.esTecnico(tecnico1)){
            throw new RuntimeException("El usuario no es tecnico");
        }

        UsuarioEntity tecnico2 = usuarioEntityService.getTecnicoOrNull(tecnico2Id);

        EstadoReclamo estadoResuelto = estadoReclamoService.findEstadoResuelto();

        Reclamo reclamo = null;

        if ( idPoste != null && idSubTipoReclamo != null ){
            Poste poste = posteService.findById(idPoste);

            EstadoReclamo estadoActivo = estadoReclamoService.findEstadoActivo();

            SubTipoReclamo subTipoReclamo = subTipoReclamoService.findById(idSubTipoReclamo);

            Optional<Reclamo> reclamoOptional = this.findReclamoDeLuminariaSinId(estadoActivo, subTipoReclamo, poste);

            if (reclamoOptional.isPresent()){
                reclamo = reclamoOptional.get();
                if (idSubTipoReclamo == 1) {
                    this.guardarReclamoLuzXReparaciones(reclamo, tiposReparacionesIds);
                }
            }
        }

        if (idReclamo != null) {
            reclamo = reclamoRepository.findById(idReclamo)
                    .orElseThrow(() -> new RuntimeException("reclamo not found"));
        }

        String objectName = null;
        if (fileFotoArreglo != null){
            objectName = loadPhoto(fileFotoArreglo, "solicitudResuelta");
        }

        if (reclamo != null){
            reclamo.finalizar(tecnico1, tecnico2, fechaArregloP, horaArregloP, estadoResuelto, observacionArreglo,
                    objectName);

            reclamoRepository.save(reclamo);

            eventPublisher.publishEvent(reclamo);
        } else {
            throw new RuntimeException("reclamo not found");
        }
    }


    @Transactional
    protected void guardarReclamoLuzXReparaciones(Reclamo reclamo, List<Integer> tiposReparacionesIds){
        for (Integer tipoReparacionId : tiposReparacionesIds){
            TipoReparacion tipoReparacion = tipoReparacionRepository.findById(tipoReparacionId)
                    .orElseThrow(() -> new RuntimeException("tipoReparacion not found"));
            ReclamoXTipoReparacion reclamoXTipoReparacion = new ReclamoXTipoReparacion(reclamo, tipoReparacion);
            reclamoXTipoReparacionRepository.save(reclamoXTipoReparacion);
        }
    }


    public List<SolicitudesVariasDto> getAllActiveClaimsExpandido(Integer idSubTipoReclamo){
        List<SolicitudesVariasDto> solicitudesVariasDtoList = new ArrayList<>();

        List<Object[]> solicitudes = reclamoRepository.getAllActiveClaimsBySubTipo(idSubTipoReclamo);

        for (Object[] solicitud : solicitudes) {
            SolicitudesVariasDto dto = new SolicitudesVariasDto(solicitud);
            solicitudesVariasDtoList.add(dto);
        }

        return solicitudesVariasDtoList;
    }


    public InformacionReclamoCompletaDto getInformacionReclamo(final Long reclamoId){

        Reclamo reclamoBuscado = reclamoRepository.findById(reclamoId)
                .orElseThrow(() -> new RuntimeException("reclamo not found"));

        List<ReclamoXUsuario> reclamoXUsuarios = reclamoXUsuarioService.getReclamoXUsuario(reclamoBuscado);

        Set<VecinoDto> vecinoDtoSet = new HashSet<>();
        reclamoXUsuarios.forEach(reclamoXUsuario -> {
            vecinoDtoSet.add(new VecinoDto(reclamoXUsuario.getReclamoXUsuarioId().getUsuario(),
                                            reclamoXUsuario.getFechaReclamo(),
                                            reclamoXUsuario.getHoraReclamo()));
        });

        List<TipoReparacion> tipoReparacionesRealizadas = new ArrayList<>();
        if (reclamoBuscado.getPosteLuz() != null){
            tipoReparacionesRealizadas = tipoReparacionXReclamoService.getAllFromReclamo(reclamoBuscado);
        }

        if (reclamoBuscado.getFotoReclamo() != null){
            reclamoBuscado.setFotoReclamo(getUrlPhoto(reclamoBuscado.getFotoReclamo()));
        }

        if (reclamoBuscado.getFotoArreglo() != null){
            reclamoBuscado.setFotoArreglo(getUrlPhoto(reclamoBuscado.getFotoArreglo()));
        }

        return new InformacionReclamoCompletaDto(reclamoBuscado, vecinoDtoSet, tipoReparacionesRealizadas);
    }


    private String loadPhoto(final MultipartFile file,
                           String carpeta) throws IOException {
        return photosStorageService.uploadPhoto(file, carpeta);
    }

    private String getUrlPhoto(String objectName){
        return photosStorageService.generateSignedUrl(objectName);
    }

}