<%@ page import="dao.Database" %>
<%@ page import="dao.EmpleadoDao" %>
<%@ page import="domain.Empleado" %>
<%@ page import="util.CurrencyUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="includes/header.jsp"%>

<main>
    <section class="py-5 text-center container">
        <h1>Ver Empleado</h1>
    </section>
    <%
        // TODO Validar si viene el campo id
        int id = Integer.parseInt(request.getParameter("id"));

        Database.connect();
        Empleado empleado = Database.jdbi.withExtension(EmpleadoDao.class, dao -> dao.getEmpleado(id));
    %>
    <div class="container">
        <div class="card text-center">
            <div class="card-header">
                Empleado
            </div>
            <div class="card-body">
                <h5 class="card-title"><%= empleado.getNombre() %></h5>
                <p class="card-text"><%= empleado.getDescripcion() %></p>
                <p class="card-text"><%= empleado.getEmail() %></p>
            </div>
            <div class="card-footer text-body-secondary">
                <%= CurrencyUtils.format(empleado.getSueldo()) %>
            </div>
        </div>
    </div>
</main>

<%@include file="includes/footer.jsp"%>
