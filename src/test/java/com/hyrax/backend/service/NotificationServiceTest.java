package com.hyrax.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hyrax.backend.TestBase;
import com.hyrax.backend.credential.UserContext;
import com.hyrax.backend.credential.UserContextHolder;
import com.hyrax.backend.dao.NotificationDAO;
import com.hyrax.backend.dto.NotificationDTO;
import com.hyrax.backend.entity.Notification;
import com.hyrax.backend.entity.NotificationType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class NotificationServiceTest extends TestBase {

    @Mock
    NotificationDAO notificationDAO;
    @Mock
    PushService pushService;

    private Notification notification;
    private List<Notification> notificationList;

    private NotificationService notificationService;

    @Before
    public void setup() {
        initMocks(this);

        notification = new Notification();
        notificationList = new ArrayList<>();

        notificationService = new NotificationService(notificationDAO, pushService);
    }

    @Test
    public void test_register_push_id() {
        String pushId = "pushId";
        String userName = "userName";
        notificationList.add(new Notification());
        notificationList.add(new Notification());
        notificationList.add(new Notification());

        UserContextHolder.setContext(new UserContext(userName));
        when(notificationDAO.getByUserName(userName)).thenReturn(notificationList);
        when(pushService.push(any(NotificationDTO.class))).thenReturn(true);

        notificationService.registerPushId(pushId);

        verify(pushService).setPushId(userName, pushId);
        verify(notificationDAO, times(3)).update(any(Notification.class));
    }

    @Test
    public void test_three_kinds_of_create_notification() {
        notificationService.create(null, null, null, null);
        notificationService.create(null, null, null, 0, null);
        notificationService.create(null, null, null, null, null);

        verify(notificationDAO, times(3)).save(any(Notification.class));
    }

    @Test
    public void test_three_kinds_is_notification_exists() {
        UUID vehicleId = UUID.randomUUID();

        notificationService.isExist(vehicleId, NotificationType.MILEAGE_ACHIEVE);
        notificationService.isExist(vehicleId, NotificationType.ENGINE_ABNORMAL, 0);
        notificationService.isExist(vehicleId, NotificationType.HEADLIGHT_ABNORMAL, null);

        verify(notificationDAO, times(3)).getByVehicleId(any(UUID.class));
    }

    @Test
    public void should_return_true_is_notification_exist() {
        UUID vehicleId = UUID.randomUUID();
        NotificationType notificationType = NotificationType.FUEL_UNDER;
        String description = "some description";

        notification.setType(notificationType);
        notification.setDescription(description);
        notificationList.add(notification);
        when(notificationDAO.getByVehicleId(vehicleId)).thenReturn(notificationList);

        boolean result = notificationService.isExist(vehicleId, notificationType, description);

        assertTrue("should return true is notification exist", result);
    }

    @Test
    public void get_notification_list() {
        UUID vehicleId = UUID.randomUUID();
        NotificationType notificationType = NotificationType.TRANSMISSION_ABNORMAL;

        notification.setType(notificationType);
        notificationList.add(notification);
        notificationList.add(notification);
        notificationList.add(notification);
        when(notificationDAO.getByVehicleId(vehicleId)).thenReturn(notificationList);

        List<Notification> result = notificationService.get(vehicleId, notificationType);

        assertEquals("should get all notifications if match conditions", 3 ,result.size());
    }

    @Test
    public void test_three_kinds_of_delete_notifications() {
        UUID vehicleId = UUID.randomUUID();
        NotificationType notificationType = NotificationType.TRANSMISSION_ABNORMAL;

        notificationService.delete(vehicleId, notificationType);
        notificationService.delete(vehicleId, notificationType, 0);
        notificationService.delete(vehicleId, notificationType, "");

        verify(notificationDAO, times(3)).getByVehicleId(any(UUID.class));
    }

    @Test
    public void matched_notification_should_be_delete() {
        UUID vehicleId = UUID.randomUUID();
        NotificationType notificationType = NotificationType.FUEL_UNDER;
        String description = "some description";

        notification.setType(notificationType);
        notification.setDescription(description);
        notificationList.add(notification);
        notificationList.add(notification);
        notificationList.add(notification);
        when(notificationDAO.getByVehicleId(vehicleId)).thenReturn(notificationList);

        notificationService.delete(vehicleId, notificationType, description);

        verify(notificationDAO, times(3)).delete(any(UUID.class));
    }
}
