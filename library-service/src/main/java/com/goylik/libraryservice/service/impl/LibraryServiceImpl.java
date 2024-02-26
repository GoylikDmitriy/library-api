package com.goylik.libraryservice.service.impl;

import com.goylik.libraryservice.model.dto.LibraryBookDto;
import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.model.entity.LibraryBook;
import com.goylik.libraryservice.repository.LibraryRepository;
import com.goylik.libraryservice.service.LibraryService;
import com.goylik.libraryservice.service.exception.BookNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LibraryBookDto> getAvailableBooks() {
        log.info("Getting all available books.");

        List<LibraryBook> availableBooks = libraryRepository.findAllAvailableBooks();
        return availableBooks.stream()
                .map(b -> modelMapper.map(b, LibraryBookDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void updateBook(long bookId, BookUpdateRequest bookDto) {
        log.info("Updating book with id = {}", bookId);

        LibraryBook book = libraryRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Error while fetching book by id: Book with id = {} is not found.", bookId);
                    return new BookNotFoundException(String.format("No such book with id = %d in library.", bookId));
                });

        book.setBorrowedTime(bookDto.getBorrowedTime());
        book.setReturnTime(bookDto.getReturnTime());

        libraryRepository.save(book);

        log.info("Book was updated successfully.");
    }

    @Override
    @Transactional
    public void addBook(LibraryBookDto bookDto) {
        log.info("Adding book to the library. Book = {}", bookDto);

        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setBookId(bookDto.getBookId());
        libraryRepository.save(libraryBook);

        log.info("Book was added to the library successfully.");
    }
}
