package learningday.api.global.rest.dto.mapper;

import learningday.api.global.rest.dto.FruitDTO;
import learningday.data.entity.Fruit;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FruitMapper {


  FruitDTO boToDTO(Fruit fruit);

  Fruit DTOToBo(FruitDTO fruitDTO);

  Fruit DTOToBo(FruitDTO fruitDTO, long id);
}
