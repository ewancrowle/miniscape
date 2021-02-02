package net.miniscape.service.scaling;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.EnvVarBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import net.miniscape.game.GameType;
import net.miniscape.server.ServerRequest;
import net.miniscape.server.ServerType;
import net.miniscape.util.environment.Environment;
import net.miniscape.util.environment.EnvironmentProvider;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ScalingServiceImpl implements ScalingService {
    private KubernetesClient client = new DefaultKubernetesClient(new EnvironmentProvider("KUBE_ADDRESS").get());

    @Override
    public String requestServer(ServerRequest request) {
        GameType gameType = request.getGameType();
        Environment environment = request.getEnvironment();
        ServerType serverType = gameType.getServerType();

        Set<EnvVar> envVars = new HashSet<>();
        envVars.add(new EnvVarBuilder().withName("GAME_TYPE").withValue(gameType.name()).build());
        envVars.add(new EnvVarBuilder().withName("SERVER_ENV").withValue(environment.name()).build());

        String appName = serverType.getName();
        String image = getServerImage(serverType);

        PodBuilder podBuilder = new PodBuilder().editOrNewSpec().addNewContainer()
                .withName(appName)
                .withImage(image)
                .addNewPort().withContainerPort(25565).withProtocol("TCP").and()
                .addAllToEnv(envVars)
                .withStdin(true).withTty(true).and().and()
                .editOrNewMetadata().withGenerateName(appName).withDeletionGracePeriodSeconds((long) 30)
                .addToLabels("app", appName).and();

        Pod pod = podBuilder.build();

        client.pods().create(pod);

        return pod.getMetadata().getName();
    }

    private String getServerImage(ServerType serverType) {
        return new EnvironmentProvider(serverType.getName() + "_IMAGE").get();
    }
}