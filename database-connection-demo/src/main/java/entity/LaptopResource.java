package entity;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Path("/laptop")
public class LaptopResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLaptop(){
//            Laptop.listAll();

        List<Laptop> laptopList=Laptop.listAll();
        return Response.ok(laptopList).build();
    }




    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLaptop(Laptop laptop){
//            Laptop.listAll();

 Laptop.persist(laptop);
 if(laptop.isPersistent()){
     return Response.created(URI.create("/laptop/"+laptop.id)).build();
 }else{
     return Response.status(Response.Status.BAD_REQUEST).build();
 }
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response oneLaptopID(@PathParam("id") Long id){

Laptop laptop=Laptop.findById(id);
return Response.ok(laptop).build();

    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLaptop(@PathParam("id") Long id,Laptop updatelaptop){

       Optional<Laptop> optionalLaptop=Laptop.findByIdOptional(id);
       if(optionalLaptop.isPresent()){
           Laptop dblaptop=optionalLaptop.get();

           //If null is present then and create for all the entity fileds
           if(Objects.nonNull(updatelaptop.getName())){
               dblaptop.setName(updatelaptop.getName());
           }

           dblaptop.persist();
           if(dblaptop.isPersistent()){
               return Response.created(URI.create("/laptop/"+id)).build();
           }else{
               return Response.status(Response.Status.BAD_REQUEST).build();
           }

       }else{
           return Response.status(Response.Status.BAD_REQUEST).build();
       }

    }



    @DELETE
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLaptop(@PathParam("id") Long id){
//            Laptop.listAll();

       boolean isDeleted=Laptop.deleteById(id);
       if(isDeleted){
           return Response.noContent().build();
       }else{
           return Response.status(Response.Status.BAD_REQUEST).build();
       }
    }
}

