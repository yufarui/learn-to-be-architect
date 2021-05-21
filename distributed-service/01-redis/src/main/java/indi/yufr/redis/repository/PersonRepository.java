package indi.yufr.redis.repository;

import indi.yufr.redis.model.PersonEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @date: 2021/5/21 9:23
 * @author: farui.yu
 */
public interface PersonRepository extends CrudRepository<PersonEntity, String> {

}
