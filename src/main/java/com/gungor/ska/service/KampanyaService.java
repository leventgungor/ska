package com.gungor.ska.service;

import com.gungor.ska.dto.KampanyaDTO;
import com.gungor.ska.dto.KampanyaListesiResponseDTO;
import com.gungor.ska.enm.KampanyaDurum;
import com.gungor.ska.enm.KampanyaKategorisi;
import com.gungor.ska.entity.Kampanya;
import com.gungor.ska.exception.BusinessException;
import com.gungor.ska.mapper.KampanyaMapper;
import com.gungor.ska.repo.KampanyaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class KampanyaService {

    private final KampanyaRepo kampanyaRepo;

    private final KampanyaMapper kampanyaMapper;

    public KampanyaDTO kampanyaEkle(KampanyaDTO kampanyaDTO) {
        Kampanya kampanya = kampanyaMapper.kampanyaDTOToKampanya(kampanyaDTO);

        Optional<KampanyaKategorisi> kampanyaKategorisi = KampanyaKategorisi.isimdenKampanyaGetir(kampanyaDTO.getKategori());
        kampanya.setKategori(kampanyaKategorisi.orElseThrow(
                () -> new BusinessException("Geçerli Bir Kampanya Kategorisi Giriniz.", HttpStatus.BAD_REQUEST))
        );

        kampanya.setMukerrer(mukerrerKontrol(kampanya));
        kampanya.setDurum(kampanya.getKategori().kampanyaDurumBelirle());

        String logMesaji = "Kampanya " + kampanya.getId() + " " + LocalDateTime.now() + " tarihinde oluşturuldu.";
        kampanya.addChangelog(logMesaji);
        Kampanya kayitliKampanya = kampanyaRepo.save(kampanya);
        log.info(logMesaji);

        return kampanyaMapper.kampanyaToKampanyaDTO(kayitliKampanya);
    }

    public KampanyaDTO kampanyaAktiveEt(String kampanyaId){
        Optional<Kampanya> kampanyaOp = kampanyaRepo.findById(Long.valueOf(kampanyaId));
        if(kampanyaOp.isEmpty())
            throw new BusinessException("Aktive Etmek istediğiniz kampanya bulunamadı.", HttpStatus.NOT_FOUND);

        Kampanya kampanya = kampanyaOp.get();

        if(kampanya.getDurum() == KampanyaDurum.Aktif){
            throw new BusinessException("Kampanya zaten aktif durumdadır..", HttpStatus.ALREADY_REPORTED);
        }

        kampanya.setDurum(KampanyaDurum.Aktif);

        String logMesaji = "Kampanya " + kampanya.getId() + " " + LocalDateTime.now() + " tarihinde aktive edildi.";
        kampanya.addChangelog(logMesaji);
        Kampanya guncellenenKampanya = kampanyaRepo.save(kampanya);
        log.info(logMesaji);
        return kampanyaMapper.kampanyaToKampanyaDTO(guncellenenKampanya);
    }

    public KampanyaDTO kampanyaDeaktiveEt(String kampanyaId){
        Optional<Kampanya> kampanyaOp = kampanyaRepo.findById(Long.valueOf(kampanyaId));
        if(kampanyaOp.isEmpty())
            throw new BusinessException("Deaktive Etmek istediğiniz kampanya bulunamadı!", HttpStatus.NOT_FOUND);

        Kampanya kampanya = kampanyaOp.get();

        if(kampanya.getDurum() == KampanyaDurum.Deaktif){
            throw new BusinessException("Kampanya zaten deaktif durumdadır..", HttpStatus.ALREADY_REPORTED);
        }

        kampanya.setDurum(KampanyaDurum.Deaktif);

        String logMesaji = "Kampanya " + kampanya.getId() + " " + LocalDateTime.now() + " tarihinde deaktive edildi.";
        kampanya.addChangelog(logMesaji);
        Kampanya guncellenenKampanya = kampanyaRepo.save(kampanya);
        log.info(logMesaji);
        return kampanyaMapper.kampanyaToKampanyaDTO(guncellenenKampanya);
    }

    public KampanyaListesiResponseDTO tumKampayanlariListele(){
        Iterable<Kampanya> tumKampanyalar = kampanyaRepo.findAll();
        int aktifSayisi = kampanyaRepo.countAllByDurum(KampanyaDurum.Aktif);
        int deaktifAsayisi = kampanyaRepo.countAllByDurum(KampanyaDurum.Deaktif);
        int onayBekleyenSayisi = kampanyaRepo.countAllByDurum(KampanyaDurum.OnayBekliyor);

        KampanyaListesiResponseDTO response = new KampanyaListesiResponseDTO();
        response.setKampanyaList(StreamSupport.stream(tumKampanyalar.spliterator(), false).toList());
        response.setAktifKampanyaSayisi(aktifSayisi);
        response.setDeAktifKampanyaSayisi(deaktifAsayisi);
        response.setOnayBekleyenKampanyaSayisi(onayBekleyenSayisi);

        return response;
    }

    private boolean mukerrerKontrol(Kampanya kampanya) {
        List<Kampanya> result = kampanyaRepo.findByKategoriAndIlanBaslikAndDetayAciklamasi(kampanya.getKategori(), kampanya.getIlanBaslik(), kampanya.getDetayAciklamasi());
        return !result.isEmpty();
    }
}
