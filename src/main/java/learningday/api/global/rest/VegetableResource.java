package learningday.api.global.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import learningday.data.entity.Vegetable;


@Path("/vegetables")
public class VegetableResource {

  @POST
  @Consumes(APPLICATION_JSON)
  public Response create(Vegetable vegetable) {
    Vegetable.persist(vegetable);
    return Response.status(Response.Status.CREATED)
        .entity(vegetable)
        .build();
  }

  @GET
  @Path("/{id}")
  @Produces(APPLICATION_JSON)
  public Vegetable get(@PathParam("id") Long id) {
    return Vegetable.findById(id);
  }

  @PUT
  @Path("/{id}")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  public Vegetable update(@PathParam("id") Long id, Vegetable vegetable) {
    if (vegetable.name == null) {
      throw new WebApplicationException("Vegetable Name was not set on request.", 422);
    }

    Vegetable entity = Vegetable.findById(id);

    if (entity == null) {
      throw new WebApplicationException("Vegetable with id of " + id + " does not exist.", 404);
    }

    entity.name = vegetable.name;
    entity.color = vegetable.color;
    //i think we need to persist wtf
    return entity;
  }

  @DELETE
  @Path("/{id}")
  @Produces(APPLICATION_JSON)
  public Response delete(@PathParam("id") Long id) {
    Vegetable entity = Vegetable.findById(id);
    if (entity == null) {
      throw new WebApplicationException("Vegetable with id of " + id + " does not exist.", 404);
    }
    entity.delete();
    return Response.status(204)
        .build();
  }

  @GET
  @Produces(APPLICATION_JSON)
  public List<Vegetable> list() {
    return Vegetable.listAll();
  }
}
