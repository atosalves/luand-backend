package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luand.luand.entities.Item;
import com.luand.luand.entities.dto.item.CreateItemDTO;
import com.luand.luand.entities.dto.item.UpdateItemDTO;
import com.luand.luand.exceptions.custom_exception.item.ItemNotFoundException;
import com.luand.luand.repositories.ItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final PrintService printService;

    @Transactional(readOnly = true)
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException());
    }

    @Transactional(readOnly = true)
    public List<Item> getAllItems() {
        var result = itemRepository.findAll();
        return result;
    }

    @Transactional
    public Item createItem(CreateItemDTO data) {

        var print = printService.getPrintById(data.printId());
        var item = new Item(data, print);

        item.setRef(item.getPrint().getModel().getRef() + "-" + item.getSize() + "-" + item.getColor() + item.getId());

        return itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long id, UpdateItemDTO data) {
        var item = getItemById(id);

        item.setColor(data.color());
        item.setSize(data.size());
        item.setAvailableQuantity(data.availableQuantity());

        var fashionLine = printService.getPrintById(data.printId());
        item.setPrint(fashionLine);

        return itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        getItemById(id);
        itemRepository.deleteById(id);
    }

}
