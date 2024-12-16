package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luand.luand.entities.Item;
import com.luand.luand.entities.dto.item.CreateItemDTO;
import com.luand.luand.entities.dto.item.UpdateItemDTO;
import com.luand.luand.exception.item.ItemNotFoundException;
import com.luand.luand.exception.user.UserAlreadyExistsException;
import com.luand.luand.repositories.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final FashionLineService fashionLineService;

    public ItemService(ItemRepository itemRepository, FashionLineService fashionLineService) {
        this.itemRepository = itemRepository;
        this.fashionLineService = fashionLineService;
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
    }

    public Item getItemByColor(String color) {
        return itemRepository.findByColor(color)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
    }

    public List<Item> getAllItens() {
        var result = itemRepository.findAll();
        return result;
    }

    public Item createItem(CreateItemDTO data) {
        verifyItemExists(data.color());

        var fashionLine = fashionLineService.getFashionLineById(data.fashionLineId());
        var item = new Item(data, fashionLine);

        fashionLineService.updateDistincts(fashionLine, item.getColor(), item.getSize());

        return itemRepository.save(item);
    }

    public Item updateItem(Long id, UpdateItemDTO data) {
        var item = getItemById(id);

        verifyItemExists(data.color());

        item.setColor(data.color());
        item.setSize(data.size());
        item.setAvailableQuantity(data.availableQuantity());

        var fashionLine = fashionLineService.getFashionLineById(data.fashionLineDTO());
        item.setFashionLine(fashionLine);
        fashionLineService.updateDistincts(fashionLine, item.getColor(), item.getSize());

        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        getItemById(id);
        itemRepository.deleteById(id);
    }

    private void verifyItemExists(String color) {
        var itemByColor = itemRepository.findByColor(color);
        if (itemByColor.isPresent()) {
            throw new UserAlreadyExistsException("Color is already in use");
        }
    }
}
