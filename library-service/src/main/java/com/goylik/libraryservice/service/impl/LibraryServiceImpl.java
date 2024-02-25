package com.goylik.libraryservice.service.impl;

import com.goylik.libraryservice.model.dto.LibraryBookDto;
import com.goylik.libraryservice.model.dto.BookUpdateRequest;
import com.goylik.libraryservice.model.entity.LibraryBook;
import com.goylik.libraryservice.repository.LibraryRepository;
import com.goylik.libraryservice.service.LibraryService;
import com.goylik.libraryservice.service.exception.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LibraryBookDto> getAvailableBooks() {
        List<LibraryBook> availableBooks = libraryRepository.findAllAvailableBooks();
        return availableBooks.stream()
                .map(b -> modelMapper.map(b, LibraryBookDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void updateBook(long bookId, BookUpdateRequest bookDto) {
        LibraryBook book = libraryRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(
                        String.format("No such book with id = %d in library.", bookId))
                );

        book.setBorrowedTime(bookDto.getBorrowedTime());
        book.setReturnTime(bookDto.getReturnTime());

        libraryRepository.save(book);
    }

    @Override
    @Transactional
    public void addBook(LibraryBookDto bookDto) {
        LibraryBook libraryBook = new LibraryBook();
        libraryBook.setBookId(bookDto.getBookId());
        libraryRepository.save(libraryBook);
    }
}
