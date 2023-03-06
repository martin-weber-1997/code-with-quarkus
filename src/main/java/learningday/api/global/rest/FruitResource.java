package learningday.api.global.rest;

import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
  public Set<FruitDTO> list() {
    log.info("request to list all fruits");
    return fruitService.listAll()
        .stream()
        .map(fruitMapper::boToDTO)
        .collect(Collectors.toSet());

  }

  @GET
  @Path("/search")
  public FruitDTO findByName(@QueryParam("name") String name) {
    log.info("request to find fruit by name: {}", name);
    return fruitMapper.boToDTO(fruitService.findByName(name));
  }


  @GET
  @Path("/{id}")
  public FruitDTO get(@PathParam("id") Long id) {
    return fruitMapper.boToDTO(fruitService.findById(id));
  }

  @POST
  @Transactional
  public Response add(FruitDTO fruitDTO) {
    fruitService.save(fruitMapper.DTOToBo(fruitDTO));


    return Response.ok(fruitDTO)
        .status(201)
        .build();
  }

  @PUT
  @Transactional
  @Path("/{id}")
  public FruitDTO update(FruitDTO fruitDTO, @PathParam("id") Long id) {
    return fruitMapper.boToDTO(fruitService.update(fruitMapper.DTOToBo(fruitDTO), id));
  }

  @DELETE
  @Transactional
  @Path("/{id}")
  public Response delete(Fruit fruit, @PathParam("id") Long id) {
    fruitService.deleteById(id);
    ;
    return Response.status(204)
        .build();
  }
}
