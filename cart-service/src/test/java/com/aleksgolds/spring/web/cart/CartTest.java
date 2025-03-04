package com.aleksgolds.spring.web.cart;


import com.aleksgolds.spring.web.api.dto.ProductDto;
import com.aleksgolds.spring.web.cart.dto.Cart;
import com.aleksgolds.spring.web.cart.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
public class CartTest {

    // Создать тесты для микросервисов, кроме Gateway. ДЗ
    // Для спрингБутТестов ставим аннотацию @SpringBootTest
    // , чтобы не загружать контекст можно прописать тестируемый класс в аннотации @SpringBootTest(class = CartService.class)
    // @DataJpaTest - для тестов правильности работы БД
    // @MockBean (Бин болванка, все связанные с тестируемым бином бины "мокаем")
    // С помощью Mockito можно задать поведение, необходимое для МокБина!
    // и вызываем его поведениес помощью Mockito. ...),
    // @WithMockUser - для тестов с юзерами
    // @JsonTest - для тестов сериализации
    // @SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM.PORT) для тестов с запуском Томкэт

    // @AutoConfigureMockMvc +
    // @Autowired
    // private MockMvc - эмуляция контекста Web, лучше чем webEnvironment для тестов правильности выполнения запросов контроллеров

    // TestRestTemplate для тестов РестТеплейт

    @Autowired
    private CartService cartService;

    @MockBean
    private RestTemplate restTemplate;

    private static ProductDto productDtoMilk;
    private static ProductDto productDtoBread;
    private static int totalPrice;

    @BeforeEach
    public void setUp() {
        cartService.clearCart("testCart");
        productDtoMilk = new ProductDto(1L,"Milk",10);
        productDtoBread = new ProductDto(2L,"Bread",15);
    }

    @Test
    public void cartFillingTest(){
        Mockito.doReturn(productDtoMilk)
                .when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        Mockito.doReturn(productDtoBread)
                .when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);
        cartService.addToCart("testCart",productDtoMilk.getId());
        cartService.addToCart("testCart",productDtoMilk.getId());
        cartService.addToCart("testCart",productDtoBread.getId());
        cartService.addToCart("testCart",productDtoBread.getId());

        Assertions.assertEquals(2,cartService.getCurrentCart("testCart").getItems().size());
        Assertions.assertEquals(4,cartService.getCurrentCart("testCart").getItems().stream().mapToInt(orderItemDto -> orderItemDto.getQuantity()).sum());

        totalPrice = cartService.getCurrentCart("testCart").getItems().stream().mapToInt(orderItemDto -> orderItemDto.getPrice()).sum();
        Assertions.assertEquals(totalPrice, cartService.getCurrentCart("testCart").getTotalPrice());
    }

    @Test
    public void cartMergeTest(){
    cartService.clearCart("guestCart");

        Mockito.doReturn(productDtoMilk)
                .when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        Mockito.doReturn(productDtoBread)
                .when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);

    cartService.addToCart("guestCart",productDtoMilk.getId());
    cartService.addToCart("guestCart",productDtoMilk.getId());
    cartService.addToCart("guestCart",productDtoBread.getId());
    cartService.addToCart("guestCart",productDtoBread.getId());

    cartService.addToCart("testCart",productDtoMilk.getId());
    cartService.addToCart("testCart",productDtoBread.getId());

    cartService.merge("testCart","guestCart");

    Assertions.assertEquals(2,cartService.getCurrentCart("testCart").getItems().size());
    Assertions.assertEquals(6,cartService.getCurrentCart("testCart").getItems().stream().mapToInt(orderItemDto -> orderItemDto.getQuantity()).sum());
    Assertions.assertEquals(0,cartService.getCurrentCart("guestCart").getItems().size());

    totalPrice = cartService.getCurrentCart("testCart").getItems().stream().mapToInt(orderItemDto -> orderItemDto.getPrice()).sum();
    Assertions.assertEquals(totalPrice,cartService.getCurrentCart("testCart").getTotalPrice());

    totalPrice = cartService.getCurrentCart("guestCart").getItems().stream().mapToInt(orderItemDto -> orderItemDto.getPrice()).sum();
    Assertions.assertEquals(totalPrice,cartService.getCurrentCart("guestCart").getTotalPrice());
    }

    @Test
    public void cartDecrementItemTest(){
        Mockito.doReturn(productDtoMilk)
                .when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoMilk.getId(), ProductDto.class);
        Mockito.doReturn(productDtoBread)
                .when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + productDtoBread.getId(), ProductDto.class);

        cartService.addToCart("testCart",productDtoMilk.getId());
        cartService.addToCart("testCart",productDtoMilk.getId());
        cartService.addToCart("testCart",productDtoMilk.getId());

        cartService.addToCart("testCart",productDtoBread.getId());
        cartService.addToCart("testCart",productDtoBread.getId());
        cartService.addToCart("testCart",productDtoBread.getId());

        cartService.decrementItem("testCart",productDtoMilk.getId());
        cartService.decrementItem("testCart",productDtoMilk.getId());
        cartService.decrementItem("testCart",productDtoMilk.getId());
        cartService.decrementItem("testCart",productDtoBread.getId());

        Assertions.assertEquals(1,cartService.getCurrentCart("testCart").getItems().size());
        Assertions.assertEquals(2,cartService.getCurrentCart("testCart").getItems().stream().mapToInt(orderItemDto -> orderItemDto.getQuantity()).sum());

        totalPrice = cartService.getCurrentCart("testCart").getItems().stream().mapToInt(orderItemDto -> orderItemDto.getPrice()).sum();
        Assertions.assertEquals(totalPrice,cartService.getCurrentCart("testCart").getTotalPrice());
    }
}
