package indi.yufr.redis.service;

import indi.yufr.redis.model.Address;
import indi.yufr.redis.model.PersonEntity;
import indi.yufr.redis.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date: 2021/5/21 9:26
 * @author: farui.yu
 */
@Service
@Slf4j
public class RedisCrudService {

    @Autowired
    private PersonRepository repo;

    public void repoTest() {
        PersonEntity rand = new PersonEntity("rand", "yufr");

        rand.setAddress(new Address("shanHai"));

        // hmset person:rand
        repo.save(rand);

        // hgetall person:rand
        log.info("rand in redis {}", repo.findById(rand.getId()));

        //
        log.info("person in redis count, [{}]", repo.count());

//        repo.deleteById(rand.getId());
    }
}
