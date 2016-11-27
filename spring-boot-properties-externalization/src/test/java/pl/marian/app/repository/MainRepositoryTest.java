package pl.marian.app.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marian.app.base.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class MainRepositoryTest extends TestBase {

    @Autowired
    private MainRepository mainRepository;

    @Test
    public void verifyIfPropertiesTakenFromProperPlace() {
        assertThat(mainRepository.getDbUser()).isEqualTo("testMainUser");
    }
}