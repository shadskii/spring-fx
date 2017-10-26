/*
 * MIT License
 *
 * Copyright (c) [2017] [Jake]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package freetimelabs.lifecycle.fx;

import javafx.scene.Scene;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

public final class SpringFXLoader
{
    /**
     *
     * @param scene
     * @return
     */
    public static ApplicationContextInitializer<ConfigurableApplicationContext> loadFX(Scene scene)
    {
        return ctx ->
                SceneGraphTraversal.load(scene)
                                   .stream()
                                   .filter(n -> Objects.nonNull(n.getId()))
                                   .forEach(n -> ctx.getBeanFactory()
                                                    .registerSingleton(n.getId(), n));
    }
}
