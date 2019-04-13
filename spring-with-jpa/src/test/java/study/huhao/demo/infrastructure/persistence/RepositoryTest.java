package study.huhao.demo.infrastructure.persistence;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
        // This listener can let @Transactional work in test and rollback by default. More information please look:
        // https://stackoverflow.com/questions/14474319/no-transactional-entitymanager-available
        TransactionalTestExecutionListener.class,
        // These two listener can let @FlywayTest to work.
        DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class
})
@FlywayTest
@Transactional
public abstract class RepositoryTest {
}
