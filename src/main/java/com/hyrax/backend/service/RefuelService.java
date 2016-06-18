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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RefuelService {

    private static Logger log = LoggerFactory.getLogger(RefuelService.class);
    private static double precision = 0.000001;

    private final RefuelDAO refuelDAO;

    @Autowired
    public RefuelService(RefuelDAO refuelDAO) {
        this.refuelDAO = refuelDAO;
    }

    /**
     * 预约加油
     * @param refuelDTO 预约的信息
     * @return 预约的ID
     */
    public UUID appointRefuel(RefuelDTO refuelDTO) {
        log.info("appoint refuel {}", refuelDTO);
        verifyAppoint(refuelDTO);

        UUID id = UUID.randomUUID();
        String userName = UserContextHolder.getUserName();
        Refuel refuel = Refuel.newInstance()
                .withId(id)
                .withUserName(userName)
                .withVehicleNumber(refuelDTO.getVehicleNumber())
                .withFuelType(refuelDTO.getFuelType())
                .withPrice(refuelDTO.getPrice())
                .withAmount(refuelDTO.getAmount())
                .withAmountType(refuelDTO.getAmountType())
                .withStationId(refuelDTO.getStationId())
                .withStationName(refuelDTO.getStationName())
                .withState(RefuelState.REQUESTED)
                .withAppointTime(refuelDTO.getAppointTime())
                .withCreateTime(new Timestamp(System.currentTimeMillis()));
        refuelDAO.save(refuel);
        return id;
    }

    /**
     * 获取当前用户所有的加油预约
     * @return
     */
    public List<RefuelDTO> getRefuels() {
        String userName = UserContextHolder.getUserName();
        log.info("get refuels for user:{}", userName);

        List<RefuelDTO> refuelDTOList = new ArrayList<>();
        List<Refuel> refuelList = refuelDAO.getByUserName(userName);
        for (Refuel refuel : refuelList) {
            refuelDTOList.add(RefuelDTO.fromRefuel(refuel));
        }
        return refuelDTOList;
    }

    /**
     * 更新加油预约的数据
     * @param id 被更新的数据ID
     * @param refuelState 接受状态
     */
    public void updateRefuel(UUID id, RefuelState refuelState) {
        log.info("update refuel {} set refuelState:{}", id, refuelState);
        Refuel refuel = refuelDAO.get(id);
        refuel.withState(refuelState).withModifyTime(new Timestamp(System.currentTimeMillis()));
        refuelDAO.update(refuel);
    }

    /**
     * 验证加油预约的参数是否合法
     * @param refuelDTO
     */
    private void verifyAppoint(RefuelDTO refuelDTO) {
        if (refuelDTO.getFuelType() == null || refuelDTO.getFuelType().isEmpty() ||
                refuelDTO.getPrice() < precision || refuelDTO.getAmount() < precision ||
                refuelDTO.getAmountType() == null || refuelDTO.getStationId() == null ||
                refuelDTO.getStationId().isEmpty() || refuelDTO.getStationName() == null ||
                refuelDTO.getStationName().isEmpty() || refuelDTO.getAppointTime() == null ||
                refuelDTO.getAppointTime().before(new Timestamp(System.currentTimeMillis()))) {
            throw new HyraxException(ErrorType.REFUEL_APPOINT_ILLEGAL);
        }
    }

}
