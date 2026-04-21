package com.behaviourbridgetracks.config;

import com.behaviourbridgetracks.model.*;
import com.behaviourbridgetracks.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final BehaviourTypeRepository behaviourTypeRepo;
    private final TriggerRepository triggerRepo;
    private final SettingRepository settingRepo;
    private final ResponseRepository responseRepo;

    @Override
    public void run(ApplicationArguments args) {

        if (behaviourTypeRepo.count() == 0) {
            behaviourTypeRepo.saveAll(List.of(
                    BehaviourType.builder()
                            .name("Physical Aggression")
                            .category("CHALLENGING").build(),
                    BehaviourType.builder()
                            .name("Verbal Aggression")
                            .category("CHALLENGING").build(),
                    BehaviourType.builder()
                            .name("Self-Injurious Behaviour")
                            .category("CHALLENGING").build(),
                    BehaviourType.builder()
                            .name("Property Destruction")
                            .category("CHALLENGING").build(),
                    BehaviourType.builder()
                            .name("Non-Compliance")
                            .category("CHALLENGING").build(),
                    BehaviourType.builder()
                            .name("Withdrawal / Avoidance")
                            .category("CHALLENGING").build(),
                    BehaviourType.builder()
                            .name("Functional Communication")
                            .category("POSITIVE").build(),
                    BehaviourType.builder()
                            .name("Emotional Regulation")
                            .category("POSITIVE").build(),
                    BehaviourType.builder()
                            .name("Task Engagement")
                            .category("POSITIVE").build(),
                    BehaviourType.builder()
                            .name("Social Interaction")
                            .category("POSITIVE").build()
            ));
            System.out.println("✅ Behaviour types seeded");
        }

        if (triggerRepo.count() == 0) {
            triggerRepo.saveAll(List.of(
                    Trigger.builder()
                            .name("Sensory Overload")
                            .category("ENVIRONMENTAL").build(),
                    Trigger.builder()
                            .name("Changes in Routine")
                            .category("ENVIRONMENTAL").build(),
                    Trigger.builder()
                            .name("Transitions")
                            .category("ENVIRONMENTAL").build(),
                    Trigger.builder()
                            .name("Task Demands")
                            .category("ENVIRONMENTAL").build(),
                    Trigger.builder()
                            .name("Crowded Spaces")
                            .category("ENVIRONMENTAL").build(),
                    Trigger.builder()
                            .name("Being Told No")
                            .category("SOCIAL").build(),
                    Trigger.builder()
                            .name("Communication Barriers")
                            .category("SOCIAL").build(),
                    Trigger.builder()
                            .name("Lack of Attention")
                            .category("SOCIAL").build(),
                    Trigger.builder()
                            .name("Social Demands")
                            .category("SOCIAL").build(),
                    Trigger.builder()
                            .name("Physical Discomfort")
                            .category("INTERNAL").build(),
                    Trigger.builder()
                            .name("Hunger / Fatigue")
                            .category("INTERNAL").build(),
                    Trigger.builder()
                            .name("Anxiety / Overwhelm")
                            .category("INTERNAL").build()
            ));
            System.out.println("✅ Triggers seeded");
        }

        if (settingRepo.count() == 0) {
            settingRepo.saveAll(List.of(
                    Setting.builder().name("Home").build(),
                    Setting.builder().name("School").build(),
                    Setting.builder().name("Public").build(),
                    Setting.builder().name("Transport").build(),
                    Setting.builder().name("Therapy").build(),
                    Setting.builder().name("Other").build()
            ));
            System.out.println("✅ Settings seeded");
        }

        if (responseRepo.count() == 0) {
            responseRepo.saveAll(List.of(
                    Response.builder()
                            .name("Visual Schedule")
                            .strategyType("PROACTIVE").build(),
                    Response.builder()
                            .name("Transition Warning")
                            .strategyType("PROACTIVE").build(),
                    Response.builder()
                            .name("Positive Reinforcement")
                            .strategyType("PROACTIVE").build(),
                    Response.builder()
                            .name("Communication Support")
                            .strategyType("PROACTIVE").build(),
                    Response.builder()
                            .name("De-escalation")
                            .strategyType("REACTIVE").build(),
                    Response.builder()
                            .name("Redirection")
                            .strategyType("REACTIVE").build(),
                    Response.builder()
                            .name("Providing Choices")
                            .strategyType("REACTIVE").build(),
                    Response.builder()
                            .name("Strategic Ignoring")
                            .strategyType("REACTIVE").build()
            ));
            System.out.println("✅ Responses seeded");
        }
    }
}