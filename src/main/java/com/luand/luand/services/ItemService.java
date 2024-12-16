package com.luand.luand.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luand.luand.entities.Item;
import com.luand.luand.entities.dto.item.CreateItemDTO;
import com.luand.luand.repositories.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final FashionLineService fashionLineService;

    public ItemService(ItemRepository itemRepository, FashionLineService fashionLineService) {
        this.itemRepository = itemRepository;
        this.fashionLineService = fashionLineService;
    }

    public Item getItem(Long id) {
        var result = itemRepository.findById(id);
        return result.get();
    }

    public List<Item> getAllItens() {
        var result = itemRepository.findAll();
        return result;
    }

    public Item createItem(CreateItemDTO itemDTO) {
        var fashionLine = fashionLineService.getFashionLine(itemDTO.fashionLineId());
        var item = new Item(itemDTO, fashionLine);

        fashionLineService.updateDistincts(fashionLine, item.getColor(), item.getSize());

        return itemRepository.save(item);
    }
}
