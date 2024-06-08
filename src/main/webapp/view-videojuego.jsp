<%@ page import="dao.Database" %>
<%@ page import="dao.VideojuegoDao" %>
<%@ page import="domain.Videojuego" %>
<%@ page import="util.CurrencyUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="includes/header.jsp"%>

<main>
    <section class="py-5 text-center container">
        <h1>Ver Videojuego</h1>
    </section>
    <%
        // TODO Validar si viene el campo id
        int id = Integer.parseInt(request.getParameter("id"));

        Database.connect();
        Videojuego videojuego = Database.jdbi.withExtension(VideojuegoDao.class, dao -> dao.getVideojuego(id));
    %>
    <div class="container">
        <div class="card text-center">
            <div class="card-header">
                Videojuego
            </div>
            <div class="card-body">
                <h5 class="card-title"><%= videojuego.getTitulo() %></h5>
                <p class="card-text"><%= videojuego.getDescripcion() %></p>
                <p class="card-text"><%= videojuego.getGenero() %></p>
            </div>
            <div class="card-footer text-body-secondary">
                <%= CurrencyUtils.format(videojuego.getPrecio()) %>
            </div>
        </div>
    </div>
</main>

<%@include file="includes/footer.jsp"%>
