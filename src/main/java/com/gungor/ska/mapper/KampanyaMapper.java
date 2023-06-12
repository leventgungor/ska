package com.gungor.ska.mapper;

import com.gungor.ska.dto.KampanyaDTO;
import com.gungor.ska.dto.KampanyaRequestDTO;
import com.gungor.ska.entity.Kampanya;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel= MappingConstants.ComponentModel.SPRING)
public interface KampanyaMapper {

    @Mapping(source = "kampanyaId", target = "id")
    @Mapping(target = "kategori", ignore = true)
    @Mapping(target = "changeLogs", ignore = true)
    Kampanya kampanyaDTOToKampanya(KampanyaDTO kampanyaDTO);

    @Mapping(source = "id", target = "kampanyaId")
    @Mapping(source = "kategori.isim", target = "kategori")
    KampanyaDTO kampanyaToKampanyaDTO(Kampanya kampanya);

    @Mapping(source = "kampanyaId", target = "id")
    @Mapping(target = "kategori", ignore = true)
    @Mapping(target = "changeLogs", ignore = true)
    List<KampanyaDTO> kampanyasToKampanyaDTOs(List<Kampanya> kampanyas);

    KampanyaDTO kampanyaRequestDTOtoKampanyaDTO(KampanyaRequestDTO kampanyaRequestDTO);
}
