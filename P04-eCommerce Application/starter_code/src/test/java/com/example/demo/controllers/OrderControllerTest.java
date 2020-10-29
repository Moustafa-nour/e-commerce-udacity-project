package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private UserRepository userRepo=mock(UserRepository.class);
    private OrderRepository orderRepo=mock(OrderRepository.class);
    private UserOrder order;
    private User user;
    List<Item> items;
    @Before
    public void init(){
        orderController=new OrderController();
        TestUtils.injectObject(orderController,"userRepository",userRepo);
        TestUtils.injectObject(orderController,"orderRepository",orderRepo);
        createUser();
        createOrder();
        when(userRepo.findByUsername("Moustafa")).thenReturn(user);
        when(orderRepo.save(order)).thenReturn(order);
        when(orderRepo.findByUser(user)).thenReturn(Collections.singletonList(order));

    }

    private void createOrder() {
        order=new UserOrder();
        order.setId(1L);
        order.setUser(user);
    }

    private void createUser() {
        user=new User();
        user.setId(1L);
        user.setUsername("Moustafa");
        Cart cart=new Cart();
        cart.setUser(user);
        cart.setId(1L);
        cart.setItems(createItems());
        user.setCart(cart);

    }
    private List<Item> createItems(){
        Item item=new Item();
        item.setId(1L);
        item.setName("lollipop");
        item.setPrice(BigDecimal.valueOf(1.0));
        items=new ArrayList<>();
        items.add(item);
        return items;
    }
    @Test
    public void verify_submit_order_happy_path(){
        ResponseEntity<UserOrder> response = orderController.submit("Moustafa");
        assertEquals(200,response.getStatusCodeValue());

    }
    @Test
    public void verify_submit_order_sad_path(){
        ResponseEntity<UserOrder> response = orderController.submit("Nour");
        assertEquals(404,response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void verify_getOrdersForUser_happy_path(){
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Moustafa");
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(1,response.getBody().size());

    }

    @Test
    public void verify_getOrdersForUser_sad_path(){
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Nour");
        assertEquals(404,response.getStatusCodeValue());
        assertNull(response.getBody());

    }


}
