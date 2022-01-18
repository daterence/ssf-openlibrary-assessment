package com.ssf.assessment.controller;

import com.ssf.assessment.service.BookService;
import com.ssf.assessment.service.BookServiceImpl;
import com.ssf.assessment.util.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static com.ssf.assessment.util.Constants.BEAN_CACHING_BOOK_SERVICE;

@Controller
@RequestMapping(path = "/book", produces = MediaType.TEXT_HTML_VALUE)
public class BookController {

    private final Logger logger = Logger.getLogger(BookController.class.getName());

    @Autowired
    @Qualifier(BEAN_CACHING_BOOK_SERVICE)
    private BookService bookService;

    @GetMapping(value = "/{workid}")
    public String getDetails(@PathVariable String workid, Model model) {
//        Book bookDetails = bookService.getDetails(workid);
        List<Book> bookList = Collections.emptyList();
        logger.info("BOOK DETAILS " + bookList);

        try {
            bookList = bookService.getDetails(workid);
        } catch (Exception e) {
            logger.warning("WARNING: %s".formatted(e.getMessage()));
        }

        if (bookList.size() > 0)
            workid = bookList.get(0).getWorkId();

        model.addAttribute("books", bookList);
        model.addAttribute("id", workid);
        return "detail";
    }
}
