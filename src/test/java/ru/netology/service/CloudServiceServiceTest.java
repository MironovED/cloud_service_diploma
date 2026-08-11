package ru.netology.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.FileData;
import ru.netology.exception.ErrorGetFilesException;
import ru.netology.exception.UnauthorizedErrorException;
import ru.netology.repository.CloudServiceRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CloudServiceServiceTest {
    private final String rawToken = "Bearer W4VQZRL6TL878AKV4BNUBN5YAH8J9HG5X2M5MKAKJXZRANJEJMH47PX7UYSGCN";
    private final String token = "W4VQZRL6TL878AKV4BNUBN5YAH8J9HG5X2M5MKAKJXZRANJEJMH47PX7UYSGCN";
    @Mock
    private CloudServiceRepository mockRepository = Mockito.mock(CloudServiceRepository.class);

    @Test
    void shouldReturnUnauthorizedException() {
        CloudServiceService cloudServiceService = new CloudServiceService(mockRepository);
        assertThrows(UnauthorizedErrorException.class, () -> cloudServiceService.getListFiles(rawToken, null));
    }

    @Test
    void shouldReturnErrorGetFilesException() {
        CloudServiceService cloudServiceService = new CloudServiceService(mockRepository);
        CloudServiceService mockService = Mockito.spy(cloudServiceService);
        Mockito.doReturn(true).when(mockService).checkToken(token);
        Mockito.doThrow(new RuntimeException()).when(mockRepository).getListFiles();
        assertThrows(ErrorGetFilesException.class, () -> mockService.getListFiles(rawToken, null));
    }

    @Test
    void shouldGetListFilesWhenLimitIsNull() {
        List<FileData> listFiles = new ArrayList<>();
        listFiles.add(new FileData("one.txt", "one.txt"));
        listFiles.add(new FileData("two.txt", "two.txt"));
        listFiles.add(new FileData("three.txt", "three.txt"));
        listFiles.add(new FileData("fore.txt", "fore.txt"));

        Mockito.when(mockRepository.getListFiles()).thenReturn(listFiles);
        CloudServiceService cloudServiceService = new CloudServiceService(mockRepository);
        CloudServiceService mockService = Mockito.spy(cloudServiceService);
        Mockito.doReturn(true).when(mockService).checkToken(token);

        var result = mockService.getListFiles(rawToken, null);
        assertEquals(3, result.size());
    }

    @Test
    void shouldGetListFilesWhenLimit5() {
        List<FileData> listFiles = new ArrayList<>();
        listFiles.add(new FileData("one.txt", "one.txt"));
        listFiles.add(new FileData("two.txt", "two.txt"));
        listFiles.add(new FileData("three.txt", "three.txt"));
        listFiles.add(new FileData("fore.txt", "fore.txt"));
        listFiles.add(new FileData("five.txt", "five.txt"));
        listFiles.add(new FileData("six.txt", "six.txt"));

        Mockito.when(mockRepository.getListFiles()).thenReturn(listFiles);
        CloudServiceService cloudServiceService = new CloudServiceService(mockRepository);
        CloudServiceService mockService = Mockito.spy(cloudServiceService);
        Mockito.doReturn(true).when(mockService).checkToken(token);

        var result = mockService.getListFiles(rawToken, 5);
        assertEquals(5, result.size());
    }

    @Test
    void shouldConvertFromFileToFileInfo() {
        FileData fileData = new FileData("12345qwerty", "files/file.txt");
        CloudServiceService cloudServiceService = new CloudServiceService(mockRepository);
        var result = cloudServiceService.convertFromFileToFileInfo(fileData);
        assertEquals("file.txt", result.getFileName());
    }

    @Test
    void shouldGetSplitToken() {
        CloudServiceService cloudServiceService = new CloudServiceService(mockRepository);
        String result = cloudServiceService.getSplitToken(rawToken);
        assertEquals("W4VQZRL6TL878AKV4BNUBN5YAH8J9HG5X2M5MKAKJXZRANJEJMH47PX7UYSGCN", result);
    }

    @Test
    void shouldReturnTrueIfTokenInDatabase() {
        CloudServiceService cloudServiceService = new CloudServiceService(mockRepository);
        String token = cloudServiceService.generateToken();
        Mockito.when(mockRepository.checkToken(token)).thenReturn(true);
        assertEquals(true, mockRepository.checkToken(token));
    }
}