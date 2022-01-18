package com.ssf.assessment.service;

import com.ssf.assessment.util.Book;

import java.util.List;

public interface BookService {
    public List<Book> search(String searchTerm);

    public List<Book> getDetails(String searchTerm);
}
