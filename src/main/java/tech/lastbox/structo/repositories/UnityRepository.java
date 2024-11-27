package tech.lastbox.structo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.lastbox.structo.model.UnityEntity;

public interface UnityRepository extends JpaRepository<UnityEntity, Long> {}
