package ventaVideojuegos;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductosGUI extends JFrame {

    private JTextField txtNombre;
    private JTextField txtConsola;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private GestorVentaVideojuegos gestorVenta;
    
    Connection connection;

    public ProductosGUI(GestorVentaVideojuegos gestorVenta) {
        this.gestorVenta = gestorVenta;


        setTitle("Ingreso de Productos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel();
        panel.setLayout(null);


        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 25);
        panel.add(lblNombre);

        JLabel lblConsola = new JLabel("Consola:");
        lblConsola.setBounds(20, 60, 80, 25);
        panel.add(lblConsola);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(20, 100, 80, 25);
        panel.add(lblCantidad);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(20, 140, 80, 25);
        panel.add(lblPrecio);

        txtNombre = new JTextField(20);
        txtNombre.setBounds(120, 20, 200, 25);
        panel.add(txtNombre);

        txtConsola = new JTextField(20);
        txtConsola.setBounds(120, 60, 200, 25);
        panel.add(txtConsola);

        txtCantidad = new JTextField(20);
        txtCantidad.setBounds(120, 100, 200, 25);
        panel.add(txtCantidad);

        txtPrecio = new JTextField(20);
        txtPrecio.setBounds(120, 140, 200, 25);
        panel.add(txtPrecio);

        JButton btnIngresar = new JButton("Ingresar Producto");
        btnIngresar.setBounds(20, 180, 150, 25);
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ingresarProducto();
            }
        });
        panel.add(btnIngresar);


        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(200, 180, 120, 25);
        btnRegresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                gestorVenta.setVisible(true);
            }
        });
        panel.add(btnRegresar);


        add(panel);


        setVisible(true);
    }

    private void ingresarProducto() {

    	String nombre = txtNombre.getText().toUpperCase();
        String consola = txtConsola.getText().toUpperCase();
        int cantidad = Integer.parseInt(txtCantidad.getText());
        double precio = Double.parseDouble(txtPrecio.getText());
        

        String[] consolasPermitidas = {"PS4", "PS5", "XBOX ONE", "XBOX SERIES X", "NINTENDO SWITCH", "PC"};


        boolean consolaValida = false;
        for (String consolaPermitida : consolasPermitidas) {
            if (consola.equals(consolaPermitida)) {
                consolaValida = true;
                break;
            }
        }

        if (!consolaValida) {
            JOptionPane.showMessageDialog(this, "Consola no válida. Por favor, ingrese una consola válida: PS4, PS5, Xbox One, Xbox Series X, Nintendo Switch, PC.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  
        }


        try (Connection conection = ConexionBD.obtenerConexion()) {
        	String consultaExistencia = "SELECT cantidad, precio FROM productos WHERE nombre = ? AND consola = ?";
            try (PreparedStatement stmtExistencia = conection.prepareStatement(consultaExistencia)) {
                stmtExistencia.setString(1, nombre);
                stmtExistencia.setString(2, consola);
                try (var rsExistencia = stmtExistencia.executeQuery()) {
                    if (rsExistencia.next()) {
                       
                        int cantidadExistente = rsExistencia.getInt("cantidad");
                        double precioExistente = rsExistencia.getDouble("precio");

                        if (precio == precioExistente) {
                        
                            int nuevaCantidad = cantidadExistente + cantidad;

                            // Actualizar la cantidad en la base de datos
                            String consultaActualizar = "UPDATE productos SET cantidad = ? WHERE nombre = ? AND consola = ?";
                            try (PreparedStatement stmtActualizar = conection.prepareStatement(consultaActualizar)) {
                                stmtActualizar.setInt(1, nuevaCantidad);
                                stmtActualizar.setString(2, nombre);
                                stmtActualizar.setString(3, consola);
                                stmtActualizar.executeUpdate();
                            }
                        } else {
                            // El precio no coincide, agregar un nuevo registro
                            String consultaInsercion = "INSERT INTO productos (nombre, consola, cantidad, precio) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement stmtInsercion = conection.prepareStatement(consultaInsercion)) {
                                stmtInsercion.setString(1, nombre);
                                stmtInsercion.setString(2, consola);
                                stmtInsercion.setInt(3, cantidad);
                                stmtInsercion.setDouble(4, precio);
                                stmtInsercion.executeUpdate();
                            }
                        }
                    } else {
                        // El producto no existe, realizar la inserción
                        String consultaInsercion = "INSERT INTO productos (nombre, consola, cantidad, precio) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement stmtInsercion = conection.prepareStatement(consultaInsercion)) {
                            stmtInsercion.setString(1, nombre);
                            stmtInsercion.setString(2, consola);
                            stmtInsercion.setInt(3, cantidad);
                            stmtInsercion.setDouble(4, precio);
                            stmtInsercion.executeUpdate();
                        }
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Producto ingresado o actualizado con éxito");


            dispose();
            
            gestorVenta.setVisible(true);

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(this, "Error al interactuar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {

        GestorVentaVideojuegos gestorVenta = new GestorVentaVideojuegos();
        gestorVenta.setVisible(true);
    }
}
