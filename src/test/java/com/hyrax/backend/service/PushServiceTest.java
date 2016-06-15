package com.hyrax.backend.service;

import java.util.UUID;

import com.hyrax.backend.TestBase;
import com.hyrax.backend.config.RestClientConfig.JPushClient;
import com.hyrax.backend.dao.UserDAO;
import com.hyrax.backend.dao.VehicleDAO;
import com.hyrax.backend.dto.NotificationDTO;
import com.hyrax.backend.entity.User;
import com.hyrax.backend.entity.Vehicle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PushServiceTest extends TestBase {

    @Mock
    private UserDAO userDAO;
    @Mock
    private VehicleDAO vehicleDAO;

    private String path;
    private String appKey;
    private String masterSecret;

    private NotificationDTO notificationDTO;
    private User user;
    private Vehicle vehicle;

    private JPushClient jPushClient;
    private PushService pushService;

    @Before
    public void setup() {
        initMocks(this);

        path ="/v3/push";
        appKey = "8d38df46a12861afbed5e0ec";
        masterSecret = "9b44ca7daa5a0ee602193c3a";

        notificationDTO = new NotificationDTO();
        user = new User();
        vehicle = new Vehicle();

        jPushClient = new JPushClient("http://test");
        pushService = new PushService(userDAO, vehicleDAO, jPushClient, path, appKey, masterSecret);
    }

    @Test
    public void return_false_if_push_id_does_not_exists() {
        boolean result = pushService.push(notificationDTO);
        assertFalse("return false if push id does not exists", result);
    }

    @Test
    public void push_method_should_return_true_if_push_success() {
        user.setPushId("123abc");
        notificationDTO.setMessage("<font color=\"#ff0000\">message</font>");
        when(userDAO.get(any(String.class))).thenReturn(user);
        when(vehicleDAO.get(any(UUID.class))).thenReturn(vehicle);

        boolean result = pushService.push(notificationDTO);

        assertTrue("return true if push success", result);
    }

    @Test
    public void return_true_if_update_push_id_success() {
        String userName = "userName";
        String pushId = "pushId";
        when(userDAO.get(userName)).thenReturn(user);

        boolean result = pushService.setPushId(userName, pushId);

        verify(userDAO).update(user);
        assertTrue("return true if update push id success", result);
    }

    @Test
    public void return_true_if_clear_push_id_successlly() {
        String userName = "userName";
        when(userDAO.get(userName)).thenReturn(user);

        boolean result = pushService.clearPushId(userName);

        verify(userDAO).update(user);
        assertTrue("return true if clear push id successfully", result);
    }

}
