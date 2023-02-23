package learningday.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fruit", schema = "demo")
@Data
public class Fruit {

  @Id
  @SequenceGenerator(
      name = "fruitSequence",
      sequenceName = "demo.fruit_sequence",
      allocationSize = 1,
      initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fruitSequence")
  public Long id;

  public String name;
  public String description;


}