package com.example.golden.heart.bot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith((MockitoExtension.class))
public class PhotoServiceTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private PhotoService photoService;

    @Test
    public void uploadPhotoTest() throws IOException {
        Long id = 1L;
        String dir = "testDir";
        byte[] fileContent = "testFileContent".getBytes();

        String fileName = "testFile.jpg";

        MockMultipartFile file = new MockMultipartFile("file", fileName, "image.jpeg", fileContent);

        Path expectedFilePath = Paths.get(dir, id + ".jpg");

//        Mocking Files class methods

        when(Files.createDirectories(any())).thenReturn(expectedFilePath.getParent());
        when(Files.newOutputStream(eq(expectedFilePath), eq(StandardOpenOption.CREATE_NEW))).thenReturn(null);

        Path actual = photoService.uploadPhoto(id, dir, file);

        // Verify that logger.info was invoked with the correct message
        verify(logger).info("Wos invoked method for upload avatar");

        // Verify that Files.createDirectories was invoked with the correct argument
        verify(Files.createDirectories(eq(expectedFilePath.getParent())));

        // Verify that Files.newOutputStream was invoked with the correct arguments
        verify(Files.newOutputStream(eq(expectedFilePath)));

        // Verify that the method returned the expected file path
        assertEquals(expectedFilePath, actual);
    }
}
