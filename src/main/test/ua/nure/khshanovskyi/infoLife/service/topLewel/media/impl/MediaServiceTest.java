package ua.nure.khshanovskyi.infoLife.service.topLewel.media.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.nure.khshanovskyi.infoLife.dao.media.impl.MediaDaoMySql;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.media.builder.MediaBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MediaServiceTest {

    @InjectMocks
    private MediaService mediaService;

    private static Media expectedMedia;

    private static MediaBuilder mediaBuilder;

    @Mock
    private MediaDaoMySql mediaDao;

    public MediaServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.mediaService = new MediaService(mediaDao);
    }

    @BeforeClass
    public static void setUp(){
        mediaBuilder = new MediaBuilder();
        expectedMedia = mediaBuilder
                .buildMediaId(1)
                .buildMediaName("Test")
                .buildMediaTopic("TEST_TOPIC")
                .buildMediaDescription("test")
                .buildMediaUriLogImg("nameOfLogo.img")
                .buildMediaPdfUri("nameOfPDFFile.pdf")
                .buildMediaSubscribers(1)
                .buildMediaPrice(1)
                .buildMediaQuantityPublicationInMonth(1)
                .buildMediaDate(new Date(System.currentTimeMillis()))
                .buildMediaTime(new Time(System.currentTimeMillis()))
                .build();
    }

    @Test
    public void productList() {
        List<Media> expectedMediaList = new ArrayList<>();
        expectedMediaList.add(expectedMedia);

        when(mediaDao.allMedia()).thenReturn(expectedMediaList);
        assertEquals(mediaService.productList(), expectedMediaList);
        assertNotEquals(mediaService.productList(), null);

        when(mediaDao.allMedia()).thenReturn(null);
        assertEquals(mediaService.productList(), new ArrayList());
        assertNotEquals(mediaService.productList(), expectedMediaList);
    }

    @Test
    public void getMediaById() {
        when(mediaDao.getMediaById(1)).thenReturn(Optional.of(expectedMedia));
        when(mediaDao.getMediaById(2)).thenReturn(Optional.empty());

        assertEquals(mediaService.getMediaById(1), Optional.of(expectedMedia));
        assertEquals(mediaService.getMediaById(2), Optional.empty());
        assertNotEquals(mediaService.getMediaById(2), Optional.of(expectedMedia));
    }

    @Test
    public void createNewMedia() {
        Media newTestMedia = mediaBuilder
                .buildMediaId(3)
                .buildMediaName("Test3")
                .buildMediaTopic("TEST_TOPIC3")
                .buildMediaDescription("test3")
                .buildMediaUriLogImg("nameOfLogo3.img")
                .buildMediaPdfUri("nameOfPDFFile3.pdf")
                .buildMediaSubscribers(3)
                .buildMediaPrice(3)
                .buildMediaQuantityPublicationInMonth(3)
                .buildMediaDate(new Date(System.currentTimeMillis()))
                .buildMediaTime(new Time(System.currentTimeMillis()))
                .build();

        when(mediaDao.getMediaById(3)).thenReturn(Optional.of(newTestMedia));

        assertEquals(mediaService.getMediaById(3), Optional.of(newTestMedia));
        assertNotEquals(mediaService.getMediaById(1), Optional.of(newTestMedia));
    }

    @Test
    public void handleTopicString() {
        String[] topicsArray = {"TEST,TEST,TEST"};

        assertEquals("TEST,TEST,TEST,", mediaService.handleTopicString(topicsArray));
        assertNotEquals("TEST.TEST.TEST.", mediaService.handleTopicString(topicsArray));
    }
}