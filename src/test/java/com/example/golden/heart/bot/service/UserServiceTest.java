package com.example.golden.heart.bot.service;

import com.example.golden.heart.bot.constants.Constants;
import com.example.golden.heart.bot.exceptions.NullUserException;

import com.example.golden.heart.bot.exceptions.VolunteerAlreadyAppointedException;
import com.example.golden.heart.bot.listener.TelegramBotUpdateListener;
import com.example.golden.heart.bot.model.Pet;
import com.example.golden.heart.bot.model.PetReport;
import com.example.golden.heart.bot.model.Photo;
import com.example.golden.heart.bot.model.User;
import com.example.golden.heart.bot.model.enums.Role;
import com.example.golden.heart.bot.repository.UserRepository;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);


    private final Long VALID_USER_ID = 1L;
    private final Long INVALID_USER_ID = -1L;
    private final Long VALID_PET_ID = 1L;
    private final Long INVALID_PET_ID = -1L;
    private final String PHONE_ADDED = "Ваш номер успешно принят. Спасибо)";

    @Mock
    UserRepository userRepository;

    @Mock
    PetService petService;

    @Mock
    TelegramBotSender telegramBotSender;

    @InjectMocks
    UserService userService;

    @Test
    void save() throws VolunteerAlreadyAppointedException {
        User user = new User();

        when(userRepository.save(user)).thenReturn(user);

        User userSave = userService.save(user);
        assertEquals(user, userSave);

        verify(userRepository).save(user);
    }

    @Test
    void edit() throws VolunteerAlreadyAppointedException {

        Long id = Constants.USER_1.getId(); // получаем id из Constants USER_1
        User existingUser = Constants.USER_1; // получаем экземпляр пользователя из Constants USER_1

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser)); // при поиске по id возвращается, existingUser
        when(userRepository.save(any(User.class))).then(returnsFirstArg()); // при сохранении любого пользователя возвращается первый обновлённый аргумент
        User updateUser = new User();
        updateUser.setName("Обновленное Имя");
        updateUser.setChatId(987654321L);
        updateUser.setRole(Role.USER);
        updateUser.setPhone("7654321");

        User result = userService.edit(id, updateUser); // вызов метода edit сервиса userService и сохранение результата в переменную result

        assertNotNull(result);
        assertEquals("Обновленное Имя", result.getName());
        assertEquals(987654321L, result.getChatId());
        assertEquals(Role.USER, result.getRole());
        assertEquals("7654321", result.getPhone());

        verify(userRepository).save(existingUser); // проверка, что метод save был вызван с существующим пользователем
    }


    @ParameterizedTest
    @MethodSource("provideUserForGetById") // данные для теста
    public void getById(Long id, User expectedUser) {
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(expectedUser)); // при вызове findById с определенным id возвращался expectedUser

        User actualUser = userService.getById(id);

        assertEquals(expectedUser, actualUser, "Возвращаемое значение не равно ожидаемому"); // проверка на соответствие expectedUser и actualUser

        verify(userRepository).findById(id); // Проверка, что метод findById был вызван на userRepository с конкретным id
    }

    private static Stream<Arguments> provideUserForGetById() { // Объявление статического метода, который предоставляет данные для теста
        return Stream.of(
                Arguments.of(1L, new User(1L, "Михаил", "Miha1L")), // набор аргументов id пользователя и объект user
                Arguments.of(2L, null) // набор аргументов id и null, для отсутствующего пользователя
        );

    }

    @Test
    void changeRole_VolunteerAlreadyAppointed_ThrowsVolunteerAlreadyAppointedException() { // тест, который выдает исключение, если волонтер уже назначен
        Long id = 1L;
        Role newRole = Role.VOLUNTEER;
        User user = new User();
        user.setId(id);
        user.setRole(Role.USER);

        when(userRepository.findById(id)).thenReturn(Optional.of(user)); // при вызове метода findById возвращает user

        List<User> volunteers = new ArrayList<>(Arrays.asList(user)); // Создание списка волонтеров и добавление в него пользователя user
        when(userService.findByRole(Role.VOLUNTEER)).thenReturn(volunteers); // при вызове метода findByRole с ролью VOLUNTEER возвращается список волонтеров

        Exception exception = assertThrows(VolunteerAlreadyAppointedException.class, () -> {
            userService.changeRole(id, newRole); // Выполнение метода changeRole и ожидание, что будет выброшено исключение VolunteerAlreadyAppointedException
        });

        String expectedMessage = "Ответственный волонтер уже назначен";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage)); // Проверка, содержит ли фактическое сообщение об ошибке ожидаемый текст
    }

    @Test
    void whenChangeRole_thenSaveUserWithNewRole() { // при изменении роли сохраняем пользователя с новой ролью
        Long id = 1L;
        Role newRole = Role.VOLUNTEER;
        User user = new User();
        user.setId(id);
        user.setRole(Role.USER);

        when(userRepository.findById(id)).thenReturn(Optional.of(user)); // при вызове метода findById возвращает user
        when(userRepository.save(any(User.class))).thenReturn(user); // при вызове метода save с любым объектом класса User он возвращает объект user

        try {
            User updatedUser = userService.changeRole(id, newRole); // Вызываем метод changeRole и сохраняем возвращенного пользователя
            assertEquals(newRole, updatedUser.getRole()); // Проверяем, что роль обновленного пользователя соответствует ожидаемой
            verify(userRepository).save(user); // Проверяем, что userRepository был вызван для сохранения пользователя
        } catch (VolunteerAlreadyAppointedException e) {
            logger.error("Ошибка при назначении роли волонтёра", e); // если пользователь уже является волонтером, то выбрасываем ошибку
        }

    }

    @Test
    public void whenChangeRoleAndUserNotFound_thenThrowIllegalArgumentException() { // если при смене роли пользователь не найден, выбрасываем исключение IllegalArgumentException
        Long id = 1L;
        Role newRole = Role.VOLUNTEER;

        when(userRepository.findById(id)).thenReturn(Optional.empty()); // возвращаем пустой Optional, имитируя отсутствие пользователя в базе данных

        assertThrows(IllegalArgumentException.class, () -> { // проверяем, что при выполнении следующего лямбда-выражения будет выброшено исключение IllegalArgumentException
            userService.changeRole(id, newRole); // пытаемся изменить роль, ожидая исключение, так как пользователь не найден
        });
    }


    @ParameterizedTest
    @MethodSource("provideDataForFindByChatId") // данные для теста
    public void findByChatId(Long chatId, User expectedUser) {

        when(userRepository.findByChatId(chatId)).thenReturn(Optional.ofNullable(expectedUser)); // возвращаем Optional с ожидаемым пользователем или пустой Optional, если expectedUser равен null

        User actualUser = userService.findByChatId(chatId);

        assertEquals(expectedUser, actualUser, "Возвращаемое значение не равно ожидаемому"); // проверяем, что фактический результат соответствует ожидаемому
        verify(userRepository).findByChatId(chatId); // проверяем, что метод findByChatId был вызван с правильным ID чата
    }

    private static Stream<Arguments> provideDataForFindByChatId() { // Объявление статического метода, который предоставляет данные для теста
        return Stream.of(
                Arguments.of(12345L, new User(12345L, "Михаил", "Miha1L")), // id чата и соответствующий пользователь
                Arguments.of(67890L, null) // id чата и null, ожидается, что пользователь не будет найден
        );
    }

    @Test
    void setChoiceCatOrDogCommand() {
        Long chatIdExists = 1L;
        Long chatIdDoesNotExist = 2L;
        String chosenPet = "Кот";
        User mockUser = new User();
        mockUser.setChatId(chatIdExists);
        when(userRepository.findByChatId(chatIdExists)).thenReturn(Optional.of(mockUser)); // возвращаем пользователя при поиске по существующему идентификатору чата
        when(userRepository.findByChatId(chatIdDoesNotExist)).thenReturn(Optional.empty()); // возвращаем пустой Optional при поиске по несуществующему идентификатору чата

        userService.setChoiceCatOrDogCommand(chatIdExists, chosenPet);  // вызов тестируемого метода для существующего идентификатора чата
        assertEquals("Кот", mockUser.getChosenPetType()); // проверка, что выбор пользователя был корректно установлен
        verify(userRepository).save(mockUser);

        try {
            userService.setChoiceCatOrDogCommand(chatIdDoesNotExist, chosenPet); // попытка вызова тестируемого метода для несуществующего идентификатора чата
            fail("Ожидается, что будет выдано исключение Null User."); // ожидаем, что будет выброшено исключение, если идентификатор чата не существует
        } catch (NullUserException e) {

        }

    }

    @Test
    void removeById() {
        Long userId = 1L;

        assertDoesNotThrow(() -> userService.removeById(userId)); // проверка, что метод removeById не выбрасывает исключений при вызове

        verify(userRepository, times(1)).deleteById(userId); // проверка, что метод deleteById был вызван ровно один раз с идентификатором пользователя userId
    }

    @ParameterizedTest
    @MethodSource("roleProvider") // данные для теста
    void findVolunteerByRole_ReturnsCorrectlyBasedOnRole(Role role, User expectedUser) {

        if (role == Role.VOLUNTEER) {
            when(userRepository.findByRole(role)).thenReturn(Arrays.asList(expectedUser)); // поведения userRepository для возврата списка пользователей при поиске по роли
        }

        User result = userService.findVolunteer();

        if (role == Role.VOLUNTEER) {
            assertEquals(expectedUser, result, "Метод должен вернуть пользователя с ролью VOLUNTEER."); // проверка, что результат соответствует ожидаемому пользователю
        } else {
            assertNull(result, "Метод должен вернуть null для ролей, отличных от VOLUNTEER."); // проверка, что результат равен null для других ролей
        }
    }

    static Stream<Arguments> roleProvider() { // Объявление статического метода, который предоставляет данные для теста
        return Stream.of(
                Arguments.of(Role.VOLUNTEER, new User()), // Предполагаем, что есть пользователь с ролью VOLUNTEER
                Arguments.of(Role.USER, null) // Для других ролей пользователь не найден
        );
    }

    @Test
    void whenUserNotFound_thenThrowException() { // если пользователь не найден, должно быть выброшено исключение
        when(userRepository.findById(INVALID_USER_ID)).thenReturn(Optional.empty()); // Мокирование метода findById для возвращения пустого Optional, имитируя ситуацию, когда пользователь не найден

        Exception exception = assertThrows(IllegalArgumentException.class, () -> { // Проверка, что при выполнении следующего кода будет выброшено исключение IllegalArgumentException
            userService.setPet(INVALID_USER_ID, VALID_PET_ID); // Попытка установить питомца для несуществующего пользователя
        });

        assertTrue(exception.getMessage().contains("Пользователь с таким id не найден")); // Проверка, что сообщение исключения содержит ожидаемый текст
    }

    @Test
    void whenPetNotFound_thenThrowException() { // если питомец не найден, должно быть выброшено исключение
        User user = new User();
        when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.of(user)); // Мокирование метода findById для возвращения пользователя
        when(petService.getPetById(INVALID_PET_ID)).thenReturn(null); // Мокирование метода getPetById для имитации ситуации, когда питомец не найден

        Exception exception = assertThrows(IllegalArgumentException.class, () -> { // Проверка, что при выполнении следующего кода будет выброшено исключение IllegalArgumentException
            userService.setPet(VALID_USER_ID, INVALID_PET_ID); // Попытка установить несуществующего питомца для пользователя
        });

        assertTrue(exception.getMessage().contains("Питомец с таким id не найден")); // Проверка, что сообщение исключения содержит ожидаемый текст
    }

    @Test
    void whenValidUserAndPet_thenSetPetSuccessfully() { // если пользователь и питомец найдены, питомец успешно устанавливается
        User user = new User();
        Pet pet = new Pet();
        when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.of(user)); // Мокирование метода findById для возвращения пользователя
        when(petService.getPetById(VALID_PET_ID)).thenReturn(pet); // Мокирование метода getPetById для возвращения питомца
        when(userRepository.save(any(User.class))).thenReturn(user); // Мокирование метода save для сохранения пользователя

        User resultUser = userService.setPet(VALID_USER_ID, VALID_PET_ID);

        assertNotNull(resultUser.getPet()); // Проверка, что у пользователя есть питомец
        assertEquals(pet, resultUser.getPet());
        assertEquals(Role.PET_OWNER, resultUser.getRole());
        assertEquals(user, pet.getOwner());
        verify(petService).savePet(pet);
        verify(userRepository).save(user);
    }


    @Test
    public void whenMessageWithSpaces_thenSavePhoneWithDashes() { // проверяем сценарий, когда номер телефона содержит пробелы и должен быть сохранён с дефисами

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("+7 123 456 78 90");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(12345L);
        when(userRepository.findByChatId(12345L)).thenReturn(Optional.of(user));

        // Вызов тестируемого метода
        userService.addedPhone(update);

        // Проверка результатов
        verify(user).setPhone("+7-123-456-78-90");
        verify(userRepository).save(user);
        verify(telegramBotSender).send(12345L, PHONE_ADDED);

    }

    @Test
    public void whenMessageWithDashes_thenSavePhoneAsIs() { // проверяет сценарий, когда номер телефона уже содержит дефисы и должен быть сохранён без изменений.
        Update update = mock(Update.class); // создание мок объекта Update
        Message message = mock(Message.class); // создание мок объекта Message
        Chat chat = mock(Chat.class); // создание мок объекта Chat
        User user = mock(User.class); // создание мок объекта User

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("+7-123-456-78-90");  // Настройка мока message для возврата строки с номером телефона при вызове метода text()
        when(message.chat()).thenReturn(chat); // Настройка мока message для возврата объекта chat при вызове метода chat()
        when(chat.id()).thenReturn(12345L); // Настройка мока chat для возврата идентификатора чата при вызове метода id()
        when(userRepository.findByChatId(12345L)).thenReturn(Optional.of(user)); // Настройка мока userRepository для возврата объекта user при поиске по идентификатору чата

        userService.addedPhone(update); // Вызов метода addedPhone с моком update

        verify(user).setPhone("+7-123-456-78-90"); // Проверка, что метод setPhone был вызван с номером телефона, который уже содержит дефисы
        verify(userRepository).save(user); // Проверка, что метод save был вызван для объекта user
        verify(telegramBotSender).send(12345L, PHONE_ADDED); // Проверка, что метод send был вызван с идентификатором чата и сообщением о добавлении телефона
    }
}