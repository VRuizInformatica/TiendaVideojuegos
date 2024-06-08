<%@ page import="domain.Videojuego" %>
<%@ page import="dao.VideojuegoDao" %>
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
                url: "edit-videojuego",
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
    Videojuego videojuego = null;
    if (request.getParameter("id") == null) {
        // Se accede al formulario para crear un nuevo videojuego
        id = 0;
    } else {
        // Se accede al formulario para editar un videojuego
        id = Integer.parseInt(request.getParameter("id"));
        Database.connect();
        videojuego = Database.jdbi.withExtension(VideojuegoDao.class, dao -> dao.getVideojuego(id));
    }
%>

<main>
    <section class="py-5 container">
        <h1><%= (id == 0) ? "Registrar nuevo Videojuego" : "Modificar Videojuego" %></h1>
        <form class="row g-3 needs-validation" method="post" enctype="multipart/form-data" id="edit-form">
            <div class="mb-3">
                <label for="titulo" class="form-label">Titulo</label>
                <input type="text" name="titulo" class="form-control" id="titulo" placeholder="Titulo del videojuego"
                       value="<%= (id != 0) ? videojuego.getTitulo() : "" %>">
            </div>

            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea rows="4" name="descripcion" cols="50" class="form-control" id="descripcion" placeholder="Descripción del videojuego">
                <%= (id != 0) ? videojuego.getDescripcion() : "" %>
            </textarea>
            </div>

            <div class="mb-3">
                <label for="genero" class="form-label">Genero</label>
                <input type="text" name="genero" class="form-control" id="genero" placeholder="Genero del videojuego"
                       value="<%= (id != 0) ? videojuego.getGenero() : "" %>">
            </div>

            <div class="col-md-4">
                <label for="precio" class="form-label">Precio</label>
                <input type="text" name="precio" class="form-control" id="precio" placeholder="15,00"
                       value="<%= (id != 0) ? videojuego.getPrecio() : "" %>">
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
