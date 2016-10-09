package pl.marian.playground.repository.base;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.marian.playground.config.Profiles;
import pl.marian.playground.config.repository.RepositoryConfig;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
@ActiveProfiles(Profiles.DEV)
@TestExecutionListeners(value = DbUnitTestExecutionListener.class, mergeMode = MERGE_WITH_DEFAULTS)
public abstract class SpringAnnotationDbUnIT {
}
