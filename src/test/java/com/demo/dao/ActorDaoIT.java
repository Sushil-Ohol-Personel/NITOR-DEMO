//package com.demo.dao;
//
//import org.hamcrest.Matchers;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.demo.config.poc.TenantContext;
//import com.demo.dao.ActorDao;
//import com.demo.main.Application;
//import com.demo.model.Actor;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = Application.class)
//public class ActorDaoIT {
//
////	@Autowired
////	private ActorDao actorDao;
//
//	@BeforeClass
//	public static void beforeClass() {
//		TenantContext.setTenantId("tenant_1");
//	}
//
//    @AfterClass
//    public static void afterClass() {
//        TenantContext.clear();
//    }
//
////	@Test
////	public void shouldHave200Actors() {
////		Assert.assertThat(this.actorDao.count(), Matchers.equalTo(200L));
////	}
////
////	@Test
////	public void actorShouldBePenelope() {
////		Actor actor = this.actorDao.findOne(1);
////		Assert.assertThat(actor.getFirstName(), Matchers.equalTo("Penelope"));
////	}
//}