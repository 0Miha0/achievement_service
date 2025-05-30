package faang.school.achievement.listener.impl.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.project.ProjectEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.handler.impl.project.BusinessmanAchievementHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectEventListenerTest {

    @Mock
    private ObjectMapper objectMapper;

    BusinessmanAchievementHandler businessmanAchievementHandler;
    ProjectEventListener projectEventListener;

    @BeforeEach
    void setUp() {
        businessmanAchievementHandler = mock(BusinessmanAchievementHandler.class);
        List<EventHandler<ProjectEvent>> projectAchievementHandlers = new ArrayList<>();
        projectAchievementHandlers.add(businessmanAchievementHandler);

        projectEventListener = new ProjectEventListener(objectMapper, projectAchievementHandlers);
    }

    @Test
    public void onMessageTest() throws IOException {
        ProjectEvent projectEvent = ProjectEvent.builder().build();
        Message message = mock(Message.class);
        byte[] pattern = "test-pattern".getBytes();

        when(objectMapper.readValue(message.getBody(), ProjectEvent.class)).thenReturn(projectEvent);
        when(businessmanAchievementHandler.supportsEvent(ProjectEvent.class)).thenReturn(true);

        projectEventListener.onMessage(message, pattern);

        verify(businessmanAchievementHandler, times(1)).handleEvent(projectEvent);
    }

    @Test
    public void onMessageThrowsExceptionTest() throws IOException {
        Message message = mock(Message.class);
        byte[] pattern = "test-pattern".getBytes();

        when(objectMapper.readValue(message.getBody(), ProjectEvent.class)).thenThrow(IOException.class);

        assertThrows(RuntimeException.class,
                () -> projectEventListener.onMessage(message, pattern));
    }
}