package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest 
{
    private Combo combo;
    private ProductoMenu hamburguesa;
    private ProductoMenu papas;
    private ProductoMenu gaseosa;

    @BeforeEach
    void setUp() 
    {
        hamburguesa = new ProductoMenu("corral", 14000);
        papas = new ProductoMenu("papas medianas", 5500);
        gaseosa = new ProductoMenu("gaseosa", 5000);

        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(hamburguesa);
        items.add(papas);
        items.add(gaseosa);

        combo = new Combo("combo corral", 0.10, items);
    }

    @Test
    void testGetNombre() 
    {
        assertEquals("combo corral", combo.getNombre());
    }

    @Test
    void testGetPrecio() 
    {
        int precioEsperado = (int)((14000 + 5500 + 5000) * (1 - 0.10));

        assertEquals(precioEsperado, combo.getPrecio());
    }

    @Test
    void testGenerarTextoFactura() 
    {
        String texto = combo.generarTextoFactura();

        assertTrue(texto.contains("combo corral"));
        assertTrue(texto.contains("0.1"));
        assertTrue(texto.contains(String.valueOf(combo.getPrecio())));
    }
}