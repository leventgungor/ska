package com.gungor.ska.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gungor.ska.dto.KampanyaRequestDTO;
import com.gungor.ska.entity.Kampanya;
import com.gungor.ska.repo.KampanyaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
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

        Optional<Kampanya> byId = kampanyaRepo.findById(1L);
        Assertions.assertNotNull(byId);
        Assertions.assertAll(
                () -> Assertions.assertEquals(byId.get().getIlanBaslik(),kampanyaRequestDTO.getIlanBaslik()),
                () -> Assertions.assertEquals(byId.get().getDetayAciklamasi(),kampanyaRequestDTO.getDetayAciklamasi()),
                () -> Assertions.assertEquals(byId.get().getKategori().getIsim(),kampanyaRequestDTO.getKategori())

        );

    }

    @Test
    
}
