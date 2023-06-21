package zti.projekt_zti.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zti.projekt_zti.dto.*;
import zti.projekt_zti.dto.GetDayRequestDto;
import zti.projekt_zti.dto.GetDayResponseDto;
import zti.projekt_zti.entity.Day;
import zti.projekt_zti.entity.Drug;
import zti.projekt_zti.entity.Meeting;
import zti.projekt_zti.entity.Supplement;
import zti.projekt_zti.repository.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * Serwis obsługujący operacje związane z danymi dnia.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class DayService {

    @Autowired
    private  DayRepository dayRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private SupplementRepository supplementRepository;
    @Autowired
    private DrugRepository drugRepository;
    @Autowired
    private Supplement2Repository supplement2Repository;
    @Autowired
    private Meeting2Repository meeting2Repository;
    @Autowired
    private Drug2Repository drug2Repository;
    @Autowired
    private Day2Repository day2Repository;

    /**
     * Konwertuje obiekt typu `Date` na `Timestamp`.
     *
     * @param day obiekt typu `Date` reprezentujący dzień
     * @return obiekt typu `Timestamp` reprezentujący dzień z zerową godziną
     */
    public Timestamp getConvertedDay(Date day) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(day.toInstant(), ZoneId.systemDefault());
        LocalDate localDate = localDateTime.toLocalDate();
        LocalDateTime localDateTimeWithMidnight = localDate.atStartOfDay();
        return Timestamp.valueOf(localDateTimeWithMidnight);
    }

    /**
     * Pobiera informacje o dniu na podstawie żądania.
     *
     * @param request obiekt typu `GetDayRequestDto` zawierający informacje o żądaniu
     * @return obiekt typu `GetDayResponseDto` zawierający informacje o dniu
     */
    public GetDayResponseDto getDay(GetDayRequestDto request) {
        Day day = dayRepository.findByDateAndUserId(getConvertedDay(request.getDate()), request.getUserId());

        if (day != null) {
            Meeting meeting = meetingRepository.findByDayId(day.getId());
            List<Drug> drugs = drugRepository.findByDayId(day.getId());
            List<Supplement> supplements = supplementRepository.findByDayId(day.getId());
            return new GetDayResponseDto(day, supplements, meeting, drugs);
        }

        return new GetDayResponseDto(new SimpleDateFormat("yyyy-MM-dd").format(request.getDate()), request.getUserId());
    }

    /**
     * Aktualizuje informacje o dniu na podstawie żądania.
     *
     * @param request obiekt typu `UpdateDayDto` zawierający informacje o dniu do zaktualizowania
     */
    public void updateDay(UpdateDayDto request) {
        Day day = dayRepository.findByDateAndUserId(getConvertedDay(request.getDate()), request.getUserId());

        if (day == null) {
            day = new Day();
            day.setUserId(request.getUserId());
            day.setWorkH(request.getWorkH());
            day.setStudiesH(request.getStudiesH());
            day.setLearningH(request.getLearningH());
            day.setSleepH(request.getSleepH());
            day.setSportH(request.getSportH());
            day.setRestH(request.getRestH());
            day.setMealsNumber(request.getMealsNumber());
            day.setWater(request.getWater());
            day.setPhysicalWellBeeing(request.getPhysicalWellBeeing());
            day.setMentalWellBeeing(request.getMentalWellBeeing());
            day.setDate(getConvertedDay(request.getDate()));
            day.setDescription(request.getDescription());
            dayRepository.save(day);

            for (SupplementDto supplement : request.getSupplements()) {
                Supplement sup = new Supplement();
                sup.setSupplementAmount(supplement.getSupplementAmount());
                sup.setSupplementName(supplement.getSupplementName());
                sup.setDayId(day.getId());
                supplementRepository.save(sup);
            }
            for (DrugDto drug : request.getDrugs()) {
                Drug drug1 = new Drug();
                drug1.setDrugAmount(drug.getDrugAmount());
                drug1.setDrugName(drug.getDrugName());
                drug1.setDayId(day.getId());
                drugRepository.save(drug1);
            }

            if (request.getMeeting() != null) {
                Meeting meeting = new Meeting();
                meeting.setMeetingH(request.getMeeting().getMeetingH());
                meeting.setPeopleCount(request.getMeeting().getPeopleCount());
                meeting.setDayId(day.getId());
                meetingRepository.save(meeting);
            }
        } else {
            day.setUserId(request.getUserId());
            day.setWorkH(request.getWorkH());
            day.setStudiesH(request.getStudiesH());
            day.setLearningH(request.getLearningH());
            day.setSleepH(request.getSleepH());
            day.setSportH(request.getSportH());
            day.setRestH(request.getRestH());
            day.setMealsNumber(request.getMealsNumber());
            day.setWater(request.getWater());
            day.setPhysicalWellBeeing(request.getPhysicalWellBeeing());
            day.setMentalWellBeeing(request.getMentalWellBeeing());
            day.setDescription(request.getDescription());
            dayRepository.save(day);

            meeting2Repository.deleteByDayId(day.getId());
            if (request.getMeeting() != null) {
                Meeting meeting = new Meeting();
                meeting.setMeetingH(request.getMeeting().getMeetingH());
                meeting.setPeopleCount(request.getMeeting().getPeopleCount());
                meeting.setDayId(day.getId());
                meetingRepository.save(meeting);
            }

            supplement2Repository.deleteByDayId(day.getId());
            if (request.getSupplements() != null) {
                for (SupplementDto supplement : request.getSupplements()) {
                    Supplement sup = new Supplement();
                    sup.setSupplementAmount(supplement.getSupplementAmount());
                    sup.setSupplementName(supplement.getSupplementName());
                    sup.setDayId(day.getId());
                    supplementRepository.save(sup);
                }
            }

            drug2Repository.deleteByDayId(day.getId());
            if (request.getDrugs() != null) {
                for (DrugDto drug : request.getDrugs()) {
                    Drug drug1 = new Drug();
                    drug1.setDrugAmount(drug.getDrugAmount());
                    drug1.setDrugName(drug.getDrugName());
                    drug1.setDayId(day.getId());
                    drugRepository.save(drug1);
                }
            }
        }
    }

    /**
     * Pobiera informacje o wszystkich dniach użytkownika na podstawie jego identyfikatora.
     *
     * @param userId identyfikator użytkownika
     * @return obiekt typu `DaysDto` zawierający informacje o dniach użytkownika
     */
    public DaysDto getAllDays(Integer userId) {
        List<Day> days = dayRepository.findAllByUserId(userId);
        if (days != null) {
            List<DayInfoDto> daysToResponse = new ArrayList<DayInfoDto>();
            for (Day day : days) {
                daysToResponse.add(new DayInfoDto(day.getId(),
                        new SimpleDateFormat("yyyy-MM-dd").format(day.getDate()), day.getPhysicalWellBeeing(),
                        day.getMentalWellBeeing()));
            }
            Collections.sort(daysToResponse, new Comparator<DayInfoDto>() {
                @Override
                public int compare(DayInfoDto day1, DayInfoDto day2) {
                    try {
                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(day1.getDate());
                        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(day2.getDate());
                        return date2.compareTo(date1); // Sortowanie malejąco
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            return new DaysDto(daysToResponse);
        }

        return new DaysDto(new ArrayList<>());
    }

    /**
     * Usuwa informacje o dniu na podstawie żądania.
     *
     * @param request obiekt typu `GetDayRequestDto` zawierający informacje o żądaniu usunięcia dnia
     */
    public void deleteDay(GetDayRequestDto request) {
        Day day = dayRepository.findByDateAndUserId(getConvertedDay(request.getDate()), request.getUserId());
        if (day != null) {
            meeting2Repository.deleteByDayId(day.getId());
            supplement2Repository.deleteByDayId(day.getId());
            drug2Repository.deleteByDayId(day.getId());
            day2Repository.deleteByDateAndUserId(getConvertedDay(request.getDate()), request.getUserId());
        }
    }

}
