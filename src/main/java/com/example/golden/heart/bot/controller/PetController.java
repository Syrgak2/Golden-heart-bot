package com.example.golden.heart.bot.controller;

import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Operation(
            summary = "Добавить питомца"
    )
    @PostMapping
    public ResponseEntity<Pet> savePet(@RequestBody Pet pet) {
        Pet pet1 = petService.savePet(pet);
        if (pet1 == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(pet1);
    }

    @Operation(
            summary = "Изменить данные питомца"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Pet> editPet(@PathVariable Long id, @RequestBody Pet pet) {
        Pet foundPet = petService.editPet(id, pet);
        if (foundPet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @Operation(
            summary = "Показать данные питомца"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @Operation(
            summary = "Удалить питомца"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> removePet(@PathVariable Long id) throws VolunteerAlreadyAppointedException {
        Pet pet = petService.getPetById(id);
        if (pet != null) {
            petService.removePetById(id);
            return ResponseEntity.ok(pet);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Добавить фото питомцу"
    )
    @PostMapping(value = "/{petId}/photo/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> savePetPhoto(@PathVariable Long petId,
                                               @RequestParam MultipartFile file) throws IOException {
        if (file.getSize() > 1024 * 500) {
            return ResponseEntity.ok("File is too big");
        }
        petService.savePetPhoto(petId, file);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Фото питомца"
    )
    @GetMapping(value = "/{petId}/photo")
    public void downloadPhoto(@PathVariable Long petId,
                              HttpServletResponse response) throws IOException {
        petService.getPhoto(petId, response);
    }

    @Operation(
            summary = "Удалить фото у питомца"
    )
    @DeleteMapping(value = "/{petId}/photo")
    public ResponseEntity<String> removePhoto(@PathVariable Long petId) {
        petService.removePhoto(petId);
        return ResponseEntity.ok().build();
    }
}
