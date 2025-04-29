package com.tauhoa.train.services;

import com.tauhoa.train.models.TrainSchedule;
import java.util.List;

public interface ITrainScheduleService {
    List<TrainSchedule> getAllSchedules();  // Lấy tất cả lịch tàu
    TrainSchedule saveSchedule(TrainSchedule schedule);  // Lưu lịch tàu
    TrainSchedule updateSchedule(int id, TrainSchedule schedule);  // Cập nhật lịch tàu
    void deleteSchedule(int id);  // Xóa lịch tàu
}
