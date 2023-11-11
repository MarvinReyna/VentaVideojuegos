package ventaVideojuegos;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestorVentaVideojuegos extends JFrame implements ActionListener {

    private JButton btnIngresoProductos;
    private JButton btnInventarioProductos;
    private JButton btnPedido;
    private JButton btnHistorialPedidos;
    private JButton btnSalir;

    public GestorVentaVideojuegos() {
        
        setTitle("Gestor de Venta de Videojuegos");
        setSize(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel panel = new JPanel(new BorderLayout());

        
        btnIngresoProductos = new JButton("Ingreso de Productos");
        btnInventarioProductos = new JButton("Inventario de Productos");
        btnPedido = new JButton("Pedido");
        btnHistorialPedidos = new JButton("Historial de Pedidos");
        btnSalir = new JButton("Salir del Programa");

       
        btnIngresoProductos.addActionListener(this);
        btnInventarioProductos.addActionListener(this);
        btnPedido.addActionListener(this);
        btnHistorialPedidos.addActionListener(this);
        btnSalir.addActionListener(this);

     
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnIngresoProductos);
        btnPanel.add(btnInventarioProductos);
        btnPanel.add(btnPedido);
        btnPanel.add(btnHistorialPedidos); 
        panel.add(btnPanel, BorderLayout.NORTH);
        
        panel.add(btnSalir, BorderLayout.SOUTH);

      
        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {  
        if (e.getSource() == btnIngresoProductos) {            
            abrirProductosGUI();
        } else if (e.getSource() == btnInventarioProductos) {      
            abrirInventarioGUI();
        } else if (e.getSource() == btnPedido) {
            abrirPedidoGUI();
        } else if (e.getSource() == btnHistorialPedidos) {           
            abrirHistorialPedidos();
        } else if (e.getSource() == btnSalir) {          
            System.exit(0);
        }
    }

    
    private void abrirProductosGUI() {
        dispose(); 
        new ProductosGUI(this);
    }

    private void abrirInventarioGUI() {
        dispose();       
        new InventarioGUI();
    }

    private void abrirPedidoGUI() {
        dispose(); 
        new PedidoGUI(null);
    }

    private void abrirHistorialPedidos() {
        dispose(); 
        new HistorialPedidoGUI();
    }

    public static void main(String[] args) {
        GestorVentaVideojuegos gestorVenta = new GestorVentaVideojuegos();
        gestorVenta.setVisible(true);
    }
}
