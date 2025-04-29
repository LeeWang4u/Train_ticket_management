package com.tauhoa.train.services;

import com.tauhoa.train.models.TrainSchedule;
import com.tauhoa.train.repositories.TrainScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainScheduleService implements ITrainScheduleService {

    private final TrainScheduleRepository trainScheduleRepository;

    @Autowired
    public TrainScheduleService(TrainScheduleRepository trainScheduleRepository) {
        this.trainScheduleRepository = trainScheduleRepository;
    }

    // Lấy tất cả lịch tàu
    @Override
    public List<TrainSchedule> getAllSchedules() {
        return trainScheduleRepository.findAll();
    }

    // Các phương thức khác cho CRUD (nếu cần)
    @Override
    public TrainSchedule saveSchedule(TrainSchedule schedule) {
        return trainScheduleRepository.save(schedule);
    }

    @Override
    public TrainSchedule updateSchedule(int id, TrainSchedule schedule) {
        if (!trainScheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with id: " + id);
        }
        schedule.setTrainScheduleId(id);
        return trainScheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(int id) {
        if (!trainScheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with id: " + id);
        }
        trainScheduleRepository.deleteById(id);
    }
}
