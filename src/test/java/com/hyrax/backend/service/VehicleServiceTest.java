package com.hyrax.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hyrax.backend.TestBase;
import com.hyrax.backend.credential.UserContext;
import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.VehicleDAO;
import com.hyrax.backend.dto.VehicleDTO;
import com.hyrax.backend.entity.NotificationType;
import com.hyrax.backend.entity.Vehicle;
import com.hyrax.backend.entity.VehicleStatus;
import com.hyrax.backend.exception.HyraxException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class VehicleServiceTest extends TestBase {

    @Mock
    private VehicleDAO vehicleDAO;
    @Mock
    private VehicleStatusService vehicleStatusService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private MarkService markService;

    private VehicleDTO vehicleDTO;
    private Vehicle vehicle;
    private List<Vehicle> vehicleList;
    private String userName;

    private VehicleService vehicleService;

    @Before
    public void setup() {
        initMocks(this);

        vehicleDTO = new VehicleDTO();
        vehicle = new Vehicle();
        vehicleList = new ArrayList<>();
        userName = "userName";
        vehicleService = new VehicleService(vehicleDAO, vehicleStatusService, notificationService, markService);
    }

    @Test
    public void throw_exception_then_create_vehicle_with_illegal_data() {
        vehicleDTO.setBrand("宝马");
        vehicleDTO.setModel("小型汽车");
        vehicleDTO.setNumber("苏E888888");
        vehicleDTO.setEngine("ADC");
        vehicleDTO.setDoorNum(4);
        vehicleDTO.setSeatNum(0);

        HyraxException hyraxException = null;
        try {
            vehicleService.createVehicle(vehicleDTO);
        } catch (HyraxException e) {
            hyraxException = e;
        }

        assertNotNull(hyraxException);
    }

    @Test
    public void create_vehicle_test() {
        vehicleDTO.setBrand("宝马");
        vehicleDTO.setModel("小型汽车");
        vehicleDTO.setNumber("苏E888888");
        vehicleDTO.setEngine("ADC");
        vehicleDTO.setDoorNum(4);
        vehicleDTO.setSeatNum(4);
        vehicleDTO.setGasCapacity(120);
        vehicleDTO.setMaintainCycle(15000);
        vehicleDTO.setCarFrame("12345678");

        UserContextHolder.setContext(new UserContext(userName));
        vehicleService.createVehicle(vehicleDTO);

        verify(vehicleDAO).save(any(Vehicle.class));
        verify(vehicleStatusService).create(any(VehicleStatus.class));
    }

    @Test
    public void get_vehicles_test() {
        UserContextHolder.setContext(new UserContext(userName));
        vehicleService.getVehicles();
        verify(vehicleDAO).getByUserName(userName);
    }

    @Test
    public void throw_exception_if_vehicle_can_not_find_by_id() {
        HyraxException hyraxException = null;
        try {
            vehicleService.getVehicle(UUID.randomUUID());
        } catch (HyraxException e) {
            hyraxException = e;
        }
        assertNotNull(hyraxException);
    }

    @Test
    public void test_get_vehicle_by_id() {
        UUID vehicleId = UUID.randomUUID();
        vehicle.setId(vehicleId);
        vehicleList.add(vehicle);
        when(vehicleDAO.getByUserName(userName)).thenReturn(vehicleList);

        UserContextHolder.setContext(new UserContext(userName));
        Vehicle result = vehicleService.getVehicle(vehicleId);

        assertEquals(vehicle, result);
    }

    @Test
    public void throw_exception_when_delete_vehicle_by_empty_id() {
        HyraxException hyraxException = null;
        try {
            vehicleService.deleteVehicle(null);
        } catch (HyraxException e) {
            hyraxException = e;
        }
        assertNotNull(hyraxException);
    }

    @Test
    public void delete_vehicle_test() {
        UUID vehicleId = UUID.randomUUID();
        vehicle.setId(vehicleId);
        vehicleList.add(vehicle);
        when(vehicleDAO.getByUserName(userName)).thenReturn(vehicleList);

        UserContextHolder.setContext(new UserContext(userName));
        vehicleService.deleteVehicle(vehicleId);

        verify(notificationService, times(NotificationType.values().length)).delete(any(UUID.class), any
                (NotificationType.class));
        verify(vehicleStatusService).delete(vehicleId);
        verify(vehicleDAO).delete(vehicleId);
    }

}
