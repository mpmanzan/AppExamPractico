package mpmanzan.appexampractico.ui.home;

public class ModeloLista {

    private String Producto;
    private double Precio;
    private int imagen;

    public ModeloLista(String producto, double precio, int imagen) {
        this.Producto = producto;
        this.Precio = precio;
        this.imagen = imagen;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

}
