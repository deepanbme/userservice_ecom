package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.model.Session;
import com.ecommerce.userservice.model.SessionStatus;
import com.ecommerce.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {


    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
    Optional<List<Session>> findAllByUserAndSessionStatus(User user, SessionStatus sessionStatus);
    Optional<Session> findSessionByToken(String token);
}
