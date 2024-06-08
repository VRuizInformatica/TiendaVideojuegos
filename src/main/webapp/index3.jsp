<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="domain.Empleado" %>
<%@ page import="dao.EmpleadoDao" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.Database" %>
<%@ page import="util.CurrencyUtils" %>

<%@include file="includes/header.jsp"%>

<script>
    $(document).ready(function () {
        $("#search-input").focus();
    });
</script>

<main>
    <section class="py-5 text-center container">
        <div class="row py-lg-5">
            <div class="col-lg-6 col-md-8 mx-auto">
                <h1 class="fw-light">Tienda Videojuegos</h1>
                <p class="lead text-body-secondary">Gestor de empleados</p>
                <p>
                    <a href="edit-empleado.jsp" class="btn btn-primary my-2">Registrar un nuevo empleado</a>
                    <a href="index.jsp" class="btn btn-primary my-2">Ir al gestor de Videojuegos</a>
                    <a href="index2.jsp" class="btn btn-primary my-2">Ir al gestor de Pedidos</a>
                </p>
            </div>
        </div>
        <form class="row g-2" id="search-form" method="GET">
            <div class="mb-1">
                <input type="text" class="form-control" placeholder="Búsqueda" name="search" id="search-input">
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-3" id="search-button">Buscar</button>
            </div>
        </form>
    </section>

    <div class="album py-5 bg-body-tertiary">
        <div class="container">
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                <%
                    String search = "";
                    if (request.getParameter("search") != null)
                        search = request.getParameter("search");

                    Database.connect();
                    List<Empleado> empleados;
                    if (search.isEmpty()) {
                        empleados = Database.jdbi.withExtension(EmpleadoDao.class, dao -> dao.getAllEmpleado());
                    } else {
                        final String searchTerm = search;
                        empleados = Database.jdbi.withExtension(EmpleadoDao.class, dao -> dao.getEmpleadoBySearchTerm(searchTerm));
                    }

                    for (Empleado empleado : empleados) {
                %>
                <div class="col">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <p class="card-text"><strong><%= empleado.getNombre() %></strong></p>
                            <p class="card-text"><%= empleado.getDescripcion() %></p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                    <a href="view-empleado.jsp?id=<%= empleado.getId() %>" type="button" class="btn btn-sm btn-outline-primary">Ver</a>
                                    <a href="edit-empleado.jsp?id=<%= empleado.getId() %>" type="button" class="btn btn-sm btn-outline-primary">Editar</a>
                                    <a href="remove-empleado?id=<%= empleado.getId() %>" type="button" class="btn btn-sm btn-outline-danger">Eliminar</a>

                                </div>
                                <small class="text-body-secondary"><%= CurrencyUtils.format(empleado.getSueldo()) %></small>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</main>

<%@include file="includes/footer.jsp"%>