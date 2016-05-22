package com.hyrax.backend.service;

import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.RefuelDAO;
import com.hyrax.backend.dto.RefuelDTO;
import com.hyrax.backend.entity.Refuel;
import com.hyrax.backend.entity.state.RefuelState;
import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class RefuelService {

    private static Logger log = LoggerFactory.getLogger(RefuelService.class);

    private final RefuelDAO refuelDAO;

    @Autowired
    public RefuelService(RefuelDAO refuelDAO) {
        this.refuelDAO = refuelDAO;
    }

    public UUID appointRefuel(RefuelDTO refuelDTO) {
        verifyAppoint(refuelDTO);

        UUID id = UUID.randomUUID();
        String userName = UserContextHolder.getUserName();
        Refuel refuel = Refuel.newInstance()
                .withId(id)
                .withUserName(userName)
                .withOwnerName(refuelDTO.getOwnerName())
                .withFromTime(refuelDTO.getFromTime())
                .withToTime(refuelDTO.getToTime())
                .withStationId(refuelDTO.getStationId())
                .withStationName(refuelDTO.getStationName())
                .withFuelType(refuelDTO.getFuelType())
                .withState(RefuelState.REQUESTED)
                .withCreateTime(new Timestamp(System.currentTimeMillis()));
        refuelDAO.save(refuel);
        return id;
    }

    public List<Refuel> getRefuels() {
        String userName = UserContextHolder.getUserName();
        return refuelDAO.getByUserName(userName);
    }

    public void updateRefuel(UUID id, double litre, double cost, RefuelState refuelState) {
        log.info("update refuel {} with liter:{}, cost:{}, refuelState:{}", id, litre, cost, refuelState);
        Refuel refuel = refuelDAO.get(id);
        refuel.withLitre(litre)
                .withCost(cost)
                .withState(refuelState)
                .withModifyTime(new Timestamp(System.currentTimeMillis()));
        refuelDAO.update(refuel);
    }

    private void verifyAppoint(RefuelDTO refuelDTO) {
        if (refuelDTO.getOwnerName() == null || refuelDTO.getOwnerName().isEmpty() ||
                refuelDTO.getFromTime() == null || refuelDTO.getFromTime().before(new Timestamp(System.currentTimeMillis())) ||
                refuelDTO.getToTime() == null || refuelDTO.getToTime().before(refuelDTO.getFromTime()) ||
                refuelDTO.getStationId() == null || refuelDTO.getStationId().isEmpty() ||
                refuelDTO.getStationName() == null || refuelDTO.getStationName().isEmpty() ||
                refuelDTO.getFuelType() == null || refuelDTO.getFuelType().isEmpty()) {
            throw new HyraxException(ErrorType.REFUEL_APPOINT_ILLEGAL);
        }
    }

}
