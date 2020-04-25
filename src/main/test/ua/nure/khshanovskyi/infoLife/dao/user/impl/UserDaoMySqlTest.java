package ua.nure.khshanovskyi.infoLife.dao.user.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.*;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.entity.user.builder.UserBuilder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserDaoMySqlTest {

    private static User expectedUser;
    private static UserBuilder userBuilder;

    private User userFromDB;

    private static UserDaoMySql userDao;

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

        userDao = new UserDaoMySql(dataSources);

        //crate new User object by UserBuilder for expected result
        userBuilder = new UserBuilder();
        expectedUser = userBuilder
                .buildUserEmail("testUserEmail@test.com")
                .buildUserSurname("TestSurname")
                .buildUserName("TestName")
                .buildUserPatronymic("TestPatronymic")
                .buildUserPhoneNumber(999999999)
                .buildUserRegion("TestRegion")
                .buildUserDistrict("TestDistrict")
                .buildUserLocality("TestLocality")
                .buildUserStreet("TestStreet")
                .buildUserHouse("23")
                .buildUserFlat(11)
                .buildUserPostcode(55555)
                .buildUserMoney(111)
                .buildUserPassword("testPassword")
                .buildUserRole(String.valueOf(Constant.USER))
                .buildUserState(String.valueOf(Constant.UNBLOCKED))
                .build();

        //add new test User object to DB
        userDao.create(expectedUser);
        System.out.println("TEST OBJECT CREATED!");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (userDao.getUserByEmail("testUserEmail@test.com").isPresent()) {
            userDao.deleteUserByUserEmail("testUserEmail@test.com");
            System.out.println("TEST OBJECT DELETED!");
        }
    }

    @Test
    public void getUserById() {
        userFromDB = userDao.getLastCreatedUser().get();

        assertEquals(userDao.getUserById(userFromDB.getUserId()).get(), userFromDB);
    }

    @Test
    public void getUserByEmail() {
        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getEmail(), expectedUser.getEmail());
        assertNotEquals(userDao.getUserByEmail("blocked@block.bl").get().getEmail(), expectedUser.getEmail());
    }

    @Test
    public void create() {
        User newTestUser = userBuilder
                .buildUserEmail("secondTestEmail@test.com")
                .buildUserSurname("secondTestSur")
                .buildUserName("secondTestName")
                .buildUserPatronymic("secondTestPatron")
                .buildUserPhoneNumber(888888888)
                .buildUserRegion("secondTestReg")
                .buildUserDistrict("secondTestDis")
                .buildUserLocality("secondTestLocal")
                .buildUserStreet("secondTestStr")
                .buildUserHouse("232")
                .buildUserFlat(11)
                .buildUserPostcode(77777)
                .buildUserMoney(22)
                .buildUserPassword("secondTestPassword")
                .buildUserRole("USER")
                .buildUserState("UNBLOCKED")
                .build();

        userDao.create(newTestUser);

        assertEquals(userDao.getUserByEmail("secondTestEmail@test.com").get().getEmail(), newTestUser.getEmail());
        assertNotEquals(userDao.getUserByEmail("secondTestEmail@test.com").get().getEmail(), expectedUser.getEmail());

        userDao.deleteUserByUserEmail("secondTestEmail@test.com");

        assertEquals(userDao.getUserByEmail("secondTestEmail@test.com"), Optional.empty());
    }

    @Test
    public void updateUserEmail() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserEmail(userFromDB.getUserId(), "updatedUserEmail@test.com");

        assertEquals(userDao.getUserByEmail("updatedUserEmail@test.com").get().getUserId(), userFromDB.getUserId());
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getEmail(), userFromDB.getEmail());

        userDao.updateUserEmail(userFromDB.getUserId(), "testUserEmail@test.com");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getEmail(), userFromDB.getEmail());
    }

    @Test
    public void updateUserSurname() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserSurname(userFromDB.getUserId(), "updatedSurname");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getSurname(), "updatedSurname");
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getSurname(), userFromDB.getSurname());

        userDao.updateUserSurname(userFromDB.getUserId(), "TestSurname");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getSurname(), userFromDB.getSurname());
    }

    @Test
    public void updateUserName() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserName(userFromDB.getUserId(), "updatedName");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getName(), "updatedName");
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getName(), userFromDB.getName());

        userDao.updateUserName(userFromDB.getUserId(), "TestName");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getName(), userFromDB.getName());
    }

    @Test
    public void updateUserPatronymic() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserPatronymic(userFromDB.getUserId(), "UpdatedPatron");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getPatronymic(), "UpdatedPatron");
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getPatronymic(), userFromDB.getPatronymic());

        userDao.updateUserPatronymic(userFromDB.getUserId(), "TestPatronymic");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getPatronymic(), userFromDB.getPatronymic());
    }

    @Test
    public void updateUserPhoneNumber() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserPhoneNumber(userFromDB.getUserId(), 888888888);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getPhoneNumber(), 888888888);
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getPhoneNumber(), userFromDB.getPhoneNumber());

        userDao.updateUserPhoneNumber(userFromDB.getUserId(), 999999999);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getPhoneNumber(), userFromDB.getPhoneNumber());
    }

    @Test
    public void updateUserRegion() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserRegion(userFromDB.getUserId(), "UpdatedReg");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getRegion(), "UpdatedReg");
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getRegion(), userFromDB.getRegion());

        userDao.updateUserRegion(userFromDB.getUserId(), "TestRegion");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getRegion(), userFromDB.getRegion());
    }

    @Test
    public void updateUserDistrict() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserDistrict(userFromDB.getUserId(), "UpdatedDis");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getDistrict(), "UpdatedDis");
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getDistrict(), userFromDB.getDistrict());

        userDao.updateUserDistrict(userFromDB.getUserId(), null);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getDistrict(), "null");

        userDao.updateUserDistrict(userFromDB.getUserId(), "TestDistrict");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getDistrict(), userFromDB.getDistrict());
    }

    @Test
    public void updateUserLocality() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserLocality(userFromDB.getUserId(), "UpdatedLocal");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getLocality(), "UpdatedLocal");
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getLocality(), userFromDB.getLocality());

        userDao.updateUserLocality(userFromDB.getUserId(), "TestLocality");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getLocality(), userFromDB.getLocality());
    }

    @Test
    public void updateUserStreet() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserStreet(userFromDB.getUserId(), "UpdatedStreet");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getStreet(), "UpdatedStreet");
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getStreet(), userFromDB.getStreet());

        userDao.updateUserStreet(userFromDB.getUserId(), "TestStreet");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getStreet(), userFromDB.getStreet());
    }

    @Test
    public void updateUserHouse() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserHouse(userFromDB.getUserId(), "33");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getHouse(), "33");
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getHouse(), userFromDB.getHouse());

        userDao.updateUserHouse(userFromDB.getUserId(), "23");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getHouse(), userFromDB.getHouse());
    }

    @Test
    public void updateUserFlat() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserFlat(userFromDB.getUserId(), 22);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getFlat(), 22);
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getFlat(), userFromDB.getFlat());

        userDao.updateUserFlat(userFromDB.getUserId(), 0);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getFlat(), 0);

        userDao.updateUserFlat(userFromDB.getUserId(), 11);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getFlat(), userFromDB.getFlat());
    }

    @Test
    public void updateUserPostcode() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserPostcode(userFromDB.getUserId(), 7);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getPostcode(), 7);
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getPostcode(), userFromDB.getPostcode());

        userDao.updateUserPostcode(userFromDB.getUserId(), 55555);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getPostcode(), userFromDB.getPostcode());
    }

    @Test
    public void updateUserMoney() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserMoney(userFromDB.getUserId(), 1);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getMoney(), 1);
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getMoney(), userFromDB.getMoney());

        userDao.updateUserMoney(userFromDB.getUserId(), 111);

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getMoney(), userFromDB.getMoney());
    }

    @Test
    public void updateUserPassword() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserPassword(userFromDB.getUserId(), "updatedPassword");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getPassword(), "updatedPassword");
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getPassword(), userFromDB.getPassword());

        userDao.updateUserPassword(userFromDB.getUserId(), "testPassword");

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getPassword(), userFromDB.getPassword());
    }

    @Test
    public void updateUserRole() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserRole(userFromDB.getUserId(), String.valueOf(Constant.ADMIN));

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getRole(), String.valueOf(Constant.ADMIN));
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getRole(), userFromDB.getRole());

        userDao.updateUserRole(userFromDB.getUserId(), String.valueOf(Constant.USER));

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getRole(), userFromDB.getRole());
    }

    @Test
    public void updateUserState() {
        userFromDB = userDao.getUserByEmail("testUserEmail@test.com").get();
        userDao.updateUserState(userFromDB.getEmail(), String.valueOf(Constant.BLOCKED));

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getState(), String.valueOf(Constant.BLOCKED));
        assertNotEquals(userDao.getUserById(userFromDB.getUserId()).get().getState(), userFromDB.getState());

        userDao.updateUserState(userFromDB.getEmail(), String.valueOf(Constant.UNBLOCKED));

        assertEquals(userDao.getUserByEmail("testUserEmail@test.com").get().getState(), userFromDB.getState());
    }
}