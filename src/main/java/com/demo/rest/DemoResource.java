package com.demo.rest;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mt.dao.ActorDao;
import com.mt.model.Actor;


@RestController
@RequestMapping(value = "/demo")
@Transactional
public class DemoResource {

	@Autowired
	private ActorDao actorDao;

	@GetMapping(value = "/{id}")
	public String getActor(@PathVariable("id") String id) {
		Actor actor = this.actorDao.getOne(Integer.valueOf(id));
		return String.format("[actor: %s %s], [DemoResource instance: %s], [ActorDao instance: %s]",
				actor.getFirstName(), actor.getLastName(), this, this.actorDao);
	}

	@PatchMapping(value = "/{id}")
	public String updateActor(@PathVariable("id") String id, @RequestBody Map<String, Object> updates) {
		Actor actor = this.actorDao.getOne(Integer.valueOf(id));
		actor.setFirstName((String) updates.get("firstName"));
		return String.format("[actor: %s %s], [DemoResource instance: %s], [ActorDao instance: %s]",
				actor.getFirstName(), actor.getLastName(), this, this.actorDao);
	}
}