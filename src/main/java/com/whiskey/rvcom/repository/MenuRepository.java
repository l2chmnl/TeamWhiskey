package com.whiskey.rvcom.repository;

import com.whiskey.rvcom.entity.restaurant.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
