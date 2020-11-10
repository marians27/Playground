package pl.waw.mrn.kudu.sample.app.dao.model;

import lombok.Data;

@Data
public class UserInfo {
    private String id;
    private String name;
    private Long counter;
}
