package servlet;

import dao.Database;
import dao.PedidoDao;
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

@WebServlet("/edit-pedido")
@MultipartConfig
public class EditPedido extends HttpServlet {

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

            String nombre = request.getParameter("nombre");
            String envio = request.getParameter("envio");
            float precio = CurrencyUtils.parse(request.getParameter("precio"));
            String descripcion = request.getParameter("descripcion");

            Database.connect();
            if (id == 0) {
                int affectedRows = Database.jdbi.withExtension(PedidoDao.class,
                        dao -> dao.addPedido(nombre, envio, precio, descripcion));
                sendMessage("Pedido registrado correctamente", response);
            } else {
                final int finalId = id;
                int affectedRows = Database.jdbi.withExtension(PedidoDao.class,
                        dao -> dao.updatePedido(nombre, envio, precio, descripcion, finalId));
                sendMessage("Pedido modificado correctamente", response);
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

        if (request.getParameter("nombre") == null) {
            sendError("El nombre es un campo obligatorio", response);
            hasErrors = true;
        }

        if (request.getParameter("envio") == null) {
            sendError("El envio es un campo obligatorio", response);
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
