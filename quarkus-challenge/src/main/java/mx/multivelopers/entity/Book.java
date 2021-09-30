package mx.multivelopers.entity;

import javax.persistence.Entity;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Schema(description = "The book in the library")
@Entity
public class Book extends PanacheEntity {

    public String name;

    public Integer publicationYear;

    public String getName() {
        return name;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }
}
