package mx.multivelopers.application;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(title = "Book API",
        description = "This API allows CRUD operations on a book",
        version = "1.0",
        contact = @Contact(name = "Multivelopers", url = "https://github.com/iscrivera/developer-games")),

    externalDocs = @ExternalDocumentation(url = "https://github.com/iscrivera/developer-games/quarkus-challenge", description = "All the Quarkus challenge"),
    tags = {
        @Tag(name = "api", description = "Public that can be used by anybody"),
        @Tag(name = "books", description = "Anybody interested in books")
    }
)
public class BookApplication extends Application {
}