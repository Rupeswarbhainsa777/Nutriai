package com.code.Nutriai.service;

import com.code.Nutriai.model.FitnessData;
import com.code.Nutriai.model.User;
import com.code.Nutriai.repository.FitnessRepository;
import com.code.Nutriai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FitnessService {


    private final FitnessRepository fitnessRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> syncFitnessData(FitnessData fitnessData) {
        try {
            User user = userRepository.findById(fitnessData.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            fitnessData.setUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(fitnessRepository.save(fitnessData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    public ResponseEntity<?> getFitnessDataByUser(Long userId) {
        List<FitnessData> data = fitnessRepository.findByUserId(userId);
        return ResponseEntity.ok(data);
    }
    public ResponseEntity<?> getFitnessDataByDate(Long userId, LocalDate date) {
        return ResponseEntity.ok(
                fitnessRepository.findByUserIdAndDate(userId, date));
    }
    public ResponseEntity<?> getFitnessSummary(Long userId, String range) {
        LocalDate from = range.equals("week")
                ? LocalDate.now().minusDays(7)
                : LocalDate.now().minusDays(30);

        List<FitnessData> data = fitnessRepository
                .findByUserIdAndDateBetween(userId, from, LocalDate.now());

        int totalSteps    = data.stream().mapToInt(FitnessData::getSteps).sum();
        int totalCalories = data.stream().mapToInt(FitnessData::getCaloriesBurned).sum();
        int totalMinutes  = data.stream().mapToInt(FitnessData::getActiveMinutes).sum();

        return ResponseEntity.ok(java.util.Map.of(
                "range",          range,
                "totalSteps",     totalSteps,
                "totalCalories",  totalCalories,
                "totalMinutes",   totalMinutes,
                "avgDailySteps",  data.isEmpty() ? 0 : totalSteps / data.size()
        ));
    }

    public ResponseEntity<?> deleteFitnessData(Long id) {
        try {
            fitnessRepository.deleteById(id);
            return ResponseEntity.ok("Fitness record deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }



}
