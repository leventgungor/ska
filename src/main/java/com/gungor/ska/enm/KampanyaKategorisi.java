package com.gungor.ska.enm;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum KampanyaKategorisi {

    TSS("Tamamlayıcı Sağlık Sigortası", 1),
    OSS("Özel Sağlık Sigortası", 2),
    HS("Hayat Sigortası", 3),
    D("Diğer", 4);

    private final String isim;

    private final int no;

    private static final Map<String, KampanyaKategorisi> isimler = Arrays.stream(KampanyaKategorisi.values()).collect(Collectors.toMap(KampanyaKategorisi::getIsim, Function.identity()));

    public static Optional<KampanyaKategorisi> isimdenKampanyaGetir(final String name) { return Optional.ofNullable(isimler.get(name));}

    KampanyaKategorisi(String isim, int no) {
        this.isim = isim;
        this.no = no;
    }

    public KampanyaDurum getKampanyaDurumu(){
        return switch (this) {
            case TSS, OSS, D:
                yield  KampanyaDurum.OnayBekliyor;
            case HS:
                yield KampanyaDurum.Aktif;
        };
    }

    public String toString() {
        return this.no + " - " + this.isim ;
    }

}
