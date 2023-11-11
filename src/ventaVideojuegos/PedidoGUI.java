package ventaVideojuegos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PedidoGUI extends JFrame {

    private JTextField txtNumeroPedido;
    private JTextField txtFecha;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtNit;
    private JComboBox<String> cmbJuego;
    private JComboBox<String> cmbConsola;
    private JTextField txtCantidad;

    private GestorVentaVideojuegos gestorVenta;

    Connection connection;

    public PedidoGUI(GestorVentaVideojuegos gestorVenta) {
        this.gestorVenta = gestorVenta;

        setTitle("Registrar Pedido");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, 0);

      
        JPanel panel = new JPanel();
        panel.setLayout(null);

   
        int y = 20;
        panel.add(crearLabel("Número de Pedido:", 20, y, 150, 25));
        txtNumeroPedido = crearTextField(180, y, 200, 25);
        panel.add(txtNumeroPedido);

        y += 40;
        panel.add(crearLabel("Fecha:", 20, y, 150, 25));
        txtFecha = crearTextField(180, y, 200, 25);
        panel.add(txtFecha);

        y += 40;
        panel.add(crearLabel("Nombre:", 20, y, 150, 25));
        txtNombre = crearTextField(180, y, 200, 25);
        panel.add(txtNombre);

        y += 40;
        panel.add(crearLabel("Apellido:", 20, y, 150, 25));
        txtApellido = crearTextField(180, y, 200, 25);
        panel.add(txtApellido);

        y += 40;
        panel.add(crearLabel("NIT:", 20, y, 150, 25));
        txtNit = crearTextField(180, y, 200, 25);
        panel.add(txtNit);

        y += 40;
        panel.add(crearLabel("Juego:", 20, y, 150, 25));
        cmbJuego = crearComboBoxProductos();
        cmbJuego.setBounds(180, y, 200, 25);
        panel.add(cmbJuego);

        y += 40;
        panel.add(crearLabel("Consola:", 20, y, 150, 25));
        cmbConsola = crearComboBoxConsolas();
        cmbConsola.setBounds(180, y, 200, 25);
        panel.add(cmbConsola);

        y += 40;
        panel.add(crearLabel("Cantidad:", 20, y, 150, 25));
        txtCantidad = crearTextField(180, y, 200, 25);
        panel.add(txtCantidad);

   
        JButton btnRegistrarPedido = new JButton("Registrar Pedido");
        btnRegistrarPedido.setBounds(20, 330, 150, 25);
        btnRegistrarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPedido();
            }
        });
        panel.add(btnRegistrarPedido);

  
        JButton btnGenerarFactura = new JButton("Generar Factura");
        btnGenerarFactura.setBounds(190, 330, 150, 25);
        btnGenerarFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarFactura();
            }
        });
        panel.add(btnGenerarFactura);

       
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(20, 360, 150, 25);
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
        panel.add(btnRegresar);

       
        add(panel);

        
        setVisible(true);
    }

    private JLabel crearLabel(String texto, int x, int y, int ancho, int alto) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, ancho, alto);
        return label;
    }

    private JTextField crearTextField(int x, int y, int ancho, int alto) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, ancho, alto);
        return textField;
    }

    private JComboBox<String> crearComboBoxProductos() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Seleccionar..."); 

  
        List<String> productos = obtenerProductosDesdeBD();

     
        Set<String> nombresUnicos = new HashSet<>(productos);

        for (String nombre : nombresUnicos) {
            comboBox.addItem(nombre);
        }

       
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String juegoSeleccionado = comboBox.getSelectedItem().toString();
            
                actualizarListaConsolas(juegoSeleccionado);
            }
        });

        return comboBox;
    }

    
    private void actualizarListaConsolas(String juegoSeleccionado) {
    
        List<String> consolas = obtenerConsolasDesdeBD(juegoSeleccionado);

       
        cmbConsola.removeAllItems();

      
        for (String consola : consolas) {
            cmbConsola.addItem(consola);
        }
    }

    private JComboBox<String> crearComboBoxConsolas() {
        JComboBox<String> comboBox = new JComboBox<>();
     
        String[] consolasPermitidas = {"", "PS4", "PS5", "Xbox One", "Xbox Series X", "Nintendo Switch", "PC"};
        for (String consola : consolasPermitidas) {
            comboBox.addItem(consola);
        }
        return comboBox;
    }

    private List<String> obtenerProductosDesdeBD() {
        List<String> productos = new ArrayList<>();
     
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String consultaSQL = "SELECT nombre FROM productos";
            try (PreparedStatement pstmt = conexion.prepareStatement(consultaSQL)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String nombreProducto = rs.getString("nombre");
                        productos.add(nombreProducto);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       
        return productos;
    }

   
    private List<String> obtenerConsolasDesdeBD(String juego) {
        List<String> consolas = new ArrayList<>();
     
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String consultaSQL = "SELECT consola FROM productos WHERE nombre = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(consultaSQL)) {
                pstmt.setString(1, juego);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String consola = rs.getString("consola");
                        consolas.add(consola);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
        return consolas;
    }

  
    private int obtenerCantidadDisponibleDesdeBD(String juego) {
        int cantidadDisponible = 0;

        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String consultaSQL = "SELECT cantidad FROM productos WHERE nombre = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(consultaSQL)) {
                pstmt.setString(1, juego);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        cantidadDisponible = rs.getInt("cantidad");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener la cantidad disponible: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return cantidadDisponible;
    }
    
 
 private int obtenerNumeroPedidoRecienRegistrado(Connection conexion) throws SQLException {
     int numeroPedido = 0;

     

     String consultaNumeroPedido = "SELECT MAX(numeroPedido) FROM pedidos";
     try (PreparedStatement pstmtNumeroPedido = conexion.prepareStatement(consultaNumeroPedido)) {
         try (ResultSet rsNumeroPedido = pstmtNumeroPedido.executeQuery()) {
             if (rsNumeroPedido.next()) {
                 numeroPedido = rsNumeroPedido.getInt(1);
             }
         }
     }

     return numeroPedido;
 }


 private void registrarPedido() {
	    String numeroPedido = txtNumeroPedido.getText();
	    String fecha = txtFecha.getText();
	    String nombre = txtNombre.getText();
	    String apellido = txtApellido.getText();
	    String nit = txtNit.getText();
	    String juego = cmbJuego.getSelectedItem().toString();
	    String consola = cmbConsola.getSelectedItem().toString();
	    String cantidad = txtCantidad.getText();

	    int cantidadIngresada = Integer.parseInt(cantidad);
	    int cantidadDisponible = obtenerCantidadDisponibleDesdeBD(juego, consola);

	    if (cantidadIngresada > cantidadDisponible) {
	        JOptionPane.showMessageDialog(this, "La cantidad ingresada es mayor a la cantidad disponible. Por favor, ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    try (Connection conexion = ConexionBD.obtenerConexion()) {
	        conexion.setAutoCommit(false);

	        try {
	            String consultaPedido = "INSERT INTO pedidos (numeroPedido, fecha, nombre, apellido, nit, juego, consola, cantidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	            try (PreparedStatement pstmtPedido = conexion.prepareStatement(consultaPedido)) {
	                pstmtPedido.setString(1, numeroPedido);
	                pstmtPedido.setString(2, fecha);
	                pstmtPedido.setString(3, nombre);
	                pstmtPedido.setString(4, apellido);
	                pstmtPedido.setString(5, nit);
	                pstmtPedido.setString(6, juego);
	                pstmtPedido.setString(7, consola);
	                pstmtPedido.setString(8, cantidad);

	                int filasAfectadasPedido = pstmtPedido.executeUpdate();

	                if (filasAfectadasPedido > 0) {
	                    // Actualizar la cantidad en la tabla productos específicamente para la consola seleccionada
	                    int nuevaCantidad = cantidadDisponible - cantidadIngresada;
	                    String consultaActualizacion = "UPDATE productos SET cantidad = ? WHERE nombre = ? AND consola = ?";
	                    try (PreparedStatement pstmtActualizacion = conexion.prepareStatement(consultaActualizacion)) {
	                        pstmtActualizacion.setInt(1, nuevaCantidad);
	                        pstmtActualizacion.setString(2, juego);
	                        pstmtActualizacion.setString(3, consola);

	                        int filasAfectadasActualizacion = pstmtActualizacion.executeUpdate();

	                        if (filasAfectadasActualizacion > 0) {
	                            conexion.commit();
	                            JOptionPane.showMessageDialog(this, "Pedido registrado con éxito", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
	                        } else {
	                            conexion.rollback();
	                            JOptionPane.showMessageDialog(this, "No se pudo actualizar la cantidad en la tabla productos", "Error", JOptionPane.ERROR_MESSAGE);
	                        }
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(this, "No se pudo registrar el pedido", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        } catch (SQLException ex) {
	            conexion.rollback();
	            JOptionPane.showMessageDialog(this, "Error al registrar el pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        } finally {
	            conexion.setAutoCommit(true);
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private int obtenerCantidadDisponibleDesdeBD(String juego, String consola) {
	    int cantidadDisponible = 0;

	    try (Connection conexion = ConexionBD.obtenerConexion()) {
	        String consultaSQL = "SELECT cantidad FROM productos WHERE nombre = ? AND consola = ?";
	        try (PreparedStatement pstmt = conexion.prepareStatement(consultaSQL)) {
	            pstmt.setString(1, juego);
	            pstmt.setString(2, consola);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    cantidadDisponible = rs.getInt("cantidad");
	                }
	            }
	        }
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error al obtener la cantidad disponible: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }

	    return cantidadDisponible;
	}


    // Método para verificar si una consola es válida para un juego específico
    private boolean esConsolaValidaParaJuego(String consola, String juego) {
        List<String> consolasValidas = obtenerConsolasDesdeBD(juego);
        return consolasValidas.contains(consola);
    }

    private double calcularTotal(String juego, int cantidad) {
        double precio = obtenerPrecioJuegoDesdeBD(juego);
        return precio * cantidad;
    }

    // Método para obtener el precio del juego desde la base de datos
    private double obtenerPrecioJuegoDesdeBD(String juego) {
        double precio = 0.0;

        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String consultaSQL = "SELECT precio FROM productos WHERE nombre = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(consultaSQL)) {
                pstmt.setString(1, juego);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        precio = rs.getDouble("precio");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener el precio del juego: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return precio;
    }

    
    
    private void generarFactura() {
        Pedido pedido = obtenerPedidoRegistrado();

        if (pedido != null) {
            double total = calcularTotal(pedido.getJuego(), pedido.getCantidad());
            String factura = "Número de Pedido: " + pedido.getNumeroPedido() + "\n" +
                    "Nombre: " + pedido.getNombre() + " " + pedido.getApellido() + "\n" +
                    "NIT: " + pedido.getNit() + "\n" +
                    "Juego: " + pedido.getJuego() + "\n" +
                    "Consola: " + pedido.getConsola() + "\n" +
                    "Cantidad: " + pedido.getCantidad() + "\n" +
                    "Total: " + total;

            mostrarFactura(factura);
            
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo obtener el pedido registrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Pedido obtenerPedidoRegistrado() {
        Pedido pedido = null;

        try (Connection conexion = ConexionBD.obtenerConexion()) {
            int numeroPedidoRegistrado = obtenerNumeroPedidoRecienRegistrado(conexion);

            String consultaSQL = "SELECT * FROM pedidos WHERE numeroPedido = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(consultaSQL)) {
                pstmt.setInt(1, numeroPedidoRegistrado);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        pedido = new Pedido();
                        pedido.setNumeroPedido(rs.getInt("numeroPedido"));
                        pedido.setFecha(rs.getString("fecha"));
                        pedido.setNombre(rs.getString("nombre"));
                        pedido.setApellido(rs.getString("apellido"));
                        pedido.setNit(rs.getString("nit"));
                        pedido.setJuego(rs.getString("juego"));
                        pedido.setConsola(rs.getString("consola"));
                        pedido.setCantidad(rs.getInt("cantidad"));
                    } else {
                        System.out.println("Error: Pedido no encontrado en la base de datos");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return pedido;
    }

    private void mostrarFactura(String factura) {
        JFrame ventanaFactura = new JFrame("Factura");
        ventanaFactura.setSize(300, 300);

        JTextArea txtAreaFactura = new JTextArea();
        txtAreaFactura.setText(factura);
        txtAreaFactura.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtAreaFactura);

        ventanaFactura.add(scrollPane);
        ventanaFactura.setVisible(true);
    }

    private void regresar() {
       
        dispose(); 
        new GestorVentaVideojuegos(); 
    }

    public static void main(String[] args) {
       
        GestorVentaVideojuegos gestorVenta = new GestorVentaVideojuegos();
        gestorVenta.setVisible(true);
    }
}




