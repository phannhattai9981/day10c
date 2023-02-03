package mvc.controller;

import mvc.entity.BookEntity;
import mvc.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;
@RequestMapping(method = RequestMethod.GET)
    public List<BookEntity> showBooks(Model model) {
        List<BookEntity> bookList = (List<BookEntity>) bookRepository.findAll();
        return bookList;
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "home";
    }

    @GetMapping(value = "/searchBook")
    public List<BookEntity> search(@RequestParam("searchBook") String searchBook, Model model) {
        List<BookEntity> resultList;
        if (searchBook.isEmpty()) {
            resultList = (List<BookEntity>) bookRepository.findAll();
        } else {
            resultList = bookRepository.findByNameContainingOrAuthorContaining(searchBook, searchBook);
        }
        return resultList;
    }


    @RequestMapping(method = RequestMethod.POST)
    public Object addBook(@RequestBody BookEntity newBook) {
        BookEntity result = bookRepository.save(newBook);
        return result;
    }

//    @RequestMapping(method = RequestMethod.PUT)
//    public Object updateBook(@RequestBody BookEntity updateBook) {
//        BookEntity result = bookRepository.findById(updateBook.getId()).get();
//        if (result == null) {
//            Map<String, String> error = new HashMap<String, String>() {
//                {
//                    put("Error", updateBook.getId() + " not exist");
//                }
//            };
//            return error;
//        }
//        result = updateBook;
//        bookRepository.save(result);
//        return result;
//    }
    @RequestMapping(method = PUT)
    public Object update(@RequestBody BookEntity updateBookEntity) {
        BookEntity bookUpdate = null;
        for (BookEntity bookEntity : bookRepository.findAll()) {
            if (bookEntity.getId() == updateBookEntity.getId()) {
                bookUpdate = bookEntity;
                break;
            }
        }

        if (bookUpdate == null) {
            Map<String, String> error = new HashMap<String, String>() {{
                put("error", updateBookEntity.getId() + "does not exist");
            }};
            return error;
        } else {
            BookEntity book= bookRepository.save(updateBookEntity);
            return book;
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Object deleteBook(@PathVariable(value = "id") int id) {
        BookEntity foundBook = null;
        for (BookEntity bookEntity : bookRepository.findAll()) {
            if (bookEntity.getId() == id) {
                foundBook = bookEntity;
                break;
            }
        }
        if (foundBook != null) {
            bookRepository.deleteById(foundBook.getId());
            Map<String, String> success = new HashMap<String, String>() {{
                put("success", "A Book Which ID =" + id + " has been deleted successfully");
            }};
            return success;
        } else {
            Map<String, String> error = new HashMap<String, String>() {{
                put("error", "A Book which ID = " + id + " does not exist");
            }};
            return error;
        }
    }
}