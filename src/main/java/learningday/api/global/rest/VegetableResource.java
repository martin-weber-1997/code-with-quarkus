package learningday.api.global.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import learningday.data.entity.Vegetable;


@Path("/vegetables")
public class VegetableResource {

  @POST
  @Consumes(APPLICATION_JSON)
  public Uni<Response> create(Vegetable vegetable) {
    return Vegetable.persist(vegetable)
        .onItem()
        .transform(persistedVegetable -> Response.status(Response.Status.CREATED)
            .entity(persistedVegetable)
            .build());
  }

  @GET
  @Path("/{id}")
  @Produces(APPLICATION_JSON)
  public Uni<Vegetable> get(@PathParam("id") Long id) {
    return Vegetable.findById(id);
  }

  @PUT
  @Path("/{id}")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  public Uni<Response> update(@PathParam("id") Long id, Vegetable vegetable) {
    return Vegetable.<Vegetable>findById(id)
        .map(entity -> {
          entity.name = vegetable.name;
          entity.color = vegetable.color;
          return entity;
        })
        .flatMap(entity -> Vegetable.persist(entity)).onItem().transform(updatedVegetable -> Response.status(Response.Status.CREATED)
            .entity(updatedVegetable)
            .build());
  }

  @DELETE
  @Path("/{id}")
  @Produces(APPLICATION_JSON)
  public Uni<Response> delete(@PathParam("id") Long id) {
    return Vegetable.deleteById(id)
        .map(ignored -> Response.status(Response.Status.NO_CONTENT)
            .build());
  }

  @GET
  @Produces(APPLICATION_JSON)
  public Uni<List<Vegetable>> list() {
    return Vegetable.listAll();
  }
}
