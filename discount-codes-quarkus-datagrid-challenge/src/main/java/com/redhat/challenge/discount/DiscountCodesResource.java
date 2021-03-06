package com.redhat.challenge.discount;

import com.redhat.challenge.discount.model.DiscountCode;
import com.redhat.challenge.discount.model.DiscountCodeType;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.mutiny.core.eventbus.EventBus;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/discounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiscountCodesResource {
    @Inject
    EventBus bus;
    Map<String, DiscountCode> discounts = new HashMap<>();
    Map<DiscountCodeType, Integer> globalUsed = new HashMap<>();

    @POST
    public Response create(DiscountCode discountCode) {
        if (!discounts.containsKey(discountCode.getName())) {
            discountCode.setUsed(0);
            discounts.put(discountCode.getName(), discountCode);
            bus.send("deleteDisc", discountCode);
            return Response.created(URI.create(discountCode.getName())).build();
        }

        return Response.ok(URI.create(discountCode.getName())).build();
    }

    @ConsumeEvent(value = "deleteDisc")
    public void deleteDiscount(DiscountCode discountCode) {
        try {
            Thread.sleep(discountCode.getExpire() * 1000L);
            discounts.remove(discountCode.getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @GET
    @Path("/consume/{name}")
    public Response consume(@PathParam("name") String name) {
        DiscountCode discountCode = discounts.get(name);

        if (discountCode == null) {
            return Response.noContent().build();
        }

        discountCode.setUsed(discountCode.getUsed() + 1);
        discounts.put(name, discountCode);
        setGlobalUsed(discountCode.getType());
        return Response.ok(discountCode).build();
    }

    private void setGlobalUsed(DiscountCodeType discountCodeType) {
        switch (discountCodeType) {
            case PERCENT:
                if (null == globalUsed.get(DiscountCodeType.PERCENT)) {
                    globalUsed.put(DiscountCodeType.PERCENT, 0);
                }
                globalUsed.put(DiscountCodeType.PERCENT, globalUsed.get(DiscountCodeType.PERCENT) + 1);
                break;
            case VALUE:
                if (null == globalUsed.get(DiscountCodeType.VALUE)) {
                    globalUsed.put(DiscountCodeType.VALUE, 0);
                }
                globalUsed.put(DiscountCodeType.VALUE, globalUsed.get(DiscountCodeType.VALUE) + 1);
                break;
            default:
                break;
        }
    }

    @GET
    @Path("/{type}")
    public DiscountCodes getByType(@PathParam("type") DiscountCodeType type) {
        List<DiscountCode> discountCodes = discounts.values().stream().filter((code) -> code.getType() == type)
                .map(this::addGlobalUsed).collect(Collectors.toList());
        return new DiscountCodes(discountCodes, discountCodes.size());
    }

    private DiscountCode addGlobalUsed(DiscountCode discountcode) {
        discountcode.setGlobalUsed(globalUsed.get(discountcode.getType()));
        return discountcode;
    }

}
