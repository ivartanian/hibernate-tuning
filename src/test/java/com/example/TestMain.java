package com.example;

import com.example.domain.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by viv on 27.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
@DatabaseSetup("/data.xml")
public class TestMain {

    @PersistenceContext
    private EntityManager entityManager;

    @Commit
    @Test
    public void testJpaRepo() throws Exception {
        Session session = getSession();
        for (int i = 1; i <= 1_000_000; i++) {
            session.persist(new User("test" + i, i, null));
            if (i % 50 == 0) {
                session.flush();
                session.clear();
            }
        }
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }
}
