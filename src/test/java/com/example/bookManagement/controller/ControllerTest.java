package com.example.bookManagement.controller;

import com.example.bookManagement.dto.BookDTO;
import com.example.bookManagement.kafka.BookProducer;
import com.example.bookManagement.service.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@ExtendWith(SpringExtension.class) // by default in @webmvctest (this is spring style not mockito )
@WebMvcTest(Controller.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Service service;

    @MockitoBean
    private BookProducer bookProducer;

    @Test
    void getBookByCriteria() throws Exception {
        BookDTO bookDTO = new BookDTO(1, "title", "author", 10, "");
        when(service.getByCriteria("title", "author", "10")).thenReturn(List.of(bookDTO));
        mockMvc.perform(get("/api/book/getByCriteria").accept(MediaType.APPLICATION_JSON)

                                                      .param("title", "title")
                                                      .queryParams(
                                                              MultiValueMap.fromSingleValue(
                                                                      Map.of("author", "author", "price", "10"))))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].title").value("title"))
               .andExpect(jsonPath("$[0].author").value("author"));
    }

    @Test
    void getBookByAuthor() throws Exception {
        BookDTO bookDTO = new BookDTO(1, "title", "author", 10, "");
        List<BookDTO> bookDTOs = List.of(bookDTO);
        when(service.getBookByAuthor("author")).thenReturn(bookDTOs);
        mockMvc.perform(get("/api/book/getBookByAuthor").param("author", "author")
                                                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].title").value("title"))
               .andExpect(jsonPath("$[0].author").value("author"));
    }

//    @Test
//    void pageGetAllBooks() {
//    }
//
//    @Test
//    void getAllBooks() {
//    }

    @Test
    void getBook() throws Exception {
        BookDTO bookDTO = new BookDTO(1, "title", "author", 10, "");
        when(service.getBook("title")).thenReturn(bookDTO);
        mockMvc.perform(get("/api/book/getBook")
                                .param("title", "title")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("title"))
               .andExpect(jsonPath("$.author").value("author"))
               .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void addBook() throws Exception {
        BookDTO bookDTO = new BookDTO(1, "title", "author", 10, "");
        when(service.addBook(any(BookDTO.class))).thenReturn(bookDTO);
        mockMvc.perform(post("/api/book/addBook").contentType(MediaType.APPLICATION_JSON)
                                                 .accept(MediaType.APPLICATION_JSON)
                                                 .content(
                                                         "{\"id\":1, \"title\":\"title\", \"author\":\"author\", \"price\":10}")
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("title"))
               .andExpect(jsonPath("author").value("author"));
    }

    @Test
    void deleteBook() throws Exception {
        when(service.deleteBook("title")).thenReturn(1);
        mockMvc.perform(delete("/api/book/deleteBook").param("title", "title")
                                                      .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().string(" total 1 row deleted "));
        verify(service, times(1)).deleteBook("title");
    }

    @Test
    void updateBook() throws Exception {
        when(service.updateBook(any(BookDTO.class))).thenReturn(1);
        mockMvc.perform(put("/api/book/updateBook")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"id\":1, \"title\":\"title\", \"author\":\"author\", \"price\":10, \"errorMessage\":\"\"}"))
               .andExpect(status().isOk())
               .andExpect(content().string("1 row updated with id 1"));
        verify(service, times(1)).updateBook(any(BookDTO.class));
    }
}