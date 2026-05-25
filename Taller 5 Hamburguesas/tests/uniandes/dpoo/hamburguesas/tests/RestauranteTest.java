package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest 
{
    private Restaurante restaurante;

    @BeforeEach
    void setUp() 
    {
        restaurante = new Restaurante();
    }

    @Test
    void testCargarInformacionRestaurante() throws HamburguesaException, NumberFormatException, IOException 
    {
        File ingredientes = new File("./data/ingredientes.txt");
        File menu = new File("./data/menu.txt");
        File combos = new File("./data/combos.txt");

        restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);

        assertFalse(restaurante.getIngredientes().isEmpty());
        assertFalse(restaurante.getMenuBase().isEmpty());
        assertFalse(restaurante.getMenuCombos().isEmpty());
    }

    @Test
    void testIniciarPedido() throws YaHayUnPedidoEnCursoException 
    {
        restaurante.iniciarPedido("Maria", "Calle 123");

        assertNotNull(restaurante.getPedidoEnCurso());
        assertEquals("Maria", restaurante.getPedidoEnCurso().getNombreCliente());
    }

    @Test
    void testIniciarPedidoConPedidoEnCurso() throws YaHayUnPedidoEnCursoException 
    {
        restaurante.iniciarPedido("Maria", "Calle 123");

        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Juan", "Calle 456");
        });
    }

    @Test
    void testCerrarYGuardarPedidoSinPedidoEnCurso() 
    {
        assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        });
    }

    @Test
    void testCerrarYGuardarPedido() throws Exception 
    {
        File carpetaFacturas = new File("./facturas/");
        if(!carpetaFacturas.exists())
        {
            carpetaFacturas.mkdirs();
        }

        restaurante.iniciarPedido("Maria", "Calle 123");

        ProductoMenu producto = new ProductoMenu("corral", 14000);
        restaurante.getPedidoEnCurso().agregarProducto(producto);

        int id = restaurante.getPedidoEnCurso().getIdPedido();

        restaurante.cerrarYGuardarPedido();

        assertNull(restaurante.getPedidoEnCurso());

        File factura = new File("./facturas/factura_" + id + ".txt");
        assertTrue(factura.exists());

        factura.delete();
    }

    @Test
    void testGetPedidosInicialmenteVacio() 
    {
        assertTrue(restaurante.getPedidos().isEmpty());
    }

    @Test
    void testGetMenuBaseInicialmenteVacio() 
    {
        assertTrue(restaurante.getMenuBase().isEmpty());
    }

    @Test
    void testGetMenuCombosInicialmenteVacio() 
    {
        assertTrue(restaurante.getMenuCombos().isEmpty());
    }

    @Test
    void testGetIngredientesInicialmenteVacio() 
    {
        assertTrue(restaurante.getIngredientes().isEmpty());
    }
}