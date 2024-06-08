package servlet;

import dao.Database;
import dao.VideojuegoDao;
import util.CurrencyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static util.ErrorUtils.sendError;
import static util.ErrorUtils.sendMessage;

@WebServlet("/edit-videojuego")
@MultipartConfig
public class EditVideojuego extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        try {
            if (hasValidationErrors(request, response))
                return;

            int id = 0;
            if (request.getParameter("id") != null) {
                id = Integer.parseInt(request.getParameter("id"));
            }

            String titulo = request.getParameter("titulo");
            String genero = request.getParameter("genero");
            float precio = CurrencyUtils.parse(request.getParameter("precio"));
            String descripcion = request.getParameter("descripcion");

            Database.connect();
            if (id == 0) {
                int affectedRows = Database.jdbi.withExtension(VideojuegoDao.class,
                        dao -> dao.addVideojuego(titulo, genero, precio, descripcion));
                sendMessage("Videojuego registrado correctamente", response);
            } else {
                final int finalId = id;
                int affectedRows = Database.jdbi.withExtension(VideojuegoDao.class,
                        dao -> dao.updateVideojuego(titulo, genero, precio, descripcion, finalId));
                sendMessage("Videojuego modificado correctamente", response);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
            sendError("El formato de precio no es correcto", response);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            sendError("Internal Server Error", response);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            sendError("Error conectando con la base de datos", response);
        }
    }

    private boolean hasValidationErrors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean hasErrors = false;

        if (request.getParameter("titulo") == null) {
            sendError("El título es un campo obligatorio", response);
            hasErrors = true;
        }

        if (request.getParameter("genero") == null) {
            sendError("El género es un campo obligatorio", response);
            hasErrors = true;
        }

        try {
            CurrencyUtils.parse(request.getParameter("precio"));
        } catch (ParseException pe) {
            sendError("Formato de precio no válido", response);
            hasErrors = true;
        }

        if (request.getParameter("descripcion") == null) {
            sendError("La descripción es un campo obligatorio", response);
            hasErrors = true;
        }

        return hasErrors;
    }
}
