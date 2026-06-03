
import Modelos.Coche;
import Modelos.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Consultas {

    private String url = "jdbc:mysql://localhost:33060/concesionario";
    private String user = "root";
    private String password = "admin";



    // Si devuelve -1, la consulta no ha encontrado el correo
    // Si devulve 0, la consulta ha encontrado el correo, pero la contraseña es incorrecta
    // Si devuelve 1, la consulta ha encontrado correctas el correo y la contraseña
    public int buscarPorEmail(String email, String contraseña_posible){

        String sql = "SELECT password FROM usuarios WHERE email = ?";

        String contraseña_real = "";
        boolean correo_correcto = false;

        try(Connection conn = DriverManager.getConnection(url,user,password)) {

            try (PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1,email);

                try(ResultSet rs = pstmt.executeQuery() ) {

                    while (rs.next()){
                        correo_correcto = true;
                        contraseña_real = rs.getString("password");
                    }


                } catch (SQLException e){
                    System.out.println("ERROR rs: " + e.getMessage());
                }



            }catch (SQLException e){
                System.out.println("ERROR pstmt: " + e.getMessage());

            }


        } catch (SQLException e){
            System.out.println("ERROR conn: " + e.getMessage());

        }

        if (contraseña_posible.equals(contraseña_real)) return 1;
        else if(!correo_correcto)return -1;
        else return 0;

    }


    public void crearNuevoUsuario(String nombre, String email, String contraseña) throws SQLException {

        String sql = "INSERT INTO usuarios (nombre, password, email) values (?,?,?)";

        try(Connection conn = DriverManager.getConnection(url,user,password)) {

            try (PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1,nombre);
                pstmt.setString(2,contraseña);
                pstmt.setString(3,email);

                int filas_afectadas = pstmt.executeUpdate();

                System.out.println(filas_afectadas + " filas afectadas");



            }catch (SQLException e){
                System.out.println("ERROR pstmt: " + e.getMessage());

            }

        }




    }

    public boolean emailRepetido(String email){

        String sql = "SELECT email FROM usuarios WHERE email = ?";

        boolean correo_repetido = false;

        try(Connection conn = DriverManager.getConnection(url,user,password)) {

            try (PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1,email);

                try(ResultSet rs = pstmt.executeQuery() ) {

                    while (rs.next()){
                        correo_repetido = true;
                    }


                } catch (SQLException e){
                    System.out.println("ERROR rs: " + e.getMessage());
                }



            }catch (SQLException e){
                System.out.println("ERROR pstmt: " + e.getMessage());

            }


        } catch (SQLException e){
            System.out.println("ERROR conn: " + e.getMessage());

        }

        return correo_repetido;
    }

    public String[] obtenerVehiculosSegunFiltro(String filtro){

        String sql = "SELECT DISTINCT " + filtro + " FROM coches " +
                "WHERE id NOT IN (SELECT coche_id FROM compras)";

        List<String> lista_tipos_coches = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()){
                lista_tipos_coches.add(rs.getString(1));
            }

        } catch (SQLException e){
            System.out.println("ERROR obtenerVehiculosSegunFiltro: " + e.getMessage());
        }

        return lista_tipos_coches.toArray(new String[0]);
    }

    // Filtro por columna solo de los coches del usuario
    public String[] obtenerVehiculosSegunFiltro(String filtro, int idUsuario) {

        String sql = "SELECT DISTINCT c." + filtro + " FROM coches c " +
                "INNER JOIN compras co ON c.id = co.coche_id " +
                "WHERE co.usuario_id = ?";

        List<String> lista = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString(filtro));
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return lista.toArray(new String[0]);
    }



    public String[] obtenerModeloSegunMarca(Set<String> marca_vehiculos_seleccionados){

        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT modelo FROM coches " +
                        "WHERE id NOT IN (SELECT coche_id FROM compras)"
        );

        List<String> lista_tipos_coches = new ArrayList<>();

        if (!marca_vehiculos_seleccionados.isEmpty()) {
            String placeholders = marca_vehiculos_seleccionados.stream()
                    .map(m -> "?")
                    .collect(Collectors.joining(", "));
            sql.append(" AND marca IN (").append(placeholders).append(")");
        }

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            for (String marca : marca_vehiculos_seleccionados) {
                pstmt.setString(index++, marca);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista_tipos_coches.add(rs.getString(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR obtenerModeloSegunMarca: " + e.getMessage());
        }

        return lista_tipos_coches.toArray(new String[0]);
    }

    // Modelos según marca, solo de los coches del usuario
    public String[] obtenerModeloSegunMarca(Set<String> marcas, int idUsuario) {

        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT c.modelo FROM coches c " +
                        "INNER JOIN compras co ON c.id = co.coche_id " +
                        "WHERE co.usuario_id = ?"
        );

        if (!marcas.isEmpty()) {
            String placeholders = marcas.stream().map(m -> "?").collect(Collectors.joining(", "));
            sql.append(" AND c.marca IN (").append(placeholders).append(")");
        }

        List<String> lista = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            pstmt.setInt(index++, idUsuario);

            for (String marca : marcas) {
                pstmt.setString(index++, marca);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("modelo"));
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return lista.toArray(new String[0]);
    }

    public List<Coche> obtenerVehiculos(
            Set<String> marcas, Set<String> modelos, Set<String> tipos,
            Integer anioMin, Integer anioMax, Integer kmMin, Integer kmMax) {

        List<Coche> coches = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM coches WHERE id NOT IN (SELECT coche_id FROM compras)"
        );

        if (!marcas.isEmpty())  sql.append(" AND marca IN (")        .append(marcas.stream() .map(m -> "?").collect(Collectors.joining(", "))).append(")");
        if (!modelos.isEmpty()) sql.append(" AND modelo IN (")       .append(modelos.stream().map(m -> "?").collect(Collectors.joining(", "))).append(")");
        if (!tipos.isEmpty())   sql.append(" AND tipo_vehiculo IN (").append(tipos.stream()  .map(t -> "?").collect(Collectors.joining(", "))).append(")");

        if (anioMin != null) sql.append(" AND anio >= ?");
        if (anioMax != null) sql.append(" AND anio <= ?");
        if (kmMin   != null) sql.append(" AND kilometros >= ?");
        if (kmMax   != null) sql.append(" AND kilometros <= ?");

        try (Connection conn  = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            for (String m : marcas)  pstmt.setString(index++, m);
            for (String m : modelos) pstmt.setString(index++, m);
            for (String t : tipos)   pstmt.setString(index++, t);
            if (anioMin != null) pstmt.setInt(index++, anioMin);
            if (anioMax != null) pstmt.setInt(index++, anioMax);
            if (kmMin   != null) pstmt.setInt(index++, kmMin);
            if (kmMax   != null) pstmt.setInt(index++, kmMax);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) coches.add(mapearCoche(rs));
            }

        } catch (SQLException e) {
            System.out.println("ERROR obtenerVehiculos: " + e.getMessage());
        }

        return coches;
    }

    public List<Coche> obtenerVehiculos(
            int idUsuario, Set<String> marcas, Set<String> modelos, Set<String> tipos,
            Integer anioMin, Integer anioMax, Integer kmMin, Integer kmMax) {

        List<Coche> coches = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT c.* FROM coches c INNER JOIN compras co ON c.id = co.coche_id WHERE co.usuario_id = ?"
        );

        if (!marcas.isEmpty())  sql.append(" AND c.marca IN (")        .append(marcas.stream() .map(m -> "?").collect(Collectors.joining(", "))).append(")");
        if (!modelos.isEmpty()) sql.append(" AND c.modelo IN (")       .append(modelos.stream().map(m -> "?").collect(Collectors.joining(", "))).append(")");
        if (!tipos.isEmpty())   sql.append(" AND c.tipo_vehiculo IN (").append(tipos.stream()  .map(t -> "?").collect(Collectors.joining(", "))).append(")");

        if (anioMin != null) sql.append(" AND c.anio >= ?");
        if (anioMax != null) sql.append(" AND c.anio <= ?");
        if (kmMin   != null) sql.append(" AND c.kilometros >= ?");
        if (kmMax   != null) sql.append(" AND c.kilometros <= ?");

        try (Connection conn  = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            pstmt.setInt(index++, idUsuario);
            for (String m : marcas)  pstmt.setString(index++, m);
            for (String m : modelos) pstmt.setString(index++, m);
            for (String t : tipos)   pstmt.setString(index++, t);
            if (anioMin != null) pstmt.setInt(index++, anioMin);
            if (anioMax != null) pstmt.setInt(index++, anioMax);
            if (kmMin   != null) pstmt.setInt(index++, kmMin);
            if (kmMax   != null) pstmt.setInt(index++, kmMax);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) coches.add(mapearCoche(rs));
            }

        } catch (SQLException e) {
            System.out.println("ERROR obtenerVehiculos(usuario): " + e.getMessage());
        }

        return coches;
    }

    // Método auxiliar
    private Coche mapearCoche(ResultSet rs) throws SQLException {
        return new Coche(
                rs.getInt("id"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("tipo_vehiculo"),
                rs.getInt("anio"),
                rs.getString("color"),
                rs.getInt("kilometros"),
                rs.getString("combustible"),
                rs.getString("transmision"),
                (Integer) rs.getObject("potencia"),
                rs.getDouble("precio"),
                rs.getString("descripcion"),
                rs.getString("estado"),
                rs.getTimestamp("fecha_publicacion")
        );
    }


    public Coche obtenerVehiculosPorId(int id){

        Coche coche = null;

        String sql = "SELECT * FROM coches WHERE id = ?";

        try(Connection conn = DriverManager.getConnection(url,user,password)) {

            try (PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setInt(1, id);

                try(ResultSet rs = pstmt.executeQuery() ) {

                    while (rs.next()){

                        int id_coche = rs.getInt("id");
                        String marca = rs.getString("marca");
                        String modelo = rs.getString("modelo");
                        String tipoVehiculo = rs.getString("tipo_vehiculo");
                        int anio = rs.getInt("anio");
                        String color = rs.getString("color");
                        int kilometros = rs.getInt("kilometros");
                        String combustible = rs.getString("combustible");
                        String transmision = rs.getString("transmision");
                        Integer potencia = (Integer) rs.getObject("potencia");
                        double precio = rs.getDouble("precio");
                        String descripcion = rs.getString("descripcion");
                        String estado = rs.getString("estado");
                        Timestamp fechaPublicacion = rs.getTimestamp("fecha_publicacion");

                        coche = new Coche(id_coche,marca,modelo,tipoVehiculo,anio,color,kilometros,combustible,transmision,potencia,precio,descripcion,estado,fechaPublicacion);

                    }


                } catch (SQLException e){
                    System.out.println("ERROR rs: " + e.getMessage());
                }



            }catch (SQLException e){
                System.out.println("ERROR pstmt: " + e.getMessage());

            }


        } catch (SQLException e){
            System.out.println("ERROR conn: " + e.getMessage());

        }

        return coche;

    }


    public Usuario obtenerUsuarioPorEmail(String email_buscar){

        Usuario usuario = null;

        String sql = "SELECT * FROM usuarios WHERE email = ?";

        try(Connection conn = DriverManager.getConnection(url,user,password)) {

            try (PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1, email_buscar);

                try(ResultSet rs = pstmt.executeQuery() ) {

                    while (rs.next()){

                        int id_coche = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        String password = rs.getString("password");
                        String email = rs.getString("email");
                        String url_imagen = rs.getString("url_imagen");
                        int dinero = rs.getInt("dinero");
                        Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");

                        usuario = new Usuario(id_coche,nombre,password,email,url_imagen,dinero,fechaRegistro);

                    }


                } catch (SQLException e){
                    System.out.println("ERROR rs: " + e.getMessage());
                }



            }catch (SQLException e){
                System.out.println("ERROR pstmt: " + e.getMessage());

            }


        } catch (SQLException e){
            System.out.println("ERROR conn: " + e.getMessage());

        }

        return usuario;

    }

    public String obtenerURLFotosPorIdCoche(int id){

        String url_img = null;

        String sql = "SELECT * FROM imagenes_coches WHERE coche_id = ?";

        try(Connection conn = DriverManager.getConnection(url,user,password)) {

            try (PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setInt(1, id);

                try(ResultSet rs = pstmt.executeQuery() ) {

                    while (rs.next()){

                        url_img = rs.getString("ruta_imagen");


                    }


                } catch (SQLException e){
                    System.out.println("ERROR rs: " + e.getMessage());
                }



            }catch (SQLException e){
                System.out.println("ERROR pstmt: " + e.getMessage());

            }


        } catch (SQLException e){
            System.out.println("ERROR conn: " + e.getMessage());

        }

        return url_img;
    }

    public boolean cambiarNombreDeUsuario(int id, String nuevo_nombre){

        boolean consulta_aceptada = false;

        String sql = "UPDATE usuarios SET nombre = ? WHERE id = ?";

        try(Connection conn = DriverManager.getConnection(url,user,password)) {

            try (PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1,nuevo_nombre);
                pstmt.setInt(2, id);

                int filas_afectadas = pstmt.executeUpdate();


                if (filas_afectadas > 0){
                    consulta_aceptada = true;
                }


            }catch (SQLException e){
                System.out.println("ERROR pstmt: " + e.getMessage());

            }


        } catch (SQLException e){
            System.out.println("ERROR conn: " + e.getMessage());

        }

        return consulta_aceptada;

    }


    public boolean comprarCoche(Usuario usuario, Coche coche) {

        boolean consulta_aceptada = false;

        String sqlCompra = "INSERT INTO compras (usuario_id, coche_id, precio_final) VALUES (?, ?, ?)";
        String sqlDinero = "UPDATE usuarios SET dinero = dinero - ? WHERE id = ?";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);

            // ===== IMPORTANTE: transacción =====
            conn.setAutoCommit(false);

            // ===== 1. INSERT COMPRA =====
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCompra)) {

                pstmt.setInt(1, usuario.getId());
                pstmt.setInt(2, coche.getId());
                pstmt.setDouble(3, coche.getPrecio());

                int filas = pstmt.executeUpdate();

                if (filas <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            // ===== 2. DESCONTAR DINERO =====
            try (PreparedStatement pstmt2 = conn.prepareStatement(sqlDinero)) {

                pstmt2.setDouble(1, coche.getPrecio());
                pstmt2.setInt(2, usuario.getId());

                int filas2 = pstmt2.executeUpdate();

                if (filas2 <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            // ===== TODO OK =====
            conn.commit();
            consulta_aceptada = true;

            // actualizar objeto en memoria (opcional pero recomendado)
            usuario.setDinero(usuario.getDinero() - coche.getPrecio());

        } catch (SQLException e) {

            System.out.println("ERROR compra: " + e.getMessage());

            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("ERROR rollback: " + ex.getMessage());
            }

        } finally {

            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("ERROR cerrar conn: " + e.getMessage());
            }
        }

        return consulta_aceptada;
    }


    public boolean venderCoche(Usuario usuario, Coche coche) {

        boolean consulta_aceptada = false;

        String sqlVenta = "DELETE FROM compras WHERE usuario_id = ? AND coche_id = ?";
        String sqlDinero = "UPDATE usuarios SET dinero = dinero + ? WHERE id = ?";
        String sqlCoches = "UPDATE coches SET precio = ? WHERE id = ?";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);

            // ===== IMPORTANTE: transacción =====
            conn.setAutoCommit(false);

            // ===== 1. INSERT COMPRA =====
            try (PreparedStatement pstmt = conn.prepareStatement(sqlVenta)) {

                pstmt.setInt(1, usuario.getId());
                pstmt.setInt(2, coche.getId());

                int filas = pstmt.executeUpdate();

                if (filas <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            // ===== 2. DESCONTAR DINERO =====
            try (PreparedStatement pstmt2 = conn.prepareStatement(sqlDinero)) {

                pstmt2.setDouble(1, coche.getPrecio());
                pstmt2.setInt(2, usuario.getId());

                int filas2 = pstmt2.executeUpdate();

                if (filas2 <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            // ===== 2. DESCONTAR DINERO =====
            try (PreparedStatement pstmt3 = conn.prepareStatement(sqlCoches)) {

                pstmt3.setDouble(1, coche.getPrecio());
                pstmt3.setInt(2, coche.getId());

                int filas3 = pstmt3.executeUpdate();

                if (filas3 <= 0) {
                    conn.rollback();
                    return false;
                }
            }


            // ===== TODO OK =====
            conn.commit();
            consulta_aceptada = true;

            // actualizar objeto en memoria (opcional pero recomendado)
            usuario.setDinero(usuario.getDinero() + coche.getPrecio());

        } catch (SQLException e) {

            System.out.println("ERROR compra: " + e.getMessage());

            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("ERROR rollback: " + ex.getMessage());
            }

        } finally {

            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("ERROR cerrar conn: " + e.getMessage());
            }
        }

        return consulta_aceptada;
    }

    public boolean actualizarUrlImagen(int id, String urlImagen) {

        String sql = "UPDATE usuarios SET url_imagen = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, urlImagen);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR actualizarUrlImagen: " + e.getMessage());
            return false;
        }
    }

    public boolean pedirPrestamo(Usuario usuario, int dinero) {

        boolean consulta_aceptada = false;

        String sql = "UPDATE usuarios SET dinero = dinero + ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dinero);
            pstmt.setInt(2, usuario.getId());

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                usuario.setDinero(usuario.getDinero() + dinero);
                consulta_aceptada = true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR préstamo: " + e.getMessage());
        }

        return consulta_aceptada;
    }


}
