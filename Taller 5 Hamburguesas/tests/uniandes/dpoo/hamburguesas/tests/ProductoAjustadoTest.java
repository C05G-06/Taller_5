package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest 
{
    private ProductoMenu productoBase;
    private ProductoAjustado productoAjustado;
    private Ingrediente queso;
    private Ingrediente tomate;

    @BeforeEach
    void setUp() 
    {
        productoBase = new ProductoMenu("corral", 14000);
        productoAjustado = new ProductoAjustado(productoBase);

        queso = new Ingrediente("queso mozzarella", 2500);
        tomate = new Ingrediente("tomate", 1000);
    }

    @Test
    void testGetNombre() 
    {
        assertEquals("corral", productoAjustado.getNombre());
    }

    @Test
    void testGetPrecioSinAjustes() 
    {
        assertEquals(14000, productoAjustado.getPrecio());
    }

    @Test
    void testGetPrecioConIngredienteAgregado() 
    {
        productoAjustado.agregarIngrediente(queso);

        assertEquals(16500, productoAjustado.getPrecio());
    }

    @Test
    void testGetPrecioConVariosIngredientesAgregados() 
    {
        productoAjustado.agregarIngrediente(queso);
        productoAjustado.agregarIngrediente(tomate);

        assertEquals(17500, productoAjustado.getPrecio());
    }

    @Test
    void testGetPrecioConIngredienteEliminado() 
    {
        productoAjustado.eliminarIngrediente(tomate);

        assertEquals(14000, productoAjustado.getPrecio());
    }

    @Test
    void testGenerarTextoFactura() 
    {
        productoAjustado.agregarIngrediente(queso);
        productoAjustado.eliminarIngrediente(tomate);

        String texto = productoAjustado.generarTextoFactura();

        assertTrue(texto.contains("corral"));
        assertTrue(texto.contains("+queso mozzarella"));
        assertTrue(texto.contains("2500"));
        assertTrue(texto.contains("-tomate"));
        assertTrue(texto.contains("16500"));
    }
}