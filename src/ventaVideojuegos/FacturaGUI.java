package ventaVideojuegos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturaGUI extends JFrame {

    private JTextArea txtAreaFactura;
    private JButton btnRegresar;
    private JButton btnCerrar;

    public FacturaGUI(Pedido pedido) {
        setTitle("Factura");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, 0);

       
        JPanel panel = new JPanel(new FlowLayout());
        
    
        txtAreaFactura = new JTextArea();
        txtAreaFactura.setEditable(false);
        txtAreaFactura.setPreferredSize(new Dimension(340, 250));
        panel.add(txtAreaFactura);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrar();
            }
        });
        panel.add(btnCerrar);

       
        add(panel);

      
        agregarDatosFactura(pedido);

      
        setVisible(true);
    }

    private void agregarDatosFactura(Pedido pedido) {
        if (pedido != null) {
            double total = calcularTotal(pedido.getJuego(), pedido.getConsola(), pedido.getCantidad());

            StringBuilder factura = new StringBuilder();
            factura.append("Número de Pedido: ").append(pedido.getNumeroPedido()).append("\n");
            factura.append("Fecha: ").append(pedido.getFecha()).append("\n");
            factura.append("Nombre: ").append(pedido.getNombre()).append("\n");
            factura.append("Apellido: ").append(pedido.getApellido()).append("\n");
            factura.append("NIT: ").append(pedido.getNit()).append("\n");
            factura.append("Juego: ").append(pedido.getJuego()).append("\n");
            factura.append("Consola: ").append(pedido.getConsola()).append("\n");
            factura.append("Cantidad: ").append(pedido.getCantidad()).append("\n");
            factura.append("Total: $").append(total).append("\n");

            txtAreaFactura.setText(factura.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Error: No se pudo obtener los datos del pedido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private double calcularTotal(String juego, String consola, int cantidad) {
        double precioJuego = obtenerPrecioJuegoDesdeBD(juego, consola);

        if (precioJuego > 0) {
            return precioJuego * cantidad;
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo obtener el precio del juego desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return 0.0;
        }
    }

    
    private double obtenerPrecioJuegoDesdeBD(String juego, String consola) {
        double precioJuego = 0.0;

        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String consultaSQL = "SELECT precio FROM productos WHERE nombre = ? AND consola = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(consultaSQL)) {
                pstmt.setString(1, juego);
                pstmt.setString(2, consola);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        precioJuego = rs.getDouble("precio");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return precioJuego;
    }


    private void cerrar() {
        
        dispose(); 
        System.exit(0);
    }

    public static void main(String[] args) {
        GestorVentaVideojuegos gestorVenta = new GestorVentaVideojuegos();
        gestorVenta.setVisible(true);
    }
}

