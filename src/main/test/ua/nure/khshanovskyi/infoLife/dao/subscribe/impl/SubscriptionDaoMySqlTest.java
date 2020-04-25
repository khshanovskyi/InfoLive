package ua.nure.khshanovskyi.infoLife.dao.subscribe.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.*;
import ua.nure.khshanovskyi.infoLife.dao.media.impl.MediaDaoMySql;
import ua.nure.khshanovskyi.infoLife.dao.user.impl.UserDaoMySql;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.dto.ShortSubscriptionJoinDTO;
import ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionJoinUserAndMediaDTO;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.media.builder.MediaBuilder;
import ua.nure.khshanovskyi.infoLife.entity.subscription.Subscription;
import ua.nure.khshanovskyi.infoLife.entity.subscription.builder.SubscriptionBuilder;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.entity.user.builder.UserBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SubscriptionDaoMySqlTest {

    private static Subscription expectedSubscription;
    private static SubscriptionBuilder subscriptionBuilder;
    private static User testUserFromDB;

    private Media mediaFromDB;
    private Subscription subscriptionFromDB;

    private static SubscriptionDaoMySql subscriptionDao;
    private static UserDaoMySql userDao;
    private static MediaDaoMySql mediaDao;

    @BeforeClass
    public static void setUp() {
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

        subscriptionDao = new SubscriptionDaoMySql(dataSources);
        userDao = new UserDaoMySql(dataSources);
        mediaDao = new MediaDaoMySql(dataSources);

        //create test User object
        UserBuilder userBuilder = new UserBuilder();
        User testUser = userBuilder
                .buildUserEmail("subscriptionEmail@test.com")
                .buildUserSurname("subSurname")
                .buildUserName("subName")
                .buildUserPatronymic("subPatronymic")
                .buildUserPhoneNumber(999999999)
                .buildUserRegion("subRegion")
                .buildUserDistrict("subDistrict")
                .buildUserLocality("subLocality")
                .buildUserStreet("subStreet")
                .buildUserHouse("23")
                .buildUserFlat(11)
                .buildUserPostcode(55555)
                .buildUserMoney(111)
                .buildUserPassword("subPassword")
                .buildUserRole(String.valueOf(Constant.USER))
                .buildUserState(String.valueOf(Constant.UNBLOCKED))
                .build();
        //add new test User object to DB
        userDao.create(testUser);

        //create new test Media object for expected result
        MediaBuilder mediaBuilder = new MediaBuilder();
        Media expectedMedia = mediaBuilder
                .buildMediaName("Test44444")
                .buildMediaTopic("TEST44444")
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

        //create new test Subscription object
        testUserFromDB = userDao.getUserByEmail("subscriptionEmail@test.com").get();
        Media testMediaFromDB = mediaDao.getLastCreatedMedia().get();

        subscriptionBuilder = new SubscriptionBuilder();
        expectedSubscription = subscriptionBuilder
                .buildUserId(testUserFromDB.getUserId())
                .buildMediaId(testMediaFromDB.getMediaId())
                .buildDateFrom(new Date(System.currentTimeMillis()))
                .buildDateTo(new Date(System.currentTimeMillis() + 2592000000L))
                .build();
        //add Test subscription to DB
        subscriptionDao.create(expectedSubscription);
        System.out.println("TEST SUBSCRIPTION OBJECT CREATED");
    }

    /**
     * Important!!!
     * Deleting {@link Subscription} from MySQL DB will be BY CASCADE.
     * In MySQL DB installed settings with "foreign keys" where "subscription.user_id" has the foreign key of
     * "user.user_id" and "subscription.id_media" has the foreign key of "media.id_media".
     * So, we will delete a user object or a media object if "subscription" table contains subscription(s)
     * with this user_id or "id_media" this(these) row(s) will be deleted.
     */
    @AfterClass
    public static void tearDown() {
        if (userDao.getUserByEmail("subscriptionEmail@test.com").isPresent()) {
            testUserFromDB = userDao.getUserByEmail("subscriptionEmail@test.com").get();
            if (subscriptionDao.getFullInfoAboutSubscription(testUserFromDB.getUserId()).isPresent()) {
                Subscription fromDB = subscriptionDao.getLastUserSubscription(testUserFromDB.getUserId()).get();
                userDao.deleteUserByUserEmail("subscriptionEmail@test.com");
                System.out.println("TEST USER DELETED!!!");
                if (mediaDao.getMediaById(fromDB.getMediaId()).isPresent()) {
                    mediaDao.deleteMediaById(fromDB.getMediaId());
                    System.out.println("TEST MEDIA DELETED!!!");
                    if (!subscriptionDao.getLastUserSubscription(testUserFromDB.getUserId()).isPresent()) {
                        System.out.println("TEST SUBSCRIPTION DELETING BY CASCADE IS SUCCESS!!!");
                    }
                }
            }
        }
    }

    @Test
    public void getUserSubscriptionsList() {
        testUserFromDB = userDao.getUserByEmail("subscriptionEmail@test.com").get();
        List<Subscription> subscriptionList = subscriptionDao.getUserSubscriptionsList(testUserFromDB.getUserId());
        subscriptionFromDB = subscriptionDao.getLastUserSubscription(testUserFromDB.getUserId()).get();

        for (Subscription subscription : subscriptionList) {
            assertEquals(subscription.getUserId(), subscriptionFromDB.getUserId());
            assertEquals(subscription.getMediaId(), subscriptionFromDB.getMediaId());
        }
    }

    @Test
    public void getLastUserSubscription() {
        testUserFromDB = userDao.getUserByEmail("subscriptionEmail@test.com").get();
        subscriptionFromDB = subscriptionDao.getLastUserSubscription(testUserFromDB.getUserId()).get();

        assertEquals(subscriptionFromDB.getUserId(), expectedSubscription.getUserId());
        assertEquals(subscriptionFromDB.getMediaId(), expectedSubscription.getMediaId());
        assertNotEquals(subscriptionDao.getLastUserSubscription(testUserFromDB.getUserId()), Optional.empty());

        assertEquals(subscriptionDao.getLastUserSubscription(555), Optional.empty());
        assertNotEquals(subscriptionDao.getLastUserSubscription(555), expectedSubscription.getUserId());
    }

    @Test
    public void getFullInfoAboutSubscription() {
        mediaFromDB = mediaDao.getLastCreatedMedia().get();
        testUserFromDB = userDao.getUserByEmail("subscriptionEmail@test.com").get();
        subscriptionFromDB = subscriptionDao.getLastUserSubscription(testUserFromDB.getUserId()).get();
        SubscriptionJoinUserAndMediaDTO fullInfo = subscriptionDao.getFullInfoAboutSubscription(testUserFromDB.getUserId()).get();

        assertEquals(fullInfo.getUserId(), testUserFromDB.getUserId());
        assertEquals(fullInfo.getName(), testUserFromDB.getName());
        assertEquals(fullInfo.getSurname(), testUserFromDB.getSurname());
        assertEquals(fullInfo.getRegion(), testUserFromDB.getRegion());
        assertEquals(fullInfo.getMoney(), testUserFromDB.getMoney());
        assertNotEquals(555, fullInfo.getUserId());

        assertEquals(fullInfo.getMediaId(), mediaFromDB.getMediaId());
        assertEquals(fullInfo.getMediaName(), mediaFromDB.getMediaName());
        assertNotEquals(1, fullInfo.getMediaId());

        assertEquals(fullInfo.getSubscriptionId(), subscriptionFromDB.getSubscriptionId());
    }

    @Test
    public void getAllActivityUserSubscription() {
        mediaFromDB = mediaDao.getLastCreatedMedia().get();
        testUserFromDB = userDao.getUserByEmail("subscriptionEmail@test.com").get();
        subscriptionFromDB = subscriptionDao.getLastUserSubscription(testUserFromDB.getUserId()).get();
        Subscription oldSubscription = subscriptionBuilder
                .buildUserId(testUserFromDB.getUserId())
                .buildMediaId(mediaFromDB.getMediaId())
                .buildDateFrom(new Date(System.currentTimeMillis() - 7776000000L))
                .buildDateTo(new Date(System.currentTimeMillis() - 2592000000L))
                .build();
        //add Test subscription to DB
        subscriptionDao.create(oldSubscription);

        Subscription notActivitySubscription = subscriptionDao.getLastCreatedSubscription().get();

        for (ShortSubscriptionJoinDTO subscriptionDto :
                subscriptionDao.getAllActivityUserSubscription(testUserFromDB.getUserId(), new Date(System.currentTimeMillis()))) {
            assertNotEquals(subscriptionDto.getDateFrom(), notActivitySubscription.getDateFrom());
            assertNotEquals(subscriptionDto.getDateTo(), notActivitySubscription.getDateTo());

            assertEquals(subscriptionDto.getDateFrom(), subscriptionFromDB.getDateFrom());
            assertEquals(subscriptionDto.getDateTo(), subscriptionFromDB.getDateTo());
        }
    }
}