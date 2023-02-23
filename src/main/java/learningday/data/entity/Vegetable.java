package learningday.data.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "vegetable", schema = "demo")
@Data
public class Vegetable extends PanacheEntity {
  public String name;
  public String color;
}