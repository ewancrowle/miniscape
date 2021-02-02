package net.miniscape.util.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ModuleLoader {
    private final List<com.google.inject.Module> injectorModules;
    private List<Class<?>> managers;

    public ModuleLoader() {
        this.injectorModules = new ArrayList<>();
        this.managers = new ArrayList<>();
    }

    public ModuleLoader addInjectorModules(com.google.inject.Module... injectorModules) {
        this.injectorModules.addAll(Arrays.asList(injectorModules));
        return this;
    }

    public ModuleLoader addManagers(Class<?>... modules) {
        for (Class<?> module : modules) {
            this.managers.add(module);
        }
        return this;
    }

    public ModuleLoader load() {
        loadInjector();
        return this;
    }

    public Injector loadInjector() {
        List<com.google.inject.Module> injectorModules = new ArrayList<>();
        injectorModules.addAll(this.injectorModules);

        List<Class<?>> toLoad = new ArrayList<>();

        for (Class<?> module : managers) {
            Manager annotation = module.getAnnotation(Manager.class);

            if (annotation.enabled()) {
                toLoad.add(module);
                System.out.println("Added manager: " + annotation.name());
            }
        }

        Injector injector = Guice.createInjector(injectorModules);
        for (Class<?> module : toLoad) {
            injector.getInstance(module);
        }

        injector.injectMembers(injector);

        return injector;
    }
}
