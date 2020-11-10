CREATE TABLE USER_INFO (
  id STRING,
  name STRING,
  counter BIGINT,
  PRIMARY KEY(id)
)
PARTITION BY HASH PARTITIONS 2
STORED AS KUDU
TBLPROPERTIES (
    'kudu.num_tablet_replicas' = '${kudu.default.replica.count}'
);