package com.hyrax.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hyrax.backend.TestBase;
import com.hyrax.backend.credential.UserContext;
import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.VehicleDAO;
import com.hyrax.backend.dao.VehicleStatusDAO;
import com.hyrax.backend.dto.VehicleStatusDTO;
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

public class VehicleStatusServiceTest extends TestBase {

    @Mock
    private VehicleDAO vehicleDAO;
    @Mock
    private VehicleStatusDAO vehicleStatusDAO;
    @Mock
    private NotificationService notificationService;

    private String userName;
    private Vehicle vehicle;
    private VehicleStatus vehicleStatus;
    private List<VehicleStatus> vehicleStatusList;
    private VehicleStatusService vehicleStatusService;

    @Before
    public void setup() {
        initMocks(this);

        userName = "userName";
        vehicle = new Vehicle();
        vehicleStatus = new VehicleStatus();
        vehicleStatusList = new ArrayList<>();
        vehicleStatusService = new VehicleStatusService(vehicleDAO, vehicleStatusDAO, notificationService);
    }

    @Test
    public void test_create_vehicle_status() {
        vehicleStatusService.create(vehicleStatus);
        verify(vehicleStatusDAO).save(vehicleStatus);
    }

    @Test
    public void test_update_vehicle_status() {
        vehicleStatusService.update(vehicleStatus);
        verify(vehicleStatusDAO).update(vehicleStatus);
    }

    @Test
    public void throw_exception_while_delete_vehicle_with_empty_id() {
        HyraxException hyraxException = null;
        try {
            vehicleStatusService.delete(null);
        } catch (HyraxException e) {
            hyraxException = e;
        }
        assertNotNull(hyraxException);
    }

    @Test
    public void test_delete_vehilce_status_by_id() {
        UUID vehicleId = UUID.randomUUID();
        vehicleStatus.setId(vehicleId);
        vehicleStatusList.add(vehicleStatus);
        when(vehicleStatusDAO.getByUserName(userName)).thenReturn(vehicleStatusList);

        UserContextHolder.setContext(new UserContext(userName));
        vehicleStatusService.delete(vehicleId);

        verify(vehicleStatusDAO).delete(vehicleId);
    }

    @Test
    public void throw_exception_while_get_vehicle_with_empty_id() {
        HyraxException hyraxException = null;
        try {
            vehicleStatusService.getVehicleStatus(null);
        } catch (HyraxException e) {
            hyraxException = e;
        }
        assertNotNull(hyraxException);
    }

    @Test
    public void throw_exception_if_user_does_not_have_permission() {
        UUID vehicleId = UUID.randomUUID();
        vehicleStatus.setUserName("anotherUser");
        when(vehicleStatusDAO.get(vehicleId)).thenReturn(vehicleStatus);
        UserContextHolder.setContext(new UserContext(userName));

        HyraxException hyraxException = null;
        try {
            vehicleStatusService.getVehicleStatus(vehicleId);
        } catch (HyraxException e) {
            hyraxException = e;
        }

        assertNotNull(hyraxException);
    }

    @Test
    public void test_get_vehicle_status_by_id() {
        UUID vehicleId = UUID.randomUUID();
        vehicleStatus.setUserName(userName);
        when(vehicleStatusDAO.get(vehicleId)).thenReturn(vehicleStatus);
        when(vehicleDAO.get(vehicleId)).thenReturn(vehicle);

        UserContextHolder.setContext(new UserContext(userName));
        VehicleStatusDTO result = vehicleStatusService.getVehicleStatus(vehicleId);

        verify(vehicleDAO).get(vehicleId);
        assertNotNull(result);
    }

    @Test
    public void test_get_vehicle_status_list() {
        for (int i = 3; i > 0; i--) {
            vehicleStatusList.add(new VehicleStatus());
        }
        when(vehicleStatusDAO.getByUserName(userName)).thenReturn(vehicleStatusList);
        when(vehicleDAO.get(any(UUID.class))).thenReturn(vehicle);

        UserContextHolder.setContext(new UserContext(userName));
        List<VehicleStatusDTO> result = vehicleStatusService.getVehiclesStatus();

        verify(vehicleDAO, times(3)).get(any(UUID.class));
        assertEquals(3, result.size());
    }

    @Test
    public void test_vehicle_status_check() {

    }
}
