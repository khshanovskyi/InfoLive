package ua.nure.khshanovskyi.infoLife.listener;

import ua.nure.khshanovskyi.infoLife.dao.media.IMediaDao;
import ua.nure.khshanovskyi.infoLife.dao.media.impl.MediaDaoMySql;
import ua.nure.khshanovskyi.infoLife.dao.subscribe.ISubscriptionDao;
import ua.nure.khshanovskyi.infoLife.dao.subscribe.impl.SubscriptionDaoMySql;
import ua.nure.khshanovskyi.infoLife.dao.user.IUserDao;
import ua.nure.khshanovskyi.infoLife.dao.user.impl.UserDaoMySql;
import ua.nure.khshanovskyi.infoLife.service.topLewel.media.IMediaService;
import ua.nure.khshanovskyi.infoLife.service.topLewel.media.impl.MediaService;
import ua.nure.khshanovskyi.infoLife.service.topLewel.subscribe.ISubscriptionService;
import ua.nure.khshanovskyi.infoLife.service.topLewel.subscribe.impl.SubscriptionService;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.IUserService;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.impl.UserServiceImpl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This class implementation of {@link ServletContextListener}.
 * This class like centre of this project. Here we do initialization of {@link IUserDao}, {@link IMediaDao},
 * {@link ISubscriptionDao} and there service.
 */
@WebListener
public class InitializationListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/infolive?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false");
        config.setUsername("root");
        config.setPassword("root");
        config.addDataSourceProperty("autoReconnect",true);
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

        //UserDaoObj with UserDaoMySql realization
        IUserDao userDao = new UserDaoMySql(dataSources);
        //Service for user with UserServiceImpl realization
        IUserService userService = new UserServiceImpl(userDao);

        //MediaDaoObj with MediaDaoMySql realization
        IMediaDao mediaDao = new MediaDaoMySql(dataSources);
        //Service for user with UserServiceImpl realization
        IMediaService mediaService = new MediaService(mediaDao);

        //SubscribeDaoObj with SubscribeDaoMySql realization
        ISubscriptionDao subscribeDao = new SubscriptionDaoMySql(dataSources);
        //Service for subscribe with SubscribeService realization
        ISubscriptionService subscriptionService = new SubscriptionService(subscribeDao);

        //context for user
        servletContext.setAttribute("userService", userService);
        //context for media
        servletContext.setAttribute("mediaService", mediaService);
        //context for subscribe
        servletContext.setAttribute("subscriptionService", subscriptionService);

        //install Resource Bundle with Ukrainian language
        servletContext.setAttribute("bundle", "default__en_US_uk_UA");
        LOGGER.info("ContextListener initialized!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("ContextListener destroyed!");
    }
}
