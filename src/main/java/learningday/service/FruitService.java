package learningday.service;

import io.smallrye.mutiny.Uni;
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
  public Uni<List<Fruit>> listAll() {
    return fruitRepository.listAll();
  }

  public Uni<Fruit> findById(Long id) {
    return fruitRepository.findById(id);
  }

  public Uni<Fruit> save(Fruit fruit) {
    return fruitRepository.persistAndFlush(fruit)
        .onItem()
        .transform(v -> v);
  }

  public Uni<Fruit> update(Fruit fruit) {
    return fruitRepository.findById(fruit.id)
        .onItem()
        .ifNull()
        .failWith(() -> new NotFoundException("Unknown fruit id : " + fruit.id))
        .invoke(fruitRepository::persistAndFlush);
  }

  public Uni<Fruit> deleteById(Long id) {
    return fruitRepository.findById(id)
        .onItem()
        .ifNull()
        .failWith(() -> new NotFoundException("Unknown fruit id : " + id))
        .call(fruitRepository::delete);

  }

  public Fruit findByName(String name) {
    return fruitRepository.findByName(name)
        .orElseThrow(NotFoundException::new);
  }


}
