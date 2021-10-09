package mx.multivelopers.kafka;

import mx.multivelopers.kafka.quarkus.City;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;

@Path("/cities")
public class CityResource {
    private static final Logger LOGGER = Logger.getLogger(CityResource.class);

    @Channel("cities")
    Emitter<City> emitter;

    @POST
    public Response enqueueCity(City city) {
        LOGGER.infof("Sending city %s to Kafka", city.getName());
        emitter.send(city);
        return Response.accepted().build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy Reactive";
    }
}