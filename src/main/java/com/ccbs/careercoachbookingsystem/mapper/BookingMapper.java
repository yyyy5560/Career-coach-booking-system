package com.ccbs.careercoachbookingsystem.mapper;

import com.ccbs.careercoachbookingsystem.entity.BookingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookingMapper {
    // 根据 userId 查询预约列表
    List<BookingEntity> findByUserId(@Param("userId") String userId);
    // 根据主键查询预约
    BookingEntity findById(@Param("id") Long id);
    // 根据 Cal.com booking uid 查询
    BookingEntity findByBookingUid(@Param("bookingUid") String bookingUid);
    // 新增预约
    int insert(BookingEntity booking);
    // 更新预约
    int update(BookingEntity booking);
}
