package com.ssf.assessment.controller;

import com.ssf.assessment.service.BookServiceImpl;
import com.ssf.assessment.util.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(path ={"/book"}, produces = MediaType.TEXT_HTML_VALUE)
public class SearchController {

    // logger class
    private final Logger logger = Logger.getLogger(SearchController.class.getName());

    // book service
    @Autowired
    private BookServiceImpl bookServiceImpl;

    // get book title for search
    @PostMapping
    public String getBookId(@RequestBody MultiValueMap<String, String> form, Model model) {
        String title = form.getFirst("title");
        logger.info("Book title >> " + title);

        List<Book> bookList = Collections.emptyList();

        try {
            logger.info("RETRIEVING >>>");
            bookList = bookServiceImpl.search(title);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        logger.info("BOOKLIST >> " + bookList);

        model.addAttribute("books", bookList);
        model.addAttribute("title", title);
        return "result";
    }
}
