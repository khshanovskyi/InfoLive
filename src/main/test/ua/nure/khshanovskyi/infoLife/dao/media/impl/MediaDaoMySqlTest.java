package ua.nure.khshanovskyi.infoLife.dao.media.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.*;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.media.builder.MediaBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class MediaDaoMySqlTest {


    private static Media expectedMedia;
    private static MediaBuilder mediaBuilder;

    private List<Media> expectedMediaList;
    private List<Media> sortedListFromDB;

    private static MediaDaoMySql mediaDao;

    @BeforeClass
    public static void setUp(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/infolive?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false");
        config.setUsername("root");
        config.setPassword("root");
        config.addDataSourceProperty("autoReconnect", true);
        config.addDataSourceProperty("tcpKeepAlive", true);
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.setMaximumPoolSize(16);
        config.setMinimumIdle(0);
        config.setIdleTimeout(30000);
        HikariDataSource dataSources = new HikariDataSource(config);

        mediaDao = new MediaDaoMySql(dataSources);

        //create new test Media object for expected result
        mediaBuilder = new MediaBuilder();
        expectedMedia = mediaBuilder
                .buildMediaName("Test23249")
                .buildMediaTopic("TEST23249")
                .buildMediaDescription("Description for test.")
                .buildMediaUriLogImg("test_logo.img")
                .buildMediaPdfUri("pdf_file.pdf")
                .buildMediaSubscribers(5).buildMediaPrice(21)
                .buildMediaQuantityPublicationInMonth(4)
                .buildMediaDate(new Date(System.currentTimeMillis()))
                .buildMediaTime(new Time(System.currentTimeMillis()))
                .build();

        //add new test Media object to DB
        mediaDao.createNewMedia(expectedMedia);
        System.out.println("TEST MEDIA OBJECT CREATED!!!");
    }

    @AfterClass
    public static void tearDown(){
        //delete test Media object from DB
        Media lastMedia = mediaDao.getLastCreatedMedia().get();
        mediaDao.deleteMediaById(lastMedia.getMediaId());
        System.out.println("TEST MEDIA OBJECT DELETED!!!");
    }

    @Test
    public void allMedia() {
        assertNotNull(mediaDao.allMedia());
    }

    @Test
    public void getMediaById() {
        Media lastMedia = mediaDao.getLastCreatedMedia().get();

        assertNotNull(mediaDao.getMediaById(lastMedia.getMediaId()));
    }

    @Test
    public void sortByPopular() {
        expectedMediaList = mediaDao.allMedia();
        List<Media> sortedList = expectedMediaList.stream().sorted(Comparator.comparing(Media::getSubscribers).reversed())
                .collect(Collectors.toList());
        sortedListFromDB = mediaDao.sortByPopular();

        assertNotNull(mediaDao.sortByPopular());

        Media expected;
        Media actual;
        for (int e = 0; e < sortedList.size(); e++) {
            expected = sortedList.get(e);
            for (int a = 0; a < sortedListFromDB.size(); a++) {
                actual = sortedListFromDB.get(e);
                assertEquals(expected.getSubscribers(), actual.getSubscribers());
            }
        }
    }

    @Test
    public void sortByTopic() {
        for (Media actual : mediaDao.sortByTopic("TEST23249")) {
            assertEquals(expectedMedia.getMediaName(), actual.getMediaName());
            assertEquals(expectedMedia.getTopic(), actual.getTopic());
        }
        for (Media actual : mediaDao.sortByTopic("CULINARY")) {
            assertNotEquals(expectedMedia.getMediaName(), actual.getMediaName());
            assertNotEquals(expectedMedia.getTopic(), actual.getTopic());
        }
    }

    @Test
    public void sortByName() {
        expectedMediaList = mediaDao.allMedia();
        List<Media> sortedList = expectedMediaList.stream().sorted(Comparator.comparing(Media::getMediaName))
                .collect(Collectors.toList());
        sortedListFromDB = mediaDao.sortByName();

        assertNotNull(mediaDao.sortByName());

        Media expected;
        Media actual;
        for (int e = 0; e < sortedList.size(); e++) {
            expected = sortedList.get(e);
            for (int a = 0; a < sortedListFromDB.size(); a++) {
                actual = sortedListFromDB.get(e);
                assertEquals(expected.getMediaName(), actual.getMediaName());
            }
        }
    }

    @Test
    public void sortByPriceAsc() {
        expectedMediaList = mediaDao.allMedia();
        List<Media> sortedList = expectedMediaList.stream().sorted(Comparator.comparing(Media::getPrice))
                .collect(Collectors.toList());
        sortedListFromDB = mediaDao.sortByPriceAsc();

        assertNotNull(mediaDao.sortByPriceAsc());

        Media expected;
        Media actual;
        for (int e = 0; e < sortedList.size(); e++) {
            expected = sortedList.get(e);
            for (int a = 0; a < sortedListFromDB.size(); a++) {
                actual = sortedListFromDB.get(e);
                assertEquals(expected.getPrice(), actual.getPrice());
            }
        }
    }

    @Test
    public void sortByPriceDesc() {
        expectedMediaList = mediaDao.allMedia();
        List<Media> sortedList = expectedMediaList.stream().sorted(Comparator.comparing(Media::getPrice).reversed())
                .collect(Collectors.toList());
        sortedListFromDB = mediaDao.sortByPriceDesc();

        assertNotNull(mediaDao.sortByPriceDesc());

        Media expected;
        Media actual;
        for (int e = 0; e < sortedList.size(); e++) {
            expected = sortedList.get(e);
            for (int a = 0; a < sortedListFromDB.size(); a++) {
                actual = sortedListFromDB.get(e);
                assertEquals(expected.getPrice(), actual.getPrice());
            }
        }
    }

    @Test
    public void searchFromSearchField() {
        for (Media actual : mediaDao.searchFromSearchField("Test23249")) {
            assertEquals(expectedMedia.getMediaName(), actual.getMediaName());
            assertEquals(expectedMedia.getTopic(), actual.getTopic());
        }
        for (Media actual : mediaDao.searchFromSearchField("2000")) {
            assertNotEquals(expectedMedia.getMediaName(), actual.getMediaName());
            assertNotEquals(expectedMedia.getTopic(), actual.getTopic());
        }
    }

    @Test
    public void updateSubscribersQuantity() {
        Media lastMedia = mediaDao.getLastCreatedMedia().get();
        assertEquals(expectedMedia.getSubscribers(), lastMedia.getSubscribers());

        mediaDao.updateSubscribersQuantity(22222, lastMedia.getMediaId());
        Media updatedLastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals(22222, updatedLastMedia.getSubscribers());
        assertNotEquals(expectedMedia.getSubscribers(), updatedLastMedia.getSubscribers());

        mediaDao.updateSubscribersQuantity(5, lastMedia.getMediaId());
    }

    @Test
    public void createNewMedia() {
        Media testMedia = mediaBuilder.buildMediaName("Test111111Test").buildMediaTopic("TEST11111").
                buildMediaDescription("Description for test.").buildMediaUriLogImg("test_logo.img").
                buildMediaPdfUri("pdf_file.pdf").buildMediaSubscribers(5).buildMediaPrice(21).
                buildMediaQuantityPublicationInMonth(4).buildMediaDate(new Date(System.currentTimeMillis())).
                buildMediaTime(new Time(System.currentTimeMillis())).build();
        mediaDao.createNewMedia(testMedia);
        Media getCreatedMedia = mediaDao.getLastCreatedMedia().get();

        assertNotNull(getCreatedMedia);
        assertEquals(testMedia.getMediaName(), getCreatedMedia.getMediaName());

        mediaDao.deleteMediaById(getCreatedMedia.getMediaId());
    }

    @Test
    public void getAllNamesOfImgLogo() {
        expectedMediaList = mediaDao.allMedia();
        List<String> expectedListOfLogoPicturesNames = new ArrayList<>();
        for (Media media : expectedMediaList) {
            expectedListOfLogoPicturesNames.add(media.getUriLogoImg());
        }
        Collections.sort(expectedListOfLogoPicturesNames);

        List<String> listOfLogoPicturesNames = mediaDao.getAllNamesOfImgLogo();
        Collections.sort(listOfLogoPicturesNames);

        String expected;
        String actual;
        for (int e = 0; e < expectedListOfLogoPicturesNames.size(); e++) {
            expected = expectedListOfLogoPicturesNames.get(e);
            for (int a = 0; a < listOfLogoPicturesNames.size(); a++) {
                actual = listOfLogoPicturesNames.get(e);
                assertEquals(expected, actual);
            }
        }
    }

    @Test
    public void getAllNamesOfPdf() {
        expectedMediaList = mediaDao.allMedia();
        List<String> expectedListOfPDFFileNames = new ArrayList<>();
        for (Media media : expectedMediaList) {
            expectedListOfPDFFileNames.add(media.getPdfUri());
        }
        Collections.sort(expectedListOfPDFFileNames);

        List<String> listOfpDFFilesNamesFromDB = mediaDao.getAllNamesOfPdf();
        Collections.sort(listOfpDFFilesNamesFromDB);

        String expected;
        String actual;
        for (int e = 0; e < expectedListOfPDFFileNames.size(); e++) {
            expected = expectedListOfPDFFileNames.get(e);
            for (int a = 0; a < listOfpDFFilesNamesFromDB.size(); a++) {
                actual = listOfpDFFilesNamesFromDB.get(e);
                assertEquals(expected, actual);
            }
        }
    }

    @Test
    public void updateMediaName() {
        Media lastMedia = mediaDao.getLastCreatedMedia().get();
        assertEquals(expectedMedia.getMediaName(), lastMedia.getMediaName());

        mediaDao.updateMediaName("UpdatedTestName", lastMedia.getMediaId());
        Media updatedLastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals("UpdatedTestName", updatedLastMedia.getMediaName());
        assertNotEquals(expectedMedia.getMediaName(), updatedLastMedia.getMediaName());

        mediaDao.updateMediaName("Test23249", lastMedia.getMediaId());
    }

    @Test
    public void updateMediaDescription() {
        Media lastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals(expectedMedia.getDescription(), lastMedia.getDescription());

        mediaDao.updateMediaDescription("Want to sort your Lists?", lastMedia.getMediaId());
        Media updatedLastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals("Want to sort your Lists?", updatedLastMedia.getDescription());
        assertNotEquals(expectedMedia.getDescription(), updatedLastMedia.getDescription());

        mediaDao.updateMediaDescription("Description for test.", lastMedia.getMediaId());
    }

    @Test
    public void updateMediaImageLogoUri() {
        Media lastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals(expectedMedia.getUriLogoImg(), lastMedia.getUriLogoImg());

        mediaDao.updateMediaImageLogoUri("newTestLogo.img", lastMedia.getMediaId());
        Media updatedLastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals("newTestLogo.img", updatedLastMedia.getUriLogoImg());
        assertNotEquals(expectedMedia.getUriLogoImg(), updatedLastMedia.getUriLogoImg());

        mediaDao.updateMediaImageLogoUri("test_logo.img", lastMedia.getMediaId());
    }

    @Test
    public void updateMediaPdfUri() {
        Media lastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals(expectedMedia.getPdfUri(), lastMedia.getPdfUri());

        mediaDao.updateMediaPdfUri("newTestPdfUri.pdf", lastMedia.getMediaId());
        Media updatedLastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals("newTestPdfUri.pdf", updatedLastMedia.getPdfUri());
        assertNotEquals(expectedMedia.getPdfUri(), updatedLastMedia.getPdfUri());

        mediaDao.updateMediaPdfUri("pdf_file.pdf", lastMedia.getMediaId());
    }

    @Test
    public void updateMediaTopic() {
        Media lastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals(expectedMedia.getTopic(), lastMedia.getTopic());

        mediaDao.updateMediaTopic("TEST,TEST,", lastMedia.getMediaId());
        Media updatedLastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals("TEST,TEST,", updatedLastMedia.getTopic());
        assertNotEquals(expectedMedia.getTopic(), updatedLastMedia.getTopic());

        mediaDao.updateMediaTopic("TEST23249", lastMedia.getMediaId());
    }

    @Test
    public void updateMediaPrice() {
        Media lastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals(expectedMedia.getPrice(), lastMedia.getPrice());

        mediaDao.updateMediaPrice(25001, lastMedia.getMediaId());
        Media updatedLastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals(25001, updatedLastMedia.getPrice());
        assertNotEquals(expectedMedia.getPrice(), updatedLastMedia.getPrice());

        mediaDao.updateMediaPrice(21, lastMedia.getMediaId());
    }

    @Test
    public void updateMediaPublicationsQuantity() {
        Media lastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals(expectedMedia.getQuantityPublicationInMonth(), lastMedia.getQuantityPublicationInMonth());

        mediaDao.updateMediaPublicationsQuantity(33, lastMedia.getMediaId());
        Media updatedLastMedia = mediaDao.getLastCreatedMedia().get();

        assertEquals(33, updatedLastMedia.getQuantityPublicationInMonth());
        assertNotEquals(expectedMedia.getQuantityPublicationInMonth(), updatedLastMedia.getQuantityPublicationInMonth());

        mediaDao.updateMediaPublicationsQuantity(4, lastMedia.getMediaId());
    }

    @Test
    public void deleteMediaById() {
        Media testMedia = mediaBuilder.buildMediaName("TestForDelete").buildMediaTopic("TEST,TEST1,").
                buildMediaDescription("Description for test.").buildMediaUriLogImg("test_logo.img").
                buildMediaPdfUri("pdf_file.pdf").buildMediaSubscribers(5).buildMediaPrice(21).
                buildMediaQuantityPublicationInMonth(4).buildMediaDate(new Date(System.currentTimeMillis())).
                buildMediaTime(new Time(System.currentTimeMillis())).build();
        mediaDao.createNewMedia(testMedia);
        Media getCreatedMedia = mediaDao.getLastCreatedMedia().get();

        assertNotNull(getCreatedMedia);
        assertEquals(testMedia.getMediaName(), getCreatedMedia.getMediaName());

        mediaDao.deleteMediaById(getCreatedMedia.getMediaId());
        getCreatedMedia = mediaDao.getLastCreatedMedia().get();

        assertNotEquals(testMedia.getMediaName(), getCreatedMedia.getMediaName());
    }
}