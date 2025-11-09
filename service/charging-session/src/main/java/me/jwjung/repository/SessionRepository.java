package me.jwjung.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import me.jwjung.domain.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
	Optional<Session> findByMemberUuid(String memberUuid);
}
