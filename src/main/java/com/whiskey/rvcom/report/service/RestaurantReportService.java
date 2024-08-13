package com.whiskey.rvcom.report.service;


import com.whiskey.rvcom.entity.report.RestaurantReport;
import com.whiskey.rvcom.entity.restaurant.Restaurant;
import com.whiskey.rvcom.report.model.dto.RestaurantDTO;
import com.whiskey.rvcom.report.model.dto.RestaurantReportDTO;
import com.whiskey.rvcom.repository.RestaurantReportRepository;
import com.whiskey.rvcom.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantReportService {

    private final RestaurantReportRepository restaurantReportRepository;

    private final RestaurantRepository restaurantRepository;

    private final ModelMapper modelMapper;


    // 식당 신고 전체 조회
    public List<RestaurantReportDTO> getAllRestaurantReports() {
        List<RestaurantReport> reports = restaurantReportRepository.findAll();

        // 리스트로 반환해야 하므로 빈 리스트 추가
        List<RestaurantReportDTO> restaurantReportDTOList = new ArrayList<>();

        for (RestaurantReport report : reports) {

            // RestaurantReport -> RestaurantReportDTO로 변환
            RestaurantDTO restaurantDTO = modelMapper.map(report.getRestaurant(), RestaurantDTO.class);
            // RestaurantReportDto 안에는 RestaurantDTO가 있음 따라서
            RestaurantReportDTO restaurantReportDTO = modelMapper.map(report, RestaurantReportDTO.class);
            // RestaurantReport -> RestaurantReportDTO로 변환할 때 RestaurantDTO도 변환해줘야함
            restaurantReportDTO.setRestaurantDTO(restaurantDTO);

            restaurantReportDTOList.add(restaurantReportDTO);
        }
        return restaurantReportDTOList;
    }


    // 식당 신고 등록
    @Transactional
    public void saveRestaurantReport(RestaurantReportDTO report) {

        // RestaurantReportDTO -> RestaurantReport로 변환
        RestaurantReport restaurantReport = modelMapper.map(report, RestaurantReport.class);
        // RestaurantReportDTO 안에는 RestaurantDTO가 있음 따라서
        // RestaurantReportDTO -> RestaurantReport로 변환할 때 RestaurantDTO도 변환해줘야함
        RestaurantDTO restaurantDTO = report.getRestaurantDTO();

        // RestaurantDTO에서 ID를 가져옴
        Long restaurantId = restaurantDTO.getId();

        // 데이터베이스에서 해당 ID를 가진 Restaurant를 조회
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with ID: " + restaurantId));

        // 조회한 Restaurant 엔티티를 설정
        restaurantReport.setRestaurant(restaurant);

        restaurant = modelMapper.map(restaurantDTO, Restaurant.class);
        restaurantReport.setRestaurant(restaurant);

        restaurantReportRepository.save(restaurantReport);
    }

}
