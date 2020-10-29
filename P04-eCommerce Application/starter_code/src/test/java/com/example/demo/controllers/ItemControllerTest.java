package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    ItemController itemController;

    ItemRepository itemRepo = mock(ItemRepository.class);
    List<Item> items;
    Item item;

    @Before
    public void init() {
        itemController=new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepo);
        createItems();
        when(itemRepo.findAll()).thenReturn(items);
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
        List<Item> lollipops=items.stream().filter(item -> item.getName()=="lollipop")
                .collect(Collectors.toList());
        when(itemRepo.findByName("lollipop")).thenReturn(lollipops);

    }

    private void createItems() {
        items = new ArrayList<>();
        item = new Item();
        item.setId(1L);
        item.setPrice(BigDecimal.valueOf(2));
        item.setName("lollipop");
        item.setDescription("med.");
        items.add(item);
        Item item2 = new Item();
        item2.setId(2L);
        item2.setPrice(BigDecimal.valueOf(3));
        item2.setName("lollipop");
        item2.setDescription("large");
        items.add(item2);
        Item item3 = new Item();
        item3.setId(3L);
        item3.setPrice(BigDecimal.valueOf(1));
        item3.setName("chocolate");
        item3.setDescription("dark");
        items.add(item3);
    }

    @Test
    public void verify_getItems() {
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void verify_getItemById(){
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals("lollipop",response.getBody().getName());
    }

    @Test
    public void verify_getItemsByName(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("lollipop");
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(2,response.getBody().size());

    }
}
