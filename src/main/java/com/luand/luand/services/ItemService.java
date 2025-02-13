package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.Item;
import com.luand.luand.entities.dto.item.CreateItemDTO;
import com.luand.luand.entities.dto.item.ItemQuantityUpdateDTO;
import com.luand.luand.entities.dto.item.UpdateItemDTO;
import com.luand.luand.exception.item.ItemNotFoundException;
import com.luand.luand.repositories.ItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final FashionLineService fashionLineService;

    @Transactional(readOnly = true)
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
    }

    @Transactional(readOnly = true)
    public Item getItemByColor(String color) {
        return itemRepository.findByColor(color)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItems() {
        var result = itemRepository.findAll();
        return result;
    }

    @Transactional
    public Item createItem(CreateItemDTO data) {

        var fashionLine = fashionLineService.getFashionLineById(data.fashionLineId());
        var item = new Item(data, fashionLine);

        return itemRepository.save(item);
    }

    @Transactional
    public Item updateAvailableQuantityItem(Long id, ItemQuantityUpdateDTO data) {
        var item = getItemById(id);

        item.setAvailableQuantity(data.quantity());

        return itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long id, UpdateItemDTO data) {
        var item = getItemById(id);

        item.setColor(data.color());
        item.setSize(data.size());
        item.setAvailableQuantity(data.availableQuantity());

        var fashionLine = fashionLineService.getFashionLineById(data.fashionLineDTO());
        item.setFashionLine(fashionLine);

        return itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

}
