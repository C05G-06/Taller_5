package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest 
{
    private Pedido pedido;
    private ProductoMenu hamburguesa;
    private ProductoMenu papas;

    @BeforeEach
    void setUp() 
    {
        pedido = new Pedido("Maria", "Calle 123");

        hamburguesa = new ProductoMenu("corral", 14000);
        papas = new ProductoMenu("papas medianas", 5500);
    }

    @Test
    void testGetIdPedido() 
    {
        assertTrue(pedido.getIdPedido() >= 0);
    }

    @Test
    void testGetNombreCliente() 
    {
        assertEquals("Maria", pedido.getNombreCliente());
    }

    @Test
    void testAgregarProducto() 
    {
        pedido.agregarProducto(hamburguesa);

        String factura = pedido.generarTextoFactura();

        assertTrue(factura.contains("corral"));
    }

    @Test
    void testGetPrecioTotalPedido() 
    {
        pedido.agregarProducto(hamburguesa);
        pedido.agregarProducto(papas);

        int neto = 14000 + 5500;
        int iva = (int)(neto * 0.19);
        int total = neto + iva;

        assertTrue(pedido.generarTextoFactura().contains(String.valueOf(total)));
    }

    @Test
    void testGenerarTextoFactura() 
    {
        pedido.agregarProducto(hamburguesa);
        pedido.agregarProducto(papas);

        String texto = pedido.generarTextoFactura();

        assertTrue(texto.contains("Maria"));
        assertTrue(texto.contains("Calle 123"));
        assertTrue(texto.contains("corral"));
        assertTrue(texto.contains("papas medianas"));
        assertTrue(texto.contains("Precio Neto"));
        assertTrue(texto.contains("IVA"));
        assertTrue(texto.contains("Precio Total"));
    }

    @Test
    void testGuardarFactura() throws FileNotFoundException 
    {
        pedido.agregarProducto(hamburguesa);

        File archivo = new File("./factura_prueba.txt");

        pedido.guardarFactura(archivo);

        assertTrue(archivo.exists());

        Scanner scanner = new Scanner(archivo);
        String contenido = "";

        while(scanner.hasNextLine())
        {
            contenido += scanner.nextLine();
        }

        scanner.close();

        assertTrue(contenido.contains("Maria"));
        assertTrue(contenido.contains("corral"));

        archivo.delete();
    }
}