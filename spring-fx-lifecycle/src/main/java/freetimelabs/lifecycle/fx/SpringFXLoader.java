package freetimelabs.lifecycle.fx;

import javafx.scene.Scene;
import org.springframework.context.ApplicationContextInitializer;

import java.util.Objects;

public final class SpringFXLoader
{
    /**
     *
     * @param scene
     * @return
     */
    public static ApplicationContextInitializer loadFX(Scene scene)
    {
        return ctx ->
                SceneGraphTraversal.load(scene)
                                   .stream()
                                   .filter(n -> Objects.nonNull(n.getId()))
                                   .forEach(n -> ctx.getBeanFactory()
                                                    .registerSingleton(n.getId(), n));
    }
}