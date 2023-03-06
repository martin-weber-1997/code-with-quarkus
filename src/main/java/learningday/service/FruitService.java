package learningday.service;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import learningday.data.entity.Fruit;
import learningday.data.repository.FruitRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class FruitService {
  @Inject
  FruitRepository fruitRepository;


  @PermitAll
  public List<Fruit> listAll() {
    return fruitRepository.listAll();
  }

  public Fruit findById(Long id) {
    return fruitRepository.findById(id);
  }

  public void save(Fruit fruit) {

    fruitRepository.persistAndFlush(fruit);

  }

  public Fruit update(Fruit fruit, Long id) {
    var entity = fruitRepository.findById(id);
    if (entity == null) {
      throw new NotFoundException("Unknown fruit id : " + fruit.id);
    }
    entity.name = fruit.name;
    entity.description = fruit.description;
    fruitRepository.persistAndFlush(entity);
    return entity;

  }

  public void deleteById(Long id) {
    var entity = fruitRepository.findById(id);
    if (entity == null) {
      throw new NotFoundException("Unknown fruit id : " + id);
    }
    deleteById(id);

  }

  public Fruit findByName(String name) {
    return fruitRepository.findByName(name)
        .orElseThrow(NotFoundException::new);
  }


}
