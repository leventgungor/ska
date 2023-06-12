package com.gungor.ska.controller;

import com.gungor.ska.dto.KampanyaDTO;
import com.gungor.ska.dto.KampanyaListesiDTO;
import com.gungor.ska.dto.KampanyaRequestDTO;
import com.gungor.ska.dto.global.APIResponse;
import com.gungor.ska.enm.KampanyaKategorisi;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kampanya")
@Tag(name = "Sigorta Kampanyası Servisleri")
public class KampanyaController {

    private final KampanyaService kampanyaService;

    private final KampanyaMapper kampanyaMapper;

    @PostMapping("/olustur")
    @Operation(summary = "Yeni kampanya oluşturur, aynı kampanya daha önce oluşturulmuşsa mükerrer olarak kaydeder")
    public ResponseEntity<APIResponse<KampanyaDTO>> kampanyaOlustur(@Valid @RequestBody KampanyaRequestDTO requestDTO) {
        KampanyaDTO kampanyaDTO = kampanyaMapper.kampanyaRequestDTOtoKampanyaDTO(requestDTO);
        KampanyaDTO kayitEdilenKampanya= kampanyaService.kampanyaOlustur(kampanyaDTO);

        APIResponse<KampanyaDTO> apiResponse = APIResponse
                .<KampanyaDTO>builder()
                .status("Kampanya başarıyla oluşturuldu.")
                .results(kayitEdilenKampanya)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/aktive-et")
    @Operation(summary = "Daha önce eklenmiş olan kampanyayı aktive eder")
    public ResponseEntity<APIResponse<KampanyaDTO>> kampanyaAktiveEt(@Schema(example = "1") @RequestParam Long kampanyaId) {
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
    public ResponseEntity<APIResponse<KampanyaDTO>> kampanyaDeaktiveEt(@Schema(example = "1") @RequestParam Long kampanyaId) {
        KampanyaDTO kampanyaDTO = kampanyaService.kampanyaDeaktiveEt(kampanyaId);

        APIResponse<KampanyaDTO> apiResponse = APIResponse
                .<KampanyaDTO>builder()
                .status("Kampanya başarıyla deaktive edildi.")
                .results(kampanyaDTO)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/kampanyalari-listele")
    @Operation(summary = "Tüm kampanyaların detaylı listesini ve durum bilgisi istatiklerini gösterir")
    public ResponseEntity<APIResponse<KampanyaListesiDTO>> tumKampanyalariListele() {
        KampanyaListesiDTO response = kampanyaService.tumKampayanlariListele();

        APIResponse<KampanyaListesiDTO> apiResponse = APIResponse
                .<KampanyaListesiDTO>builder()
                .status("Tüm kampanyalar başarıyla listelendi.")
                .results(response)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/kategorileri-listele")
    @Operation(summary = "Kampanya kategorilerini getirir")
    public ResponseEntity<APIResponse<List<String>>> kampanyaKategorileriniListele(){
        List<String> degerler = Arrays.stream(KampanyaKategorisi.values()).map(KampanyaKategorisi::toString).collect(Collectors.toList());

        APIResponse<List<String>> apiResponse = APIResponse
                .<List<String>>builder()
                .status("Kampanya kategorileri başarıyla listelendi.")
                .results(degerler)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/durum-degisikligi-listele")
    @Operation(summary = "Kampanyaya ait durum değişikliklerinin listesini getirir")
    public ResponseEntity<APIResponse<List<String>>> kampanyaDurumDegisikleriListele(@Schema(example = "1") @RequestParam Long kampanyaId) {
        List<String> changeLog = kampanyaService.getDurumDegisiklikleri(kampanyaId);

        APIResponse<List<String>> apiResponse = APIResponse
                .<List<String>>builder()
                .status("Kampanyanın durum değişiklikleri başarıyla listelendi.")
                .results(changeLog)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
     }
}