package ca.carleton.s4806;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long> {
    List<BuddyInfo> findByName(String name);
}
