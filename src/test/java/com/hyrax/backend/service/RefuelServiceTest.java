package com.hyrax.backend.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hyrax.backend.TestBase;
import com.hyrax.backend.credential.UserContext;
import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.RefuelDAO;
import com.hyrax.backend.dto.RefuelDTO;
import com.hyrax.backend.entity.Refuel;
import com.hyrax.backend.entity.state.RefuelState;
import com.hyrax.backend.exception.ErrorType;
import com.hyrax.backend.exception.HyraxException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RefuelServiceTest extends TestBase {

    @Mock
    private RefuelDAO refuelDAO;

    private Refuel refuel;
    private RefuelDTO refuelDTO;
    private RefuelService refuelService;

    @Before
    public void setup() {
        initMocks(this);

        refuel = new Refuel();
        refuelDTO = new RefuelDTO();
        refuelService = new RefuelService(refuelDAO);

        refuelDTO.setAppointTime(new Timestamp(System.currentTimeMillis() + 10000));
        refuelDTO.setStationId("stationId");
        refuelDTO.setStationName("stationName");
        refuelDTO.setFuelType("fuelType");
    }

    @Test
    public void throw_correct_Hyrax_Exception_if_refuel_appoint_parameter_is_illegal() {
        refuelDTO.setFuelType("");

        HyraxException hyraxException = null;
        try {
            refuelService.appointRefuel(refuelDTO);
        } catch (HyraxException e) {
            hyraxException = e;
        }

        assertEquals("http response status should be correct", ErrorType.REFUEL_APPOINT_ILLEGAL.getStatus(),
                     hyraxException.getStatus());
        assertEquals("system error code should be correct", ErrorType.REFUEL_APPOINT_ILLEGAL.getErrorCode(),
                     hyraxException.getErrorCode());
        assertEquals("client message should be correct", ErrorType.REFUEL_APPOINT_ILLEGAL.getClinetMessage(),
                     hyraxException.getClientMessage());
    }

    @Test
    public void test_appoint_refuel() {
        refuelDTO.setFuelType("#97");
        refuelDTO.setAmount(10);
        refuelDTO.setAmountType(0);
        refuelDTO.setPrice(10);
        UUID result = refuelService.appointRefuel(refuelDTO);

        verify(refuelDAO).save(any(Refuel.class));
        assertNotNull("should return refuel id if appoint success", result);
    }

    @Test
    public void test_get_users_refule_list() {
        List<Refuel> refuelList = new ArrayList<>();
        String userName = "userName";
        UserContextHolder.setContext(new UserContext(userName));
        when(refuelDAO.getByUserName(userName)).thenReturn(refuelList);

        List<RefuelDTO> result = refuelService.getRefuels();

        assertEquals(refuelList, result);
    }

    @Test
    public void test_update_refuel_appointment() {
        UUID refuelId = UUID.randomUUID();
        when(refuelDAO.get(refuelId)).thenReturn(refuel);

        refuelService.updateRefuel(refuelId, RefuelState.ACCEPTED);

        verify(refuelDAO).update(refuel);
    }

}
