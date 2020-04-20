import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;
import java.util.Random;

class Interaction {
    Configuration config = new Configuration()
            .configure()
            .addAnnotatedClass(Manager.class)
            .addAnnotatedClass(Developer.class); // startup settings...
    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
    SessionFactory sessionFactory = config.buildSessionFactory(builder.build()); // don't close until the end of program

    void createDevelopers(Integer amount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            session.save(new Developer("Developer" + i, i + "@company.com",
                    "Java", random.nextInt(100)));
        }
        transaction.commit();
        session.close();
    }


    void createManagers(Integer amount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            session.save(new Manager("Manager" + i, i + "@company.com",
                    "Economist", random.nextInt(100)));
        }
        transaction.commit();
        session.close();
    }

   public String fJd() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Session sessionRawQuery = sessionFactory.openSession();
        Query query = sessionRawQuery.createQuery("FROM Developer"); // deprecated
        List <Developer> developers = query.getResultList();
        sessionRawQuery.close();
        return mapper.writeValueAsString(developers);
    }

    public String fJm() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Session sessionRawQuery = sessionFactory.openSession();
        Query query = sessionRawQuery.createQuery("FROM Manager"); // deprecated
        List <Manager> managers = query.getResultList();
        sessionRawQuery.close();
        return mapper.writeValueAsString(managers);
    }

    public void del(String TYPE, int num) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        if (TYPE.equals("m")) {
            Manager man = session.find(Manager.class, num);
            session.delete(man);
        } else if (TYPE.equals("d")) {
            Developer dev = session.find(Developer.class, num);
            session.delete(dev);
        }
        transaction.commit();
        session.close();
    }

    public void updName(String TYPE, int num, int KPI) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        if (TYPE.equals("m")) {
            Manager man = session.find(Manager.class, num);
            man.setKPI(KPI);
            session.update(man);
        } else if (TYPE.equals("d")) {
            Developer dev = session.find(Developer.class, num);
            dev.setKPI(KPI);
            session.update(dev);
        }
        transaction.commit();
        session.close();
    }
}
