package com.gungor.ska.controller;

import com.gungor.ska.dto.KampanyaDTO;
import com.gungor.ska.dto.KampanyaListesiResponseDTO;
import com.gungor.ska.dto.KampanyaRequestDTO;
import com.gungor.ska.dto.global.APIResponse;
import com.gungor.ska.mapper.KampanyaMapper;
import com.gungor.ska.service.KampanyaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kampanya")
@Tag(name = "Sigorta Kampanyası Servisleri")
public class KampanyaRestController {

    private static final String SUCCESS = "SUCCESS";

    private final KampanyaService kampanyaService;

    private final KampanyaMapper kampanyaMapper;

    @PostMapping("/ekle")
    @Operation(summary = "Yeni kampanya ekler, aynı kampanya daha önce eklenmişse mükerrer olarak ekler")
    public ResponseEntity<APIResponse<KampanyaDTO>> kampanyaEkle(@Valid @RequestBody KampanyaRequestDTO requestDTO) {
        KampanyaDTO kampanyaDTO = kampanyaMapper.kampanyaRequestDTOtoKampanyaDTO(requestDTO);
        KampanyaDTO kayitEdilenKampanya= kampanyaService.kampanyaEkle(kampanyaDTO);

        APIResponse<KampanyaDTO> apiResponse = APIResponse
                .<KampanyaDTO>builder()
                .status("Kampanya başarıyla oluşturuldu.")
                .results(kayitEdilenKampanya)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/aktive-et")
    @Operation(summary = "Daha önce eklenmiş olan kampanyayı aktive eder")
    public ResponseEntity<APIResponse<KampanyaDTO>> kampanyaAktiveEt(@Schema(example = "1") @RequestParam String kampanyaId) {
        KampanyaDTO kampanyaDTO = kampanyaService.kampanyaAktiveEt(kampanyaId);

        APIResponse<KampanyaDTO> apiResponse = APIResponse
                .<KampanyaDTO>builder()
                .status("Kampanya başarıyla aktive edildi.")
                .results(kampanyaDTO)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/deaktive-et")
    @Operation(summary = "Daha önce eklenmiş olan kampanyayı deaktive eder")
    public ResponseEntity<APIResponse<KampanyaDTO>> kampanyaDeaktiveEt(@Schema(example = "1") @RequestParam String kampanyaId) {
        KampanyaDTO kampanyaDTO = kampanyaService.kampanyaDeaktiveEt(kampanyaId);

        APIResponse<KampanyaDTO> apiResponse = APIResponse
                .<KampanyaDTO>builder()
                .status("Kampanya başarıyla deaktive edildi.")
                .results(kampanyaDTO)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/listele")
    @Operation(summary = "Tüm kampanyaların detaylı listesini ve durum bilgisi istatiklerini gösterir")
    public ResponseEntity<APIResponse<KampanyaListesiResponseDTO>> tumKampanyalariListele() {
        KampanyaListesiResponseDTO response = kampanyaService.tumKampayanlariListele();

        APIResponse<KampanyaListesiResponseDTO> apiResponse = APIResponse
                .<KampanyaListesiResponseDTO>builder()
                .status("Tüm kampanyalar başarıyla listelendi.")
                .results(response)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}