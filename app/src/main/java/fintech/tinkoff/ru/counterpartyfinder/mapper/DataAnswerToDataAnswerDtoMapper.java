package fintech.tinkoff.ru.counterpartyfinder.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

import fintech.tinkoff.ru.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.Branch;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.Data;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.DataAnswer;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.State;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.Status;
import fintech.tinkoff.ru.counterpartyfinder.data.network.model.Type;
import io.realm.RealmList;

/**
 * 09.03.2018.
 */
@Mapper
public abstract class DataAnswerToDataAnswerDtoMapper {
    public static DataAnswerToDataAnswerDtoMapper INSTANCE = Mappers.getMapper(DataAnswerToDataAnswerDtoMapper.class);

    @Mappings({
            @Mapping(source = "data.kpp", target = "kpp"),
            @Mapping(source = "data.capital", target = "capital"),
            @Mapping(source = "data.branchCount", target = "branchCount"),
            @Mapping(source = "data.source", target = "source"),
            @Mapping(source = "data.qc", target = "qc"),
            @Mapping(source = "data.hid", target = "hid"),
            @Mapping(source = "data.inn", target = "inn"),
            @Mapping(source = "data.ogrn", target = "ogrn"),
            @Mapping(source = "data.okpo", target = "okpo"),
            @Mapping(source = "data.okved", target = "okved"),
            @Mapping(source = "data.okveds", target = "okveds"),
            @Mapping(source = "data.authorities", target = "authorities"),
            @Mapping(source = "data.documents", target = "documents"),
            @Mapping(source = "data.licenses", target = "licenses"),
            @Mapping(source = "data.phones", target = "phones"),
            @Mapping(source = "data.emails", target = "emails"),
            @Mapping(source = "data.okvedType", target = "okvedType"),
            @Mapping(source = "data.opf.type", target = "opfType"),
            @Mapping(source = "data.opf.code", target = "opfCode"),
            @Mapping(source = "data.opf.full", target = "opfFull"),
            @Mapping(source = "data.opf.shortName", target = "opfShortName"),
            @Mapping(source = "data.name.fullWithOpf", target = "nameFullWithOpf"),
            @Mapping(source = "data.name.shortWithOpf", target = "nameShortWithOpf"),
            @Mapping(source = "data.name.latin", target = "nameLatin"),
            @Mapping(source = "data.name.full", target = "nameFull"),
            @Mapping(source = "data.name.shortName", target = "nameShortName"),
            @Mapping(source = "data.address.value", target = "addressValue"),
            @Mapping(source = "data.address.unrestrictedValue", target = "addressUnrestrictedValue"),

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

    private String convertLongToDateString(Long time) {
        return (time != null) ? new Date(time).toString() : null;
    }
}