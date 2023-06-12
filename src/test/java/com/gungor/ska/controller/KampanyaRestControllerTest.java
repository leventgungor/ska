package com.gungor.ska.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gungor.ska.dto.KampanyaDTO;
import com.gungor.ska.dto.KampanyaListesiDTO;
import com.gungor.ska.dto.KampanyaRequestDTO;
import com.gungor.ska.mapper.KampanyaMapper;
import com.gungor.ska.service.KampanyaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Tag("unit")
public class KampanyaRestControllerTest {

    @MockBean
    private KampanyaService kampanyaService;

    @MockBean
    private KampanyaMapper kampanyaMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private KampanyaDTO kampanyaDTO;

    private KampanyaDTO updateKampanyaDTO;

    private KampanyaRequestDTO kampanyaRequestDTO;

    private KampanyaListesiDTO kampanyaListesiResponseDTO;


    @BeforeEach
    void setup() {
        kampanyaDTO = new KampanyaDTO();
        kampanyaDTO.setKampanyaId(1L);
        kampanyaDTO.setIlanBaslik("Allianz Tamamlayıcı %10 İndirim Kampanyası");
        kampanyaDTO.setDetayAciklamasi("Tamamlayıcı Sağlık Sigortası indirimiyle geldi! Sonbahara sağlık sigortasıyla girmek için Allianz Tamamlayıcı Sağlık Sigortası’nda %10 indirim sizi bekliyor.");
        kampanyaDTO.setKategori("Tamamlayıcı Sağlık Sigortası");
        kampanyaDTO.setDurum("OnayBekliyor");

        updateKampanyaDTO = new KampanyaDTO();
        updateKampanyaDTO.setKampanyaId(1L);
        updateKampanyaDTO.setIlanBaslik("Allianz Tamamlayıcı %10 İndirim Kampanyası");
        updateKampanyaDTO.setDetayAciklamasi("Tamamlayıcı Sağlık Sigortası indirimiyle geldi! Sonbahara sağlık sigortasıyla girmek için Allianz Tamamlayıcı Sağlık Sigortası’nda %10 indirim sizi bekliyor.");
        updateKampanyaDTO.setKategori("Tamamlayıcı Sağlık Sigortası");
        updateKampanyaDTO.setDurum("Aktif");

        kampanyaRequestDTO = new KampanyaRequestDTO();
        kampanyaRequestDTO.setIlanBaslik("Allianz Tamamlayıcı %10 İndirim Kampanyası");
        kampanyaRequestDTO.setDetayAciklamasi("Tamamlayıcı Sağlık Sigortası indirimiyle geldi! Sonbahara sağlık sigortasıyla girmek için Allianz Tamamlayıcı Sağlık Sigortası’nda %10 indirim sizi bekliyor.");
        kampanyaRequestDTO.setKategori("Tamamlayıcı Sağlık Sigortası");
    }

    @Test
    void givenKampanyaRequestDTO_whenKampanyaOlustur_thenReturnKampanyaDTO() throws Exception {
        // given
        given(kampanyaService.kampanyaOlustur(any(KampanyaDTO.class)))
                .willReturn(kampanyaDTO);
        given(kampanyaMapper.kampanyaRequestDTOtoKampanyaDTO(any(KampanyaRequestDTO.class)))
                .willReturn(kampanyaDTO);

        // when
        ResultActions response = mockMvc.perform(post("/kampanya/olustur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(kampanyaRequestDTO)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.results.ilanBaslik", is(kampanyaRequestDTO.getIlanBaslik())))
                .andExpect(jsonPath("$.results.detayAciklamasi", is(kampanyaRequestDTO.getDetayAciklamasi())))
                .andExpect(jsonPath("$.results.kategori", is(kampanyaRequestDTO.getKategori())));

    }

    @Test
    void givenKampanyaId_whenKampanyaAktiveEt_thenReturnKampanyaDTO() throws Exception {
        // given
        given(kampanyaService.kampanyaAktiveEt(any(Long.class))).willReturn(kampanyaDTO);

        // when
        ResultActions response = mockMvc.perform(get("/kampanya/aktive-et?kampanyaId={kampanyaId}", 1L).contentType(MediaType.APPLICATION_JSON));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.ilanBaslik", is(kampanyaDTO.getIlanBaslik())))
                .andExpect(jsonPath("$.results.detayAciklamasi", is(kampanyaDTO.getDetayAciklamasi())))
                .andExpect(jsonPath("$.results.kategori", is(kampanyaDTO.getKategori())))
                .andExpect(jsonPath("$.results.durum", is(kampanyaDTO.getDurum())));

    }

    @Test
    void givenKampanyaId_whenKampanyaDeaktiveEt_thenReturnKampanyaDTO() throws Exception {
        // given
        given(kampanyaService.kampanyaDeaktiveEt(any(Long.class))).willReturn(kampanyaDTO);

        // when
        ResultActions response = mockMvc.perform(get("/kampanya/deaktive-et?kampanyaId={kampanyaId}", 1L).contentType(MediaType.APPLICATION_JSON));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.ilanBaslik", is(kampanyaDTO.getIlanBaslik())))
                .andExpect(jsonPath("$.results.detayAciklamasi", is(kampanyaDTO.getDetayAciklamasi())))
                .andExpect(jsonPath("$.results.kategori", is(kampanyaDTO.getKategori())))
                .andExpect(jsonPath("$.results.durum", is(kampanyaDTO.getDurum())));

    }

    @Test
    void whenKampanyaListele_thenReturnKampanyaListesiResponseDTO() throws Exception {
        //given
        kampanyaListesiResponseDTO = new KampanyaListesiDTO();
        kampanyaListesiResponseDTO.setDeAktifKampanyaSayisi(1);
        kampanyaListesiResponseDTO.setOnayBekleyenKampanyaSayisi(0);
        kampanyaListesiResponseDTO.setDeAktifKampanyaSayisi(0);
        kampanyaListesiResponseDTO.setKampanyaList(Collections.singletonList(kampanyaDTO));

        //when
        given(kampanyaService.tumKampayanlariListele()).willReturn(kampanyaListesiResponseDTO);

        ResultActions response = mockMvc.perform(get("/kampanya/kampanyalari-listele").contentType(MediaType.APPLICATION_JSON));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.aktifKampanyaSayisi", is(kampanyaListesiResponseDTO.getAktifKampanyaSayisi())))
                .andExpect(jsonPath("$.results.deAktifKampanyaSayisi", is(kampanyaListesiResponseDTO.getDeAktifKampanyaSayisi())))
                .andExpect(jsonPath("$.results.onayBekleyenKampanyaSayisi", is(kampanyaListesiResponseDTO.getOnayBekleyenKampanyaSayisi())));
                //.andExpect(jsonPath("$.results.kampanyaList", is(kampanyaListesiResponseDTO.getKampanyaList())));
        //TODO bunun için bir json dönüşümü gerekebilir
    }


}
