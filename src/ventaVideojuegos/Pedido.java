package ventaVideojuegos;

public class Pedido {
    private int numeroPedido;
    private String fecha;
    private String nombre;
    private String apellido;
    private String nit;
    private String juego;
    private String consola;
    private int cantidad;
	public Pedido(int numeroPedido, String fecha, String nombre, String apellido, String nit, String juego,
			String consola, int cantidad) {
		super();
		this.numeroPedido = numeroPedido;
		this.fecha = fecha;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nit = nit;
		this.juego = juego;
		this.consola = consola;
		this.cantidad = cantidad;
	}
	public int getNumeroPedido() {
		return numeroPedido;
	}
	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getJuego() {
		return juego;
	}
	public void setJuego(String juego) {
		this.juego = juego;
	}
	public String getConsola() {
		return consola;
	}
	public void setConsola(String consola) {
		this.consola = consola;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Pedido() {
       
    }
}
