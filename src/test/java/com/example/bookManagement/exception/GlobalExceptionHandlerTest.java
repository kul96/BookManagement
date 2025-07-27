package com.example.bookManagement.exception;

import com.example.bookManagement.controller.Controller;
import com.example.bookManagement.kafka.BookProducer;
import com.example.bookManagement.service.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(GlobalExceptionHandler.class)
@WebMvcTest(Controller.class)
@AutoConfigureMockMvc(addFilters = false ) // security skip
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Service service;

    @MockitoBean
    private BookProducer bookProducer;

    @Test
    void handleTitleFoundNullException() throws Exception {
        when(service.getBook("title")).thenThrow(new TitleFoundNullException("not found"));
        mockMvc.perform(get("/api/book/getBook")
                                .param("title", "title")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.message").value("not found"))
               .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void handleDuplicateDataFoundException() throws Exception {
        when(service.getBook("title")).thenThrow(new DuplicateDataFoundException("duplicate found"));
        mockMvc.perform(get("/api/book/getBook")
                                .param("title", "title")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.message").value("duplicate found"))
               .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    void handleBookNotFoundException() throws Exception {
        when(service.getBook("title")).thenThrow(new BookNotFoundException("not found"));
        mockMvc.perform(get("/api/book/getBook")
                                .param("title", "title")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.message").value("not found"))
               .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void handleBadRequest() throws Exception {
        when(service.getBook("title")).thenThrow(RuntimeException.class);
        mockMvc.perform(get("/api/book/getBook")
                                .param("title", "title")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }
}