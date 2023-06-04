package ru.hammer2000.springapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hammer2000.springapp.model.Book;
import ru.hammer2000.springapp.services.BooksService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final BooksService booksService;

    @Autowired
    public UserController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping()
    public String index() {
        return "user/index";
    }

    @GetMapping("/books")
    public String books(@RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                              @RequestParam(value = "sort_by_year", required = false) boolean sortByYear,
                              @RequestParam(value = "sort_by_title", required = false) boolean sortByTitle,
                              Model model) {
        if (page != null && booksPerPage != null) {
            if (sortByTitle) {
                model.addAttribute("books", booksService.findAll(page, booksPerPage,
                        "title"));
            } else if (sortByYear) {
                model.addAttribute("books", booksService.findAll(page, booksPerPage,
                        "year"));
            } else {
                model.addAttribute("books", booksService.findAll(page, booksPerPage));
            }
        } else {
            if (sortByTitle) {
                model.addAttribute("books", booksService.findAll("title"));
            } else if (sortByYear) {
                model.addAttribute("books", booksService.findAll("year"));
            } else {
                model.addAttribute("books", booksService.findAll(0, 3));
            }
        }
        return "user/books";
    }

    @GetMapping("books/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));

        if (booksService.findOne(id).getOwner() != null) {
            model.addAttribute("owner", "");
        }
        return "user/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "book_for_search", required = false) String bookForSearch,
                         Model model) {

        if (bookForSearch != null && !bookForSearch.equals("")) {
            List<Book> books = booksService.searchBook(bookForSearch);
            if (!books.isEmpty()) {
                model.addAttribute("books", books);
            } else {
                model.addAttribute("notBooks", "");
            }
        }
        return "user/search";
    }
}
