package mx.multivelopers.kafka;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import mx.multivelopers.kafka.quarkus.City;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestSseElementType;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
@Path("/consumed-cities")
public class ConsumedCityResource {

    @Channel("cities-from-kafka")
    Multi<City> cities;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.TEXT_PLAIN)
    public Multi<String> stream() {
        return cities.map(city -> String.format("'%s' from %s", city.getName(), city.getCountry()));
    }
}