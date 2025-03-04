package com.aleksgolds.spring.web.cart;

import com.aleksgolds.spring.web.api.dto.CartDto;
import com.aleksgolds.spring.web.api.dto.OrderItemDto;
import com.aleksgolds.spring.web.api.dto.ProductDto;
import com.aleksgolds.spring.web.cart.controllers.CartController;
import com.aleksgolds.spring.web.cart.dto.Cart;
import com.aleksgolds.spring.web.cart.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

}
