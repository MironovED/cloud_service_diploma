package ru.netology.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.dto.EditInfo;
import ru.netology.repository.CloudServiceRepository;
import ru.netology.service.CloudServiceService;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CloudServiceController.class)
class CloudServiceControllerTest {
    private final String rawToken = "Bearer W4VQZRL6TL878AKV4BNUBN5YAH8J9HG5X2M5MKAKJXZRANJEJMH47PX7UYSGCN";
    private final String token = "W4VQZRL6TL878AKV4BNUBN5YAH8J9HG5X2M5MKAKJXZRANJEJMH47PX7UYSGCN";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CloudServiceService mockCloudServiceService;
    @MockitoBean
    private CloudServiceRepository mockRepository;

    @BeforeEach
    void setUp() {
        Mockito.doReturn(true).when(mockCloudServiceService).checkToken(rawToken);
        Mockito.doReturn(true).when(mockRepository).checkToken(token);
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/cloud/logout")
                        .header("auth-token", rawToken))
                .andExpect(status().isOk());
    }

    @Test
    void uploadFile() throws Exception {
        mockMvc.perform(multipart("/cloud/file")
                        .file("file", "test".getBytes(StandardCharsets.UTF_8))
                        .header("auth-token", rawToken)
                        .param("filename", "test.txt")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Success upload"));
    }

    @Test
    void shouldReturnExceptionWhenUploadFile() throws Exception {
        mockMvc.perform(multipart("/cloud/file")
                        .file("file", "test".getBytes(StandardCharsets.UTF_8))
                        .header("auth-token", rawToken)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteFile() throws Exception {
        mockMvc.perform(delete("/cloud/file")
                        .param("filename", "test.txt")
                        .header("auth-token", rawToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Success deleted"));
    }

    @Test
    void shouldReturnExceptionWhenDeleteFile() throws Exception {
        mockMvc.perform(delete("/cloud/file")
                        .header("auth-token", rawToken))
                .andExpect(status().is4xxClientError());
    }

//    @Test
//    void downloadFile() throws Exception {
//        mockMvc.perform(delete("/cloud/file")
//                        .param("filename", "test.txt")
//                        .header("auth-token", rawToken))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Success deleted"));
//    }

    @Test
    void shouldReturnExceptionWhenDownloadFile() throws Exception {
        mockMvc.perform(get("/cloud/file")
                        .header("auth-token", rawToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void editFile() throws Exception {
        EditInfo editInfo = new EditInfo("test1.txt");
        mockMvc.perform(put("/cloud/file")
                        .param("filename", "test.txt")
                        .header("auth-token", rawToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editInfo)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success upload"));
    }

    @Test
    void shouldReturnExceptionWhenEditFile() throws Exception {
        EditInfo editInfo = new EditInfo("test1.txt");
        mockMvc.perform(put("/cloud/file")
                        .header("auth-token", rawToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editInfo)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAllFiles() throws Exception {
        Mockito.doReturn(true).when(mockCloudServiceService).checkToken(rawToken);
        Mockito.doReturn(true).when(mockRepository).checkToken(token);
        mockMvc.perform(get("/cloud/list")
                        .param("limit", "4")
                        .header("auth-token", rawToken))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnExceptionWhenGetAllFiles() throws Exception {
        mockMvc.perform(get("/cloud/list")
                        .param("limit", "4"))
                .andExpect(status().is4xxClientError());
    }
}