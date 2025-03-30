package com.luand.luand.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.luand.luand.entities.Color;
import com.luand.luand.entities.Image;
import com.luand.luand.entities.Item;
import com.luand.luand.entities.Model;
import com.luand.luand.entities.Print;
import com.luand.luand.entities.Size;
import com.luand.luand.entities.dto.item.CreateItemDTO;
import com.luand.luand.entities.dto.item.UpdateItemDTO;
import com.luand.luand.entities.dto.model.CreateModelDTO;
import com.luand.luand.entities.dto.print.CreatePrintDTO;
import com.luand.luand.exceptions.custom_exception.item.ItemNotFoundException;
import com.luand.luand.repositories.ItemRepository;

@SpringBootTest
public class ItemServiceTest {

        @MockitoBean
        ItemRepository itemRepository;

        @Autowired
        ItemService itemService;

        List<Item> itemEntities;

        @BeforeEach
        void setUp() {
                var image1 = new Image();
                image1.setId(1L);
                var image2 = new Image();
                image2.setId(2L);

                var color = new Color();
                color.setId(1L);

                var createModelDTO = new CreateModelDTO(
                                "name_test_1",
                                "ref_test_1",
                                "description_test_1",
                                BigDecimal.valueOf(10),
                                Set.of(Size.P, Size.M));
                var model = new Model(createModelDTO);
                model.setId(1L);

                var createPrintDTO1 = new CreatePrintDTO(
                                "name_test_1", "ref_test_1", image1, Set.of(image1, image2),
                                Set.of(color), model.getId());

                var print1 = new Print(createPrintDTO1, model);

                var itemDTO1 = new CreateItemDTO(
                                Size.P,
                                color,
                                10,
                                print1.getId());
                var itemDTO2 = new CreateItemDTO(
                                Size.P,
                                color,
                                10,
                                print1.getId());
                var itemDTO3 = new CreateItemDTO(
                                Size.P,
                                color,
                                10,
                                print1.getId());

                var item1 = new Item(itemDTO1, print1);
                var item2 = new Item(itemDTO2, print1);
                var item3 = new Item(itemDTO3, print1);

                itemEntities = List.of(item1, item2, item3);
        }

        @Test
        void shouldReturnItemById() {
                var item = itemEntities.get(0);
                var itemId = item.getId();

                when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

                var result = itemService.getItemById(itemId);

                assertNotNull(result);

                assertEquals(itemId, result.getId());
                assertEquals(item.getRef(), result.getRef());
                assertEquals(item.getSize(), result.getSize());

                verify(itemRepository).findById(itemId);
        }

        @Test
        void shouldThrowNotFoundItemWhenItemById() {
                var itemId = 1L;

                when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

                assertThrows(ItemNotFoundException.class, () -> {
                        itemService.getItemById(itemId);
                });

                verify(itemRepository).findById(itemId);
        }

        @Test
        void shouldReturnAllItems() {

                var item = itemEntities.get(0);

                when(itemRepository.findAll()).thenReturn(itemEntities);

                var result = itemService.getAllItems();

                assertNotNull(result);
                assertEquals(itemEntities.size(), result.size());
                assertEquals(item.getRef(), result.get(0).getRef());

                verify(itemRepository).findAll();
        }

        @Test
        void shouldReturnAllItemsEmpty() {

                var result = itemService.getAllItems();

                assertNotNull(result);

                assertEquals(0, result.size());

                verify(itemRepository).findAll();
        }

        @Test
        void shouldCreateItem() {
                var color = new Color();
                color.setId(1L);

                var createModelDTO = new CreateModelDTO(
                                "name_test_1",
                                "ref_test_1",
                                "description_test_1",
                                BigDecimal.valueOf(10),
                                Set.of(Size.P, Size.M));
                var model = new Model(createModelDTO);
                model.setId(1L);

                var image1 = new Image();
                image1.setId(1L);
                var image2 = new Image();
                image2.setId(2L);

                var createPrintDTO1 = new CreatePrintDTO(
                                "name_test_1", "ref_test_1", image1, Set.of(image1, image2),
                                Set.of(color), model.getId());

                var print1 = new Print(createPrintDTO1, model);
                print1.setId(1L);

                var createItemDTO = new CreateItemDTO(
                                Size.P,
                                color,
                                10,
                                print1.getId());

                when(itemRepository.save(any(Item.class))).thenAnswer(invoker -> invoker.getArgument(0));

                var result = itemService.createItem(createItemDTO);

                assertNotNull(result);
                assertNotNull(result.getRef());

                assertEquals(createItemDTO.availableQuantity(), result.getAvailableQuantity());
                assertEquals(createItemDTO.size(), result.getSize());

                verify(itemRepository).save(any(Item.class));
        }

        @Test
        void shouldUpdateItem() {
                var item = itemEntities.get(0);
                var itemId = item.getId();

                when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
                when(itemRepository.save(any(Item.class))).thenAnswer(invoker -> invoker.getArgument(0));

                var originalSize = item.getSize();
                var originalColor = item.getColor();

                var color1 = new Color();
                color1.setId(1L);
                var color2 = new Color();
                color2.setId(2L);

                var createModelDTO = new CreateModelDTO(
                                "name_test_1",
                                "ref_test_1",
                                "description_test_1",
                                BigDecimal.valueOf(10),
                                Set.of(Size.P, Size.M));
                var model = new Model(createModelDTO);
                model.setId(1L);

                var image1 = new Image();
                image1.setId(1L);
                var image2 = new Image();
                image2.setId(2L);

                var createPrintDTO1 = new CreatePrintDTO(
                                "name_test_1", "ref_test_1", image1, Set.of(image1, image2),
                                Set.of(color1), model.getId());

                var print1 = new Print(createPrintDTO1, model);
                print1.setId(1L);

                var updateItemDTO = new UpdateItemDTO(
                                Size.GG,
                                color2,
                                15,
                                print1.getId());

                var result = itemService.updateItem(itemId, updateItemDTO);

                assertNotNull(result);
                assertNotNull(result.getRef());

                assertNotEquals(originalSize, result.getSize());
                assertNotEquals(originalColor, result.getColor());

                assertEquals(updateItemDTO.printId(), result.getPrint().getId());

                verify(itemRepository).findById(itemId);
                verify(itemRepository).save(item);
        }

        @Test
        void shouldDeleteItem() {
                var item = itemEntities.get(0);
                var itemId = item.getId();

                when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
                doNothing().when(itemRepository).deleteById(itemId);

                itemService.deleteItem(itemId);

                verify(itemRepository).findById(itemId);
                verify(itemRepository).deleteById(itemId);
        }
}
