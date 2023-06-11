package com.gungor.ska.service;

import com.gungor.ska.dto.response.KampanyaListesiResponseDTO;
import com.gungor.ska.enm.KampanyaDurum;
import com.gungor.ska.enm.KampanyaKategorisi;
import com.gungor.ska.entity.Kampanya;
import com.gungor.ska.repo.KampanyaRepo;
import com.gungor.ska.dto.request.KampanyaDurumRequestDTO;
import com.gungor.ska.dto.request.KampanyaRequestDTO;
import com.gungor.ska.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    public Kampanya kampanyaEkle(KampanyaRequestDTO request) {
        Kampanya kampanya = new Kampanya();
        Optional<KampanyaKategorisi> kampanyaKategorisi = KampanyaKategorisi.isimdenKampanyaGetir(request.getKategori());
        kampanya.setKategori(kampanyaKategorisi.orElseThrow(
                () -> new BusinessException("Geçerli Bir Kampanya Kategorisi Giriniz.", HttpStatus.BAD_REQUEST))
        );
        kampanya.setIlanBaslik(request.getIlanBaslik());
        kampanya.setDetayAciklamasi(request.getDetayAciklamasi());

        boolean isMukerrer= mukerrerKontrol(kampanya);
        kampanya.setMukerrer(isMukerrer);

        kampanya.setDurum(kampanya.getKategori().kampanyaDurumBelirle());

        log.info("Kampanya başarıyla kaydedildi.");
        kampanya.addChangelog("Kampanya " + LocalDateTime.now() + " tarihinde oluşturuldu.");
        return kampanyaRepo.save(kampanya);
    }

    public Kampanya kampanyaAktiveEt(KampanyaDurumRequestDTO requestDTO){
        Optional<Kampanya> kampanyaOptional = kampanyaRepo.findById(requestDTO.getKampanyaID());
        if(kampanyaOptional.isEmpty())
            throw new BusinessException("Aktive Etmek istediğiniz kampanya bulunamadı.", HttpStatusCode.valueOf(404));

        Kampanya kampanya = kampanyaOptional.get();
        kampanya.setDurum(KampanyaDurum.Aktif);

        kampanya.addChangelog("Kampanya " + LocalDateTime.now() + " tarihinde aktive edildi.");
        Kampanya guncellenenKampanya = kampanyaRepo.save(kampanya);
        log.info("Kampanya başarıyla aktive edildi.");
        return guncellenenKampanya;
    }

    public Kampanya kampanyaDeaktiveEt(KampanyaDurumRequestDTO requestDTO){
        Optional<Kampanya> kampanyaOptional = kampanyaRepo.findById(requestDTO.getKampanyaID());
        if(kampanyaOptional.isEmpty())
            throw new BusinessException("Deaktive Etmek istediğiniz kampanya bulunamadı!", HttpStatusCode.valueOf(404));

        Kampanya kampanya = kampanyaOptional.get();
        kampanya.setDurum(KampanyaDurum.Deaktif);
        kampanya.addChangelog("Kampanya " + LocalDateTime.now() + " tarihinde deaktive edildi.");
        Kampanya guncellenenKampanya = kampanyaRepo.save(kampanya);
        log.info("Kampanya "+ requestDTO.getKampanyaID() +" başarıyla deaktive edildi.");
        return guncellenenKampanya;
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
