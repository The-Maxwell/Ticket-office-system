package entities;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = configureSessionFactory();
    private static ServiceRegistry serviceRegistry;

    private static SessionFactory configureSessionFactory()
            throws HibernateException {
        // Налаштування hibernate
        Configuration configuration = new Configuration()
                .setProperty( "hibernate.connection.driver_class",
                        "com.mysql.cj.jdbc.Driver" )
                .setProperty( "hibernate.connection.url",
                        "jdbc:mysql://localhost:3306/ticketoffice_c?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false" )
                .setProperty( "hibernate.connection.username", "root" )
                .setProperty( "hibernate.connection.password", "root" )
                .setProperty( "hibernate.connection.pool_size", "2" )
                .setProperty( "hibernate.connection.autocommit", "false" )
                .setProperty( "hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider" )
                .setProperty( "hibernate.cache.use_second_level_cache", "false" )
                .setProperty( "hibernate.cache.use_query_cache", "false" )
                .setProperty( "hibernate.dialect", "org.hibernate.dialect.MySQLDialect" )
                .setProperty( "hibernate.show_sql","true" )
                .setProperty( "hibernate.current_session_context_class", "thread" )
                .setProperty( "hibernate.enable_lazy_load_no_trans", "true" )
                .addPackage( "entities" )
                .addAnnotatedClass(JournaryEntity.class)
                .addAnnotatedClass(VehicleEntity.class)
                .addAnnotatedClass(PassengerEntity.class)
                .addAnnotatedClass(ReceiptEntity.class)
                .addAnnotatedClass(TicketEntity.class)
                .addAnnotatedClass(UserEntity.class)
                ;
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(configuration.getProperties());
        serviceRegistry = serviceRegistryBuilder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}