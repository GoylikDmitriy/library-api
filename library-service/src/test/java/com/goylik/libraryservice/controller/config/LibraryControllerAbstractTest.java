package com.goylik.libraryservice.controller.config;

import com.goylik.libraryservice.controller.LibraryController;
import com.goylik.libraryservice.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LibraryController.class)
public abstract class LibraryControllerAbstractTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected LibraryService libraryService;
}
