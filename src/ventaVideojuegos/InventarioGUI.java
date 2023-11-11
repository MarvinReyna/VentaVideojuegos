package ventaVideojuegos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InventarioGUI extends JFrame {

    private JTable tablaInventario;
    private JButton btnRegresar;
    
    private GestorVentaVideojuegos gestorVenta;
    
    Connection connection;

    public InventarioGUI(GestorVentaVideojuegos gestorVenta) {
        this.gestorVenta = gestorVenta;}

    public InventarioGUI() {
       
        setTitle("Inventario de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

     
        JPanel panel = new JPanel();
        panel.setLayout(null); 

        
        tablaInventario = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaInventario);
        scrollPane.setBounds(20, 20, 550, 250);
        panel.add(scrollPane);

        
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(20, 300, 120, 25);
        btnRegresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
         
                dispose();

                new GestorVentaVideojuegos();
            }
        });
        panel.add(btnRegresar);


        add(panel);

        setVisible(true);

        cargarDatosInventario();
    }

    private void cargarDatosInventario() {
        try (Connection conexion = ConexionBD.obtenerConexion()) {

            String consultaSQL = "SELECT * FROM productos";
            try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consultaSQL)) {

                DefaultTableModel modeloTabla = new DefaultTableModel();
                tablaInventario.setModel(modeloTabla);

                modeloTabla.addColumn("Nombre");
                modeloTabla.addColumn("Consola");
                modeloTabla.addColumn("Cantidad");
                modeloTabla.addColumn("Precio");

                while (rs.next()) {
                    Object[] fila = {
                            rs.getString("nombre"),
                            rs.getString("consola"),
                            rs.getInt("cantidad"),
                            rs.getDouble("precio")
                    };
                    modeloTabla.addRow(fila);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos del inventario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        GestorVentaVideojuegos gestorVenta = new GestorVentaVideojuegos();
        gestorVenta.setVisible(true);
    }
}

