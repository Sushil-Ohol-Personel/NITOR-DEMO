package com.mt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mt.model.Actor;

public interface ActorDao extends JpaRepository<Actor, Integer> {
}