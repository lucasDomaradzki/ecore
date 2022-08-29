create database IF not exists ecore;
use ecore;

source /data/database/initial-setup/team-ddl.sql
source /data/database/initial-setup/user-ddl.sql
source /data/database/initial-setup/role-ddl.sql
source /data/database/initial-setup/role-dml.sql

commit;