package Toon.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import Toon.Entity.teamInfo;
import java.util.List;
import java.util.Optional;

public interface teamInfoRepository extends JpaRepository<teamInfo, Long> {

	Optional<teamInfo> findBycategory(String category);

	List<teamInfo> findByCategory(String string);



    
}
