package com.isharipov.counterpartyfinder.mapper;

import com.isharipov.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import com.isharipov.counterpartyfinder.data.network.model.Branch;
import com.isharipov.counterpartyfinder.data.network.model.Data;
import com.isharipov.counterpartyfinder.data.network.model.DataAnswer;
import com.isharipov.counterpartyfinder.data.network.model.State;
import com.isharipov.counterpartyfinder.data.network.model.Status;
import com.isharipov.counterpartyfinder.data.network.model.Type;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;

/**
 * 09.03.2018.
 */
@Mapper
public abstract class DataAnswerToDataAnswerDtoMapper {
    private static final String DEFAULT_VALUE = "-";
    public static DataAnswerToDataAnswerDtoMapper INSTANCE = Mappers.getMapper(DataAnswerToDataAnswerDtoMapper.class);

    @Mappings({
            @Mapping(source = "value", target = "value", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "unrestrictedValue", target = "unrestrictedValue", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.kpp", target = "kpp", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.capital", target = "capital", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.branchCount", target = "branchCount", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.source", target = "source", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.qc", target = "qc", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.hid", target = "hid", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.inn", target = "inn", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.ogrn", target = "ogrn", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.okpo", target = "okpo", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.okved", target = "okved", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.okveds", target = "okveds"),
            @Mapping(source = "data.authorities", target = "authorities"),
            @Mapping(source = "data.documents", target = "documents"),
            @Mapping(source = "data.licenses", target = "licenses"),
            @Mapping(source = "data.phones", target = "phones"),
            @Mapping(source = "data.emails", target = "emails"),
            @Mapping(source = "data.okvedType", target = "okvedType", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.opf.type", target = "opfType", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.opf.code", target = "opfCode", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.opf.full", target = "opfFull", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.opf.shortName", target = "opfShortName", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.name.fullWithOpf", target = "nameFullWithOpf", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.name.shortWithOpf", target = "nameShortWithOpf", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.name.latin", target = "nameLatin", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.name.full", target = "nameFull", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.name.shortName", target = "nameShortName", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.address.value", target = "addressValue", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.address.unrestrictedValue", target = "addressUnrestrictedValue", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.address.data.geoLat", target = "location.geoLat"),
            @Mapping(source = "data.address.data.geoLon", target = "location.geoLon"),
            @Mapping(source = "data.management.name", target = "managementName", defaultValue = DEFAULT_VALUE),
            @Mapping(source = "data.management.post", target = "managementPost", defaultValue = DEFAULT_VALUE),
            @Mapping(target = "branchType", ignore = true, defaultValue = DEFAULT_VALUE),
            @Mapping(target = "stateActualityDate", ignore = true, defaultValue = DEFAULT_VALUE),
            @Mapping(target = "stateStatus", ignore = true, defaultValue = DEFAULT_VALUE),
            @Mapping(target = "stateRegistrationDate", ignore = true, defaultValue = DEFAULT_VALUE),
            @Mapping(target = "stateLiquidationDate", ignore = true, defaultValue = DEFAULT_VALUE),
            @Mapping(target = "ogrnDate", ignore = true, defaultValue = DEFAULT_VALUE),
            @Mapping(target = "tapDate", ignore = true, defaultValue = DEFAULT_VALUE),
            @Mapping(target = "isFavorite", ignore = true, defaultValue = DEFAULT_VALUE),
            @Mapping(target = "type", ignore = true, defaultValue = DEFAULT_VALUE)

    })
    public abstract DataAnswerDto map(DataAnswer answer);

    public abstract List<DataAnswerDto> map(List<DataAnswer> answers);

    public abstract RealmList<String> mapListToRealmList(List<String> list);

    @AfterMapping
    void convertLongToDateString(DataAnswer answer, @MappingTarget DataAnswerDto dto) {
        Data data = answer.getData();
        if (data != null) {
            dto.setOgrnDate(convertLongToDateString(answer.getData().getOgrnDate()));
            State state = data.getState();
            if (state != null) {
                dto.setStateStatus(Status.valueOf(state.getStatus()).getDescription());
                dto.setStateActualityDate(convertLongToDateString(state.getActualityDate()));
                dto.setStateRegistrationDate(convertLongToDateString(state.getRegistrationDate()));
                dto.setStateLiquidationDate(convertLongToDateString(state.getLiquidationDate()));
            }
            String branchType = data.getBranchType();
            if (branchType != null) {
                dto.setBranchType(Branch.valueOf(answer.getData().getBranchType()).getDescription());
            }
            dto.setType(Type.valueOf(answer.getData().getType()).getDescription());
        }
    }

    private Date convertLongToDateString(Long time) {
        return (time != null) ? new Date(time) : null;
    }
}