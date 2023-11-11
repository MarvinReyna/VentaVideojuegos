package ventaVideojuegos;

public class Productos {
	private String Nombre;
	private String Consola;
	private int Cantidad;
	private double Precio;
	public Productos(String nombre, String consola, int cantidad, double precio) {
		super();
		Nombre = nombre;
		Consola = consola;
		Cantidad = cantidad;
		Precio = precio;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getConsola() {
		return Consola;
	}
	public void setConsola(String consola) {
		Consola = consola;
	}
	public int getCantidad() {
		return Cantidad;
	}
	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}
	public double getPrecio() {
		return Precio;
	}
	public void setPrecio(double precio) {
		Precio = precio;
	}

	
	
}