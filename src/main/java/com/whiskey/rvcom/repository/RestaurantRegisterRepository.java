package com.whiskey.rvcom.repository;

import com.whiskey.rvcom.entity.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRegisterRepository extends JpaRepository<Restaurant, Long> {
}
