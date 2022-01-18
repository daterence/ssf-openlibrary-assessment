package com.ssf.assessment.service;

import com.ssf.assessment.util.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.ssf.assessment.util.Constants.*;

@Service(BEAN_CACHING_BOOK_SERVICE)
public class CachingBookServiceImp implements BookService{
    private final Logger logger = Logger.getLogger(CachingBookServiceImp.class.getName());

    @Autowired
    @Qualifier(BEAN_BOOK_SERVICE)
    private BookServiceImpl delegate;

    @Autowired
    private BookCacheService cacheService;

    public List<Book> search(String searchTerm) {
        logger.info(">>>>>> Using CachingBookServiceImpl");
        Optional<List<Book>> opt = cacheService.get(searchTerm);
        List<Book> bookList = Collections.emptyList();

        if (opt.isPresent()) {
            logger.info("Cache hit for %s".formatted(searchTerm));
        } else {
            try {
                bookList = delegate.search(searchTerm);
                if (bookList.size() > 0)
                    cacheService.save(searchTerm, bookList);
            } catch (Exception e) {
                logger.warning("WARNING: %s".formatted(e.getMessage()));
            }
        }
        return bookList;
    }

    public List<Book> getDetails(String searchTerm) {
        Optional<List<Book>> opt = cacheService.get(searchTerm);
        List<Book> bookList = Collections.emptyList();

        if (opt.isPresent()) {
            logger.info("Cache hit for %s".formatted(searchTerm));
            bookList = opt.get();
        } else {
            try {
                bookList = delegate.getDetails(searchTerm);
                if (bookList.size() > 0)
                    cacheService.save(searchTerm, bookList);
            } catch (Exception e) {
                logger.warning("WARNING >> ".formatted(e.getMessage()));
            }
        }
        return bookList;
    }
}
