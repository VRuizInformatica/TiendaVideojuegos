<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="domain.Pedido" %>
<%@ page import="dao.PedidoDao" %>
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
                <p class="lead text-body-secondary">Gestor de Pedidos</p>
                <p>
                    <a href="edit-pedido.jsp" class="btn btn-primary my-2">Registrar un nuevo pedido</a>
                    <a href="index.jsp" class="btn btn-primary my-2">Ir al gestor de Videojuegos</a>
                    <a href="index3.jsp" class="btn btn-primary my-2">Ir al gestor de Empleados</a>
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
                    List<Pedido> pedidos;
                    if (search.isEmpty()) {
                        pedidos = Database.jdbi.withExtension(PedidoDao.class, dao -> dao.getAllPedido());
                    } else {
                        final String searchTerm = search;
                        pedidos = Database.jdbi.withExtension(PedidoDao.class, dao -> dao.getPedidoBySearchTerm(searchTerm));
                    }

                    for (Pedido pedido : pedidos) {
                %>
                <div class="col">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <p class="card-text"><strong><%= pedido.getNombre() %></strong></p>
                            <p class="card-text"><%= pedido.getDescripcion() %></p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                    <a href="view-pedido.jsp?id=<%= pedido.getId() %>" type="button" class="btn btn-sm btn-outline-primary">Ver</a>
                                    <a href="edit-pedido.jsp?id=<%= pedido.getId() %>" type="button" class="btn btn-sm btn-outline-primary">Editar</a>
                                    <a href="remove-pedido?id=<%= pedido.getId() %>" type="button" class="btn btn-sm btn-outline-danger">Eliminar</a>

                                </div>
                                <small class="text-body-secondary"><%= CurrencyUtils.format(pedido.getPrecio()) %></small>
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