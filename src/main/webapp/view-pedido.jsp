<%@ page import="dao.Database" %>
<%@ page import="dao.PedidoDao" %>
<%@ page import="domain.Pedido" %>
<%@ page import="util.CurrencyUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="includes/header.jsp"%>

<main>
    <section class="py-5 text-center container">
        <h1>Ver Pedido</h1>
    </section>
    <%
        // TODO Validar si viene el campo id
        int id = Integer.parseInt(request.getParameter("id"));

        Database.connect();
        Pedido pedido = Database.jdbi.withExtension(PedidoDao.class, dao -> dao.getPedido(id));
    %>
    <div class="container">
        <div class="card text-center">
            <div class="card-header">
                Pedido
            </div>
            <div class="card-body">
                <h5 class="card-title"><%= pedido.getNombre() %></h5>
                <p class="card-text"><%= pedido.getDescripcion() %></p>
                <p class="card-text"><%= pedido.getEnvio() %></p>
            </div>
            <div class="card-footer text-body-secondary">
                <%= CurrencyUtils.format(pedido.getPrecio()) %>
            </div>
        </div>
    </div>
</main>

<%@include file="includes/footer.jsp"%>
