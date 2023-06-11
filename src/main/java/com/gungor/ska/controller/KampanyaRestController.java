package com.gungor.ska.controller;

import com.gungor.ska.dto.response.APIResponse;
import com.gungor.ska.dto.response.KampanyaListesiResponseDTO;
import com.gungor.ska.entity.Kampanya;
import com.gungor.ska.dto.request.KampanyaDurumRequestDTO;
import com.gungor.ska.dto.request.KampanyaRequestDTO;
import com.gungor.ska.service.KampanyaService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/ekle")
    @Operation(summary = "Yeni kampanya ekler, aynı kampanya daha önce eklenmişse mükerrer olarak ekler")
    public ResponseEntity<APIResponse<Kampanya>> kampanyaEkle(@Valid @RequestBody KampanyaRequestDTO requestDTO) {
        Kampanya kayitEdilenKampanya= kampanyaService.kampanyaEkle(requestDTO);

        APIResponse<Kampanya> responseDTO = APIResponse
                .<Kampanya>builder()
                .status("Kampanya başarıyla oluşturuldu.")
                .results(kayitEdilenKampanya)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/aktive-et")
    @Operation(summary = "Daha önce eklenmiş olan kampanyayı aktive eder")
    public ResponseEntity<APIResponse<Kampanya>> kampanyaAktiveEt(@RequestBody KampanyaDurumRequestDTO requestDTO) {
        Kampanya response = kampanyaService.kampanyaAktiveEt(requestDTO);

        APIResponse<Kampanya> responseDTO = APIResponse
                .<Kampanya>builder()
                .status("Kampanya başarıyla aktive edildi.")
                .results(response)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/deaktive-et")
    @Operation(summary = "Daha önce eklenmiş olan kampanyayı deaktive eder")
    public ResponseEntity<APIResponse<Kampanya>> kampanyaDeaktiveEt(@RequestBody KampanyaDurumRequestDTO requestDTO) {
        Kampanya response = kampanyaService.kampanyaDeaktiveEt(requestDTO);

        APIResponse<Kampanya> responseDTO = APIResponse
                .<Kampanya>builder()
                .status("Kampanya başarıyla deaktive edildi.")
                .results(response)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
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