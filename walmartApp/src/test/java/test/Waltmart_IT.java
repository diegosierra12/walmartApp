package test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.message.MessageType;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * @author Diego Sierra
 */
@Test
public class Waltmart_IT extends TestNGCitrusTestDesigner {

	String WaltmartEndPoint = "http://api.walmartlabs.com";
    private static final String RESOURCE01 = "/v1/search?query=ipod&format=json&apiKey=n7h9s5yzwvapnv87wq73f7gc&responseGroup=base&order=asc&numItems=10&start=1";
    private static final String RESOURCE02 = "/v1/search?query=ipod&format=json&apiKey=n7h9s5yzwvapnv87wq73f7gc&responseGroup=base&order=asc&numItems=1&start=3";
    private static final String RESOURCE03 = "/v1/search?query=ipod&format=json&apiKey=n7h9s5yzwvapnv87wq73f7gc&responseGroup=base&order=asc&numItems=25&start=1";
    //Test para extraer la cantidad de ítems de la consulta.
    @CitrusTest
    public void testNumItems(){

        http()
            .client( WaltmartEndPoint )
            .send()
            .get(RESOURCE01)
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("");

        http()
            .client( WaltmartEndPoint )
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON)
            .extractFromPayload("$.numItems", "NumItems")
            ; 
        
        echo("La cantidad de ítems es: ${NumItems}");
    }
   
    //Test para extraer el nombre del primer ítem encontrado.
    @CitrusTest
    public void testNameFirstItem() {

        http()
            .client( WaltmartEndPoint )
            .send()
            .get(RESOURCE01)
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("");

        http()
            .client( WaltmartEndPoint )
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON)
            .extractFromPayload("$.items[0].name", "Name")
            ;
        	
        echo("El nombre del ítem es: ${Name}");
    }
    
    //Test para extraer las opciones de regalo del ítem 3.
    @CitrusTest
    public void testGiftOptions() {

        http()
            .client( WaltmartEndPoint )
            .send()
            .get(RESOURCE02)
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("");

        http()
            .client( WaltmartEndPoint )
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON)
            .extractFromPayload("$.items[0].giftOptions", "GiftOptions")
            ;
        	
        echo("Las opciones de regalo para el ítem 3 son: ${GiftOptions}");
    }
    
    //Test para extraer los nombres de todos los ítems consultados.
    @CitrusTest
    public void testNamesItems() {

        http()
            .client( WaltmartEndPoint )
            .send()
            .get(RESOURCE01)
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("");

        http()
            .client( WaltmartEndPoint )
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON)
            .extractFromPayload("$.items[*].name", "Names")
            ;
        	
        echo("Los nombres de todos los ítems son: ${Names}");
    }
    
    
  //Test para extraer los nombres de todos los ítems consultados que tienen precio entre 0 y 150 dolares.
    @CitrusTest
    public void testNamesItemsPrice() {

        http()
            .client( WaltmartEndPoint )
            .send()
            .get(RESOURCE01)
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("");

        http()
            .client( WaltmartEndPoint )
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON)
            .extractFromPayload("$.items[?(@.salePrice < 150)].name", "Names")
            .extractFromPayload("$.items[?(@.salePrice < 150)].salePrice", "Price")
            ;
        	
        echo("Los nombres de todos los ítems son: ${Names}");
        echo("Los valores de todos los ítems son: ${Price}");
    }
    
    

  //Test para extraer los nombres de todos los ítems consultados que tienen relación con el producto Apple iPod touch 32GB.
    @CitrusTest
    public void testNamesItemsIpod32GB() {

        http()
            .client( WaltmartEndPoint )
            .send()
            .get(RESOURCE01)
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("");

        http()
            .client( WaltmartEndPoint )
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON)
            .extractFromPayload("$.items[?(@.name =~ /.*IPOD TOUCH 32GB.*?/i)].name", "Names")
            ;
        	
        echo("Los nombres de todos los ítems son: ${Names}");
    }
    
  //Test para extraer los nombres de todos los ítems consultados que tienen la palabra Refurbished al final.
    @CitrusTest
    public void testNamesItemsFinalRefurbished() {

        http()
            .client( WaltmartEndPoint )
            .send()
            .get(RESOURCE03)
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("");

        http()
            .client( WaltmartEndPoint )
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON)
            .extractFromPayload("$.items[?(@.name =~ /.*REFURBISHED/i)].name", "Names")
            ;
        	
        echo("Los nombres de todos los ítems son: ${Names}");
    }
    
  //Test para extraer los nombres de todos los ítems consultados que contienen la palabra Refurbished.
    @CitrusTest
    public void testNamesItemsContainsRefurbished() {

        http()
            .client( WaltmartEndPoint )
            .send()
            .get(RESOURCE03)
            .messageType(MessageType.JSON)
            .contentType(ContentType.APPLICATION_JSON.getMimeType())
            .payload("");

        http()
            .client( WaltmartEndPoint )
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON)
            .extractFromPayload("$.items[?(@.name =~ /.*REFURBISHED.*?/i)].name", "Names")
            ;
        	
        echo("Los nombres de todos los ítems son: ${Names}");
    }
}
