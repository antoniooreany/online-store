package com.wasp.onlinestore.web;

import com.wasp.onlinestore.service.ProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDeleteServlet extends HttpServlet {
    private final ProductService productService;

    public ProductDeleteServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean deleted = productService.delete(id);
        if (deleted) {
            resp.setStatus(HttpServletResponse.SC_OK);
            RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/");
            dispatcher.forward(req, resp);
        } else {
            resp.getWriter().println("Did not delete product by id: " + id);
        }
    }
}
