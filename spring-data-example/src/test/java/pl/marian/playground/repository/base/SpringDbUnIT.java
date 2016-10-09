package pl.marian.playground.repository.base;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.marian.playground.config.Profiles;
import pl.marian.playground.config.repository.RepositoryConfig;

import javax.sql.DataSource;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
@ActiveProfiles(Profiles.DEV)
public abstract class SpringDbUnIT {

    @Autowired
    private DataSource dataSource;

    protected void loadDataSet(String dataSetFile) throws Exception {
        IDataSet dataSet = readDataSet(dataSetFile);
        loadDataSet(dataSet);
    }

    private void loadDataSet(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    private IDataSet readDataSet(String dataSetFile) throws Exception {
        URL resourceUrl = getClass().getClassLoader().getResource(dataSetFile);
        assertNotNull("Can not find resource on classpath: " + dataSetFile, resourceUrl);
        return new FlatXmlDataSetBuilder().build(resourceUrl);
    }
}
