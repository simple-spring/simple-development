package ${packagePath}.cassandra.repository;

import ${packagePath}.cassandra.table.DemoCassandraDo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liko.wang
 * @Date 2020/3/31/031 12:37
 * @Description demo
 **/
@Repository
public interface DemoRepository extends CrudRepository<DemoCassandraDo, String> {

}