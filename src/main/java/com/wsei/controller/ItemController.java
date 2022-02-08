package com.wsei.controller;

import com.wsei.model.Item;
import com.wsei.model.ItemNotFoundException;
import com.wsei.model.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository repository;

    @GetMapping("/items")
    public List<Item> getItem() {
        return (List<Item>) repository.findAll();
    }

    @GetMapping("/items/{id}")
    public Item getItem(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @PostMapping("/items")
    public Item saveItem(@RequestBody Item item){
        return repository.save(item);
    }

    @PutMapping("/items/{id}")
    Item replaceItem(@RequestBody Item newItem, @PathVariable Long id) {
        return repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setItemCode(newItem.getItemCode());
                    item.setUnitId(newItem.getUnitId());
                    item.setWeight(newItem.getItemCode());
                    return repository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });
    }

    @DeleteMapping("/items/{id}")
    void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
