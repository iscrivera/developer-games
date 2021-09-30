package mx.multivelopers.service;

// end::adocTransactional[]
import org.eclipse.microprofile.config.inject.ConfigProperty;

import mx.multivelopers.entity.Book;

// tag::adocTransactional[]
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class BookService {

    @Transactional(SUPPORTS)
    public List<Book> findAllBooks() {
        return Book.listAll();
    }

    @Transactional(SUPPORTS)
    public Book findBookByName(String name) {
        return Book.find("name", name).firstResult();
    }

    // tag::adocPersistBook[]
    public Book persistBook(Book book) {
        Book.persist(book);
        return book;
    }
    // end::adocPersistBook[]

    public Book updateBook(Book book) {
        Book entity = Book.findById(book.id);
        entity.name = book.name;
        entity.publicationYear = book.publicationYear;
        return entity;
    }

    public void deleteBook(Long id) {
        Book book = Book.findById(id);
        book.delete();
    }

    @Transactional(SUPPORTS)
    public List<Book> findBookByPublicationYearBetween(Integer lowerYear, Integer higherYear) {
        return Book.find("Select b from Book b where b.publicationYear between ?1 and ?2", lowerYear,
                higherYear).list();
    }
    
    @Transactional(SUPPORTS)
    public Book findBookById(Long id) {
        return Book.findById(id);
    }

}
