package com.exadel.team2.sandbox.service.impl;

import com.exadel.team2.sandbox.BaseTestClass;
import com.exadel.team2.sandbox.dao.EventDAO;
import com.exadel.team2.sandbox.dao.ImageDAO;
import com.exadel.team2.sandbox.entity.ImageEntity;
import com.exadel.team2.sandbox.exceptions.NoSuchException;
import com.exadel.team2.sandbox.mapper.ImageMapper;
import com.exadel.team2.sandbox.mapper.ModelMap;
import com.exadel.team2.sandbox.service.EventService;
import com.exadel.team2.sandbox.service.ImageService;
import com.exadel.team2.sandbox.service.ImageUploadService;
import com.exadel.team2.sandbox.web.image.ImageResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ImageServiceImpl.class, ImageMapper.class, ModelMap.class})
public class ImageServiceImplTest extends BaseTestClass {

    private static final long IMAGE_ID = 3L;

    @MockBean
    private ImageDAO imageDAO;

    @MockBean
    private EventDAO eventDAO;

    @MockBean
    private EventServiceImpl eventServiceImpl;

    @MockBean
    private ImageUploadServiceImpl imageUploadServiceImpl;

    @Autowired
    private EventService eventService;

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private ImageService imageService;

    @Test
    void getById_imageExists_ok() {
        when(imageDAO.findById(IMAGE_ID)).thenReturn(getImageResponseEntity());

        ImageResponseDTO actualImageResponse = imageService.getById(IMAGE_ID);

        Assertions.assertEquals(IMAGE_ID, actualImageResponse.getId());
    }

    @Test
    void getById_noImage_exceptionThrown() {
        when(imageDAO.findById(IMAGE_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchException.class, () -> imageService.getById(IMAGE_ID));
    }

    @Test
    void getAll_imagesExistInDB_ok() {
        List<ImageEntity> imageEntities = createImageEntities();
        when(imageDAO.findAll()).thenReturn(imageEntities);

        List<ImageResponseDTO> actualImages = imageService.getAll();

        List<ImageResponseDTO> expectedImages = createImageDto();
        Assertions.assertEquals(expectedImages, actualImages);
    }

    @Test
    void getAll_imagesDoesNotExist_exceptionThrown() {
        List<ImageEntity> emptyEntity = createEmptyEntity();
        when(imageDAO.findAll()).thenReturn(emptyEntity);

        assertThrows(NoSuchException.class, () -> imageService.getAll());
    }

    @Test
    void getAll_imagesDoesNotExist_thrownNullPointerException() {
        when(imageDAO.findAll()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> imageService.getAll());
    }

    @Test
    void deleteById_imageExists_ok() {
        when(imageDAO.existsById(IMAGE_ID)).thenReturn(true);

        boolean status = imageService.delete(IMAGE_ID);

        verify(imageDAO).deleteById(IMAGE_ID);
        Assertions.assertTrue(status);
    }

    @Test
    void deleteById_imageDoesNotExist_exceptionThrown() {
        when(imageDAO.existsById(IMAGE_ID)).thenReturn(false);

        assertThrows(NoSuchException.class, () -> imageService.delete(IMAGE_ID));
    }


    private Optional<ImageEntity> getImageResponseEntity() {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(IMAGE_ID);
        imageEntity.setName("Java cover");
        imageEntity.setAltText("Short description about image");
        imageEntity.setExt("jpg");
        imageEntity.setSize(5L);
        imageEntity.setCreatedAt(LocalDateTime.now());

        return Optional.of(imageEntity);
    }

    private List<ImageEntity> createImageEntities() {
        ImageEntity imageEntity1 = new ImageEntity();
        imageEntity1.setId(1L);

        ImageEntity imageEntity2 = new ImageEntity();
        imageEntity2.setId(2L);

        ImageEntity imageEntity3 = new ImageEntity();
        imageEntity3.setId(3L);

        List<ImageEntity> imageEntities = new ArrayList<>();
        imageEntities.add(imageEntity1);
        imageEntities.add(imageEntity2);
        imageEntities.add(imageEntity3);

        return imageEntities;
    }

    private List<ImageResponseDTO> createImageDto() {
        ImageResponseDTO imageResponseDTO1 = new ImageResponseDTO();
        imageResponseDTO1.setId(1L);

        ImageResponseDTO imageResponseDTO2 = new ImageResponseDTO();
        imageResponseDTO2.setId(2L);

        ImageResponseDTO imageResponseDTO3 = new ImageResponseDTO();
        imageResponseDTO3.setId(3L);

        List<ImageResponseDTO> imageResponseDTOs = new ArrayList<ImageResponseDTO>();
        imageResponseDTOs.add(imageResponseDTO1);
        imageResponseDTOs.add(imageResponseDTO2);
        imageResponseDTOs.add(imageResponseDTO3);

        return imageResponseDTOs;
    }

    private List<ImageEntity> createEmptyEntity() {
        List<ImageEntity> imageEntities = new ArrayList<>();

        return imageEntities;
    }

    private ImageEntity saveImageEntity() {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName("Java cover");
        imageEntity.setAltText("Short description about image");
        imageEntity.setExt("jpg");
        imageEntity.setSize(5L);

        return imageEntity;
    }

    private ImageResponseDTO responseDto() {
        ImageResponseDTO imageDTO = new ImageResponseDTO();
        imageDTO.setId(IMAGE_ID);
        imageDTO.setName("Java cover");
        imageDTO.setAltText("Short description about image");
        imageDTO.setExt("jpg");
        imageDTO.setSize(5L);
        imageDTO.setCreatedAt(LocalDateTime.now());

        return imageDTO;
    }
}