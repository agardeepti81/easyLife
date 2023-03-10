package org.deeptiagarwal.easylife;

import jakarta.validation.constraints.AssertTrue;
import org.deeptiagarwal.easylife.dao.AuthGroupRepoI;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.ItemsRepoI;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.Items;
import org.deeptiagarwal.easylife.models.Users;
import org.deeptiagarwal.easylife.services.ItemsServices;
import org.deeptiagarwal.easylife.services.UserServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class EasylifeApplicationTests {

	UsersRepoI usersRepoI;
	ItemsRepoI itemsRepoI;
	AuthGroupRepoI authGroupRepoI;
	UserServices userServices;
	ItemsServices itemsServices;

	@Autowired
	public EasylifeApplicationTests(UsersRepoI usersRepoI, ItemsRepoI itemsRepoI, AuthGroupRepoI authGroupRepoI, UserServices userServices, ItemsServices itemsServices) {
		this.usersRepoI = usersRepoI;
		this.itemsRepoI = itemsRepoI;
		this.authGroupRepoI = authGroupRepoI;
		this.userServices = userServices;
		this.itemsServices = itemsServices;
	}

	Users user1 = new Users("Test","test@gmail.com","passWORD@12");
	Items item1 = new Items("Milk 2%",5,"box");
	@Autowired
	private CategoriesRepoI categoriesRepoI;

	@Test
	void contextLoadsOne() {
		if(usersRepoI.findByEmail("johndoe@gmail.com").isPresent()){
			Users user = usersRepoI.findByEmail("johndoe@gmail.com").get();
			assertTrue(true);
		}

		if(usersRepoI.findByEmailAllIgnoreCase("DEEptiag@Gmail.com").isPresent()){
			Users user = usersRepoI.findByEmailAllIgnoreCase("DEEptiag@Gmail.com").get();
			assertTrue(true);
		}
	}

	@Test
	void contextLoadsTwo() {
		if(itemsRepoI.findByUserAndCategory(usersRepoI.findById(2).get(),categoriesRepoI.findById(1).get()).isEmpty()){
			assertTrue(false);
		}

		if (!itemsRepoI.findByUser(usersRepoI.findById(1).get()).isEmpty()) {
			assertTrue(true);
		}
	}

	@Test
	void contextLoadsThree() {
		if(authGroupRepoI.findByEmail("abc123@gmail.com").isEmpty()){
			assertTrue(true);
		}
	}

	@Test
	void exceptionTestingOne() {
		assertThatThrownBy(() -> {
			userServices.addCategoriesToUser(user1);
		}).isInstanceOf(Exception.class);
	}

	@Test
	void exceptionTestingTwo() {
		assertThatThrownBy(() -> {
			userServices.addCategoryToUser(6,3);
		}).isInstanceOf(Exception.class);
	}

	@Test
	void exceptionTestingThree() {
		assertThatThrownBy(() -> {
			itemsServices.editItem(45,item1);
		}).isInstanceOf(Exception.class);
	}

	@Test
	void exceptionTestingFour() {
		assertThatThrownBy(() -> {
			itemsServices.deleteItem(6,7);
		}).isInstanceOf(Exception.class);
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 3, 9, 10, 27,34})
	void itemsAvailable(int id) {
		assertTrue(itemsRepoI.findById(id).isPresent());
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4})
	void authGroupAvailable(int id) {
		assertTrue(authGroupRepoI.findById(id).isPresent());
	}
}

