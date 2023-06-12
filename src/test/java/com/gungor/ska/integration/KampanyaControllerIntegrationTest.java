package com.gungor.ska.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gungor.ska.dto.KampanyaRequestDTO;
import com.gungor.ska.enm.KampanyaDurum;
import com.gungor.ska.enm.KampanyaKategorisi;
import com.gungor.ska.entity.Kampanya;
import com.gungor.ska.repo.KampanyaRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Tag("integration")
public class KampanyaControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    KampanyaRepo kampanyaRepo;

    @BeforeEach
    void setup() {
        kampanyaRepo.deleteAll();
    }

    @AfterEach
    void tearDown(){
        kampanyaRepo.deleteAll();
    }

    @Test
    void givenKampanyaRequestDTO_whenKampanyaOlustur_thenReturnKampanyaDTO() throws Exception {

        //given
        KampanyaRequestDTO kampanyaRequestDTO = new KampanyaRequestDTO();
        kampanyaRequestDTO.setIlanBaslik("Allianz Tamamlayıcı %10 İndirim Kampanyası");
        kampanyaRequestDTO.setDetayAciklamasi("Tamamlayıcı Sağlık Sigortası indirimiyle geldi! Sonbahara sağlık sigortasıyla girmek için Allianz Tamamlayıcı Sağlık Sigortası’nda %10 indirim sizi bekliyor.");
        kampanyaRequestDTO.setKategori("Tamamlayıcı Sağlık Sigortası");

        //when
        ResultActions response = mockMvc.perform(post("/kampanya/olustur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(kampanyaRequestDTO)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.results.ilanBaslik", is(kampanyaRequestDTO.getIlanBaslik())))
                .andExpect(jsonPath("$.results.detayAciklamasi", is(kampanyaRequestDTO.getDetayAciklamasi())))
                .andExpect(jsonPath("$.results.kategori", is(kampanyaRequestDTO.getKategori())));
    }

    @Test
    void givenKampanyaId_whenKampanyaAktiveEt_thenReturnKampanyaDTO() throws Exception {
        //given
        Kampanya kampanya1 = new Kampanya();
        kampanya1.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanya1.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanya1.setKategori(KampanyaKategorisi.D);
        kampanya1.setDurum(KampanyaDurum.OnayBekliyor);
        kampanya1.setMukerrer(false);

        Kampanya savedKampanya = kampanyaRepo.save(kampanya1);

        //when
        ResultActions response = mockMvc.perform(get("/kampanya/aktive-et?kampanyaId={id}", savedKampanya.getId()).contentType(MediaType.APPLICATION_JSON));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.ilanBaslik", is(kampanya1.getIlanBaslik())))
                .andExpect(jsonPath("$.results.detayAciklamasi", is(kampanya1.getDetayAciklamasi())))
                .andExpect(jsonPath("$.results.kategori", is(kampanya1.getKategori().getIsim())))
                .andExpect(jsonPath("$.results.durum", is("Aktif")));
    }

    @Test
    void givenKampanyaId_whenKampanyaDeaktiveEt_thenReturnKampanyaDTO() throws Exception {
        //given
        Kampanya kampanya1 = new Kampanya();
        kampanya1.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanya1.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanya1.setKategori(KampanyaKategorisi.D);
        kampanya1.setDurum(KampanyaDurum.OnayBekliyor);
        kampanya1.setMukerrer(false);

        Kampanya savedKampanya = kampanyaRepo.save(kampanya1);

        //when
        ResultActions response = mockMvc.perform(get("/kampanya/deaktive-et?kampanyaId={id}", savedKampanya.getId()).contentType(MediaType.APPLICATION_JSON));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.ilanBaslik", is(kampanya1.getIlanBaslik())))
                .andExpect(jsonPath("$.results.detayAciklamasi", is(kampanya1.getDetayAciklamasi())))
                .andExpect(jsonPath("$.results.kategori", is(kampanya1.getKategori().getIsim())))
                .andExpect(jsonPath("$.results.durum", is("Deaktif")));
    }

    @Test
    void givenKampanya_whenKampanyaListele_thenReturnKampanyaListesiDTO() throws Exception {
        //given
        Kampanya kampanya1 = new Kampanya();
        kampanya1.setIlanBaslik("Yolcu360 Ek İndirim Kampanyası");
        kampanya1.setDetayAciklamasi("Allianz müşterileri yolcu360.com ve mobil uygulaması  üzerinden günlük  1-29 gün araç kiralamada indirimli fiyatlar üzerinden %10 ek indirim fırsatından yararlanıyor!");
        kampanya1.setKategori(KampanyaKategorisi.D);
        kampanya1.setDurum(KampanyaDurum.OnayBekliyor);
        kampanya1.setMukerrer(false);

        kampanyaRepo.save(kampanya1);

        //when
        ResultActions response = mockMvc.perform(get("/kampanya/kampanyalari-listele")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.aktifKampanyaSayisi", is(0)))
                .andExpect(jsonPath("$.results.deAktifKampanyaSayisi", is(0)))
                .andExpect(jsonPath("$.results.onayBekleyenKampanyaSayisi", is(1)))
                .andExpect(jsonPath("$.results.kampanyaList.size()", is(1)));
    }

}
