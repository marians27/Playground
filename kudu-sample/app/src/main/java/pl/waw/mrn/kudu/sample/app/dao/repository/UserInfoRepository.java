package pl.waw.mrn.kudu.sample.app.dao.repository;

import lombok.AllArgsConstructor;
import org.apache.kudu.client.*;
import pl.waw.mrn.kudu.sample.app.dao.model.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static org.apache.kudu.client.KuduPredicate.ComparisonOp.EQUAL;
import static org.apache.kudu.client.KuduPredicate.newComparisonPredicate;
import static pl.waw.mrn.kudu.sample.app.dao.repository.KuduExecutor.execute;

@AllArgsConstructor
public class UserInfoRepository {
    private final Supplier<KuduClient> clientProvider;
    private final Function<String, String> tableNameConverter;

    public void save(UserInfo userInfo) {
        KuduSession session = clientProvider.get().newSession();
        try {
            execute(() -> {
                KuduTable table = clientProvider.get().openTable(tableName());
                Upsert upsert = table.newUpsert();
                upsert.getRow().addString("id", userInfo.getId());
                upsert.getRow().addString("name", userInfo.getName());
                upsert.getRow().addLong("counter", userInfo.getCounter());
                session.apply(upsert);
            });
        } finally {
            execute(session::close);
        }
    }

    public Optional<UserInfo> findById(String id) {
        return execute(() -> {
            KuduTable table = clientProvider.get().openTable(tableName());
            KuduScanner scanner = clientProvider.get().newScannerBuilder(table)
                    .setProjectedColumnNames(asList("id", "name", "counter"))
                    .addPredicate(
                            newComparisonPredicate(table.getSchema().getColumn("id"), EQUAL, id))
                    .build();

            while (scanner.hasMoreRows()) {
                for (RowResult row : scanner.nextRows()) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(row.getString("id"));
                    userInfo.setName(row.getString("name"));
                    userInfo.setCounter(row.getLong("counter"));
                    return Optional.of(userInfo);
                }
            }

            return Optional.empty();
        });
    }

    public List<UserInfo> findAll() {
        return execute(() -> {
            KuduTable table = clientProvider.get().openTable(tableName());
            KuduScanner scanner = clientProvider.get().newScannerBuilder(table)
                    .setProjectedColumnNames(asList("id", "name", "counter"))
                    .build();

            List<UserInfo> result = new ArrayList<>();
            while (scanner.hasMoreRows()) {
                for (RowResult row : scanner.nextRows()) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(row.getString("id"));
                    userInfo.setName(row.getString("name"));
                    userInfo.setCounter(row.getLong("counter"));
                    result.add(userInfo);
                }
            }

            return result;
        });
    }

    private String tableName() {
        return tableNameConverter.apply("USER_INFO");
    }
}
