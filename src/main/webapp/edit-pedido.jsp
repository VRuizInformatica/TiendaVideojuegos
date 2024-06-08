<%@ page import="domain.Pedido" %>
<%@ page import="dao.PedidoDao" %>
<%@ page import="dao.Database" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="includes/header.jsp"%>

<script>
    $(document).ready(function () {
        $("#edit-button").click(function (event) {
            event.preventDefault();
            const form = $("#edit-form")[0];
            const data = new FormData(form);

            $("#edit-button").prop("disabled", true);

            $.ajax({
                type: "POST",
                enctype: "multipart/form-data",
                url: "edit-pedido",
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    $("#result").html(data);
                    $("#name").value("");
                    $("#edit-button").prop("disabled", false);
                },
                error: function (error) {
                    $("#result").html(error.responseText);
                    $("#edit-button").prop("disabled", false);
                }
            });
        });
    });
</script>

<%
    int id;
    Pedido pedido = null;
    if (request.getParameter("id") == null) {
        // Se accede al formulario para crear un nuevo pedido
        id = 0;
    } else {
        // Se accede al formulario para editar un pedido
        id = Integer.parseInt(request.getParameter("id"));
        Database.connect();
        pedido = Database.jdbi.withExtension(PedidoDao.class, dao -> dao.getPedido(id));
    }
%>

<main>
    <section class="py-5 container">
        <h1><%= (id == 0) ? "Registrar nuevo Pedido" : "Modificar Pedido" %></h1>
        <form class="row g-3 needs-validation" method="post" enctype="multipart/form-data" id="edit-form">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" name="nombre" class="form-control" id="nombre" placeholder="Nombre del pedido"
                       value="<%= (id != 0) ? pedido.getNombre() : "" %>">
            </div>

            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea rows="4" name="descripcion" cols="50" class="form-control" id="descripcion" placeholder="Descripción del pedido">
                <%= (id != 0) ? pedido.getDescripcion() : "" %>
            </textarea>
            </div>

            <div class="mb-3">
                <label for="envio" class="form-label">Envio</label>
                <input type="text" name="envio" class="form-control" id="envio" placeholder="Envio del pedido"
                       value="<%= (id != 0) ? pedido.getEnvio() : "" %>">
            </div>

            <div class="col-md-4">
                <label for="precio" class="form-label">Precio</label>
                <input type="text" name="precio" class="form-control" id="precio" placeholder="15,00"
                       value="<%= (id != 0) ? pedido.getPrecio() : "" %>">
            </div>

            <div class="col-12">
                <input type="submit" value="Enviar" id="edit-button"/>
            </div>
            <input type="hidden" name="id" value="<%= id %>"/>
        </form>
        <br/>
        <div id="result"></div>
    </section>
</main>


<%@include file="includes/footer.jsp"%>
