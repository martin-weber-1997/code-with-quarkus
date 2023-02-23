package learningday.data.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import learningday.data.entity.Fruit;

@ApplicationScoped
public class FruitRepository implements PanacheRepositoryBase<Fruit,
    Long> {
  public Optional<Fruit> findByName(String name) {
    return find("name", name).firstResult().await().asOptional()
        .indefinitely();
  }
}