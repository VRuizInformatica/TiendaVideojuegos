package servlet;

import dao.Database;
import dao.EmpleadoDao;
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

@WebServlet("/edit-empleado")
@MultipartConfig
public class EditEmpleado extends HttpServlet {

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
            String email = request.getParameter("email");
            float sueldo = CurrencyUtils.parse(request.getParameter("sueldo"));
            String descripcion = request.getParameter("descripcion");

            Database.connect();
            if (id == 0) {
                int affectedRows = Database.jdbi.withExtension(EmpleadoDao.class,
                        dao -> dao.addEmpleado(nombre, email, sueldo, descripcion));
                sendMessage("Empleado registrado correctamente", response);
            } else {
                final int finalId = id;
                int affectedRows = Database.jdbi.withExtension(EmpleadoDao.class,
                        dao -> dao.updateEmpleado(nombre, email, sueldo, descripcion, finalId));
                sendMessage("Empleado modificado correctamente", response);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
            sendError("El formato del sueldo no es correcto", response);
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

        if (request.getParameter("email") == null) {
            sendError("El email es un campo obligatorio", response);
            hasErrors = true;
        }

        try {
            CurrencyUtils.parse(request.getParameter("sueldo"));
        } catch (ParseException pe) {
            sendError("Formato del sueldo no es válido", response);
            hasErrors = true;
        }

        if (request.getParameter("descripcion") == null) {
            sendError("La descripción es un campo obligatorio", response);
            hasErrors = true;
        }

        return hasErrors;
    }
}
