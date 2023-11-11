package ventaVideojuegos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class HistorialPedidoGUI extends JFrame {

    private JTable tablaFactura;
    private JButton btnRegresar;

private GestorVentaVideojuegos gestorVenta;
    
    Connection connection;

    public HistorialPedidoGUI(GestorVentaVideojuegos gestorVenta) {
        this.gestorVenta = gestorVenta;}

    

    public HistorialPedidoGUI() {
    	
    	
        setTitle("HISTORIAL DE PEDIDOS");
        setSize(800, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());


        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Número de Pedido");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("NIT");
        modeloTabla.addColumn("Juego");
        modeloTabla.addColumn("Consola");
        modeloTabla.addColumn("Cantidad");

 
        llenarDatosTabla(modeloTabla);

     
        tablaFactura = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaFactura);
        panel.add(scrollPane, BorderLayout.CENTER);

    
        btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
        panel.add(btnRegresar, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void llenarDatosTabla(DefaultTableModel modelo) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String consultaSQL = "SELECT * FROM pedidos";
            try (PreparedStatement pstmt = conexion.prepareStatement(consultaSQL)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Vector<Object> fila = new Vector<>();
                        fila.add(rs.getString("numeroPedido"));
                        fila.add(rs.getString("fecha"));
                        fila.add(rs.getString("nombre"));
                        fila.add(rs.getString("apellido"));
                        fila.add(rs.getString("nit"));
                        fila.add(rs.getString("juego"));
                        fila.add(rs.getString("consola"));
                        fila.add(rs.getString("cantidad"));
                        modelo.addRow(fila);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener los datos de la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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