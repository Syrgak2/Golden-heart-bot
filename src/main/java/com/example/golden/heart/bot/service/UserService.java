package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.exception.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.exception.NullUserException;
import com.example.golden.heart.bot.model.Role;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User edit(Long id, User user) {
        return userRepository.findById(id)
                .map(foundUser -> {
                    foundUser.setName(user.getName());
                    foundUser.setChatId(user.getChatId());
                    foundUser.setPet(user.getPet());
                    foundUser.setRole(user.getRole());
                    foundUser.setPhone(user.getPhone());
                    return userRepository.save(foundUser);
                }).orElse(null);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User changeRole(String userName, Role role) throws VolunteerAlreadyAppointedException {
        if (userRepository.findByUserName(userName) == null) {
            throw new IllegalArgumentException("Пользователь с таким username не найден");
        }
        if (!userRepository.findByRole(Role.VOLUNTEER).isEmpty() && role == Role.VOLUNTEER) {
            User volunteer = userRepository.findByRole(Role.VOLUNTEER).iterator().next();
            throw new VolunteerAlreadyAppointedException("Ответственный волонтер уже назначен, id " + volunteer.getId() + ", username " + volunteer.getUserName());
        }
        User foundUser = userRepository.findByUserName(userName);
        foundUser.setRole(role);
        return userRepository.save(foundUser);
    }

    public User findByChatId(Long chatId) {
        return userRepository.findByChatId(chatId).orElse(null);
    }

    /**
     * Сохроняет выбор собаки или кошки
     * Вызывается в классе CatOrDogCommand
     * @param chatId chatId пользователя
     * @param chosenPet выбор пользователя
     */
    public void setChoiceCatOrDogCommand(Long chatId, String chosenPet) {
        User user = findByChatId(chatId);

        if (user == null) {
            throw new  NullUserException();
        }

        user.setChosenPetType(chosenPet);
        userRepository.save(user);
    }

    public void removeById(Long id) {
        userRepository.deleteById(id);
    }
}
