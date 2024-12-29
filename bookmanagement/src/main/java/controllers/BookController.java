/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dtos.BookDetailsDto;
import dtos.BookSummarizeDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.BookService;

/**
 *
 * @author Ato
 */
/*

/v1/book/
/v1/book/details

 */
@WebServlet(urlPatterns = {
                "/api/v1/book/all",
                "/api/v1/admin/book/details",
                "/api/v1/admin/book/create"})
public class BookController extends HttpServlet {

    private final BookService bookService;

    public BookController() {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String path = request.getRequestURI() == null ? "/" : request.getRequestURI();
        String[] pathList = path.split("/");
        path = pathList[pathList.length - 1];
        try {
            switch (path) {
                case "all": {
                    List<BookSummarizeDto> bookDtoList = bookService.getBookList();

                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonResponse = objectMapper.writeValueAsString(bookDtoList);

                    PrintWriter out = response.getWriter();
                    out.print("{\"data\":" + jsonResponse + "}");
                    out.flush();
                    break;
                }
                case "details": {
                    int bookId = Integer.parseInt(request.getParameter("bookId"));
                    BookDetailsDto bookDetailsDto = bookService.getBookDetails(bookId);
                    if (bookDetailsDto != null) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        String jsonResponse = objectMapper.writeValueAsString(bookDetailsDto);

                        System.out.println("Data: " + bookDetailsDto);

                        PrintWriter out = response.getWriter();
                        out.print("{\"data\":" + jsonResponse + "}");
                        out.flush();
                        break;
                    }
                }
                default: {

                }
            }
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            BookDetailsDto bookDetailsDto = objectMapper.readValue(
                    stringBuilder.toString(),
                    BookDetailsDto.class);

            System.out.println(stringBuilder);
            System.out.println(bookDetailsDto);

            boolean isBookCreated = bookService.createNewBook(bookDetailsDto);

            PrintWriter out = response.getWriter();
            if (isBookCreated) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print("{\"message\": \"The book is created!\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"message\": \"The book is not created!\"}");
            }
            out.flush();
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {

    }
}
