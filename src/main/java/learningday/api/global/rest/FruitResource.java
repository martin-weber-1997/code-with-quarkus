package learningday.api.global.rest;

import static javax.ws.rs.core.Response.Status.NO_CONTENT;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import learningday.api.global.rest.dto.FruitDTO;
import learningday.api.global.rest.dto.mapper.FruitMapper;
import learningday.data.entity.Fruit;
import learningday.service.FruitService;
import lombok.extern.slf4j.Slf4j;


@Path("/fruit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j

public class FruitResource {



  @Inject
  FruitMapper fruitMapper;

  @Inject
  FruitService fruitService;

  @GET
  public Uni<Collection<FruitDTO>> list() {
    log.info("request to list all fruits");
    return fruitService.listAll()
        .onItem()
        .transform(fruits -> fruits.stream()
            .map(fruitMapper::boToDTO)
            .collect(java.util.stream.Collectors.toList()));
  }

  @GET
  @Path("/search")
  public FruitDTO findByName(@QueryParam("name") String name) {
    log.info("request to find fruit by name: {}", name);
    return fruitMapper.boToDTO(fruitService.findByName(name));
  }


  @GET
  @Path("/{id}")
  public Uni<FruitDTO> get(@PathParam("id") Long id) {
    return fruitService.findById(id)
        .onItem()
        .transform(fruitMapper::boToDTO);
  }

  @POST
  @ReactiveTransactional
  public Uni<FruitDTO> add(FruitDTO fruitDTO) {
    return fruitService.save(fruitMapper.DTOToBo(fruitDTO))
        .onItem()
        .transform(v -> fruitMapper.boToDTO(v));
  }

  @PUT
  @ReactiveTransactional
  @Path("/{id}")
  public Uni<FruitDTO> update(FruitDTO fruitDTO, @PathParam("id") Long id) {
    return fruitService.findById(id)
        .onItem()
        .transform(fruit -> fruitMapper.DTOToBo(fruitDTO, id))
        .flatMap(fruit -> fruitService.save(fruit))
        .onItem()
        .transform(v -> fruitMapper.boToDTO(v));

  }

  @DELETE
  @ReactiveTransactional
  @Path("/{id}")
  public Uni<Response> delete(Fruit fruit, @PathParam("id") Long id) {
    return fruitService.deleteById(id)
        .replaceWith(Response.ok()
            .status(NO_CONTENT)::build);
  }
}
