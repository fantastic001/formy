package org.psw_isa.psw_isa_backend;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.psw_isa.psw_isa_backend.models.FormItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;

@Service
public class WidgetDiscovery {

    
    public List<Widget> getWidgets() {
        ArrayList<Widget> result = new ArrayList<>();

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(Widget.class));
        for (BeanDefinition bd : scanner.findCandidateComponents(BackendApplication.class.getPackageName())) {
            try {
                Class<?> cl = Class.forName(bd.getBeanClassName());
                result.add((Widget) cl.getConstructor().newInstance());
            } catch (ClassNotFoundException e) {
                Logger.getInstance().error("Class not found " + bd.getBeanClassName());
            } catch (InstantiationException e) {
                Logger.getInstance().error("Class " + bd.getBeanClassName() + ": " +
                    "Cannot instantiate object"
                );
            } catch (IllegalAccessException e) {
                Logger.getInstance().error("Class " + bd.getBeanClassName() + ": " +
                    "Constructor does not exist"
                );
            } catch (IllegalArgumentException e) {
                Logger.getInstance().error("Class " + bd.getBeanClassName() + ": " +
                    "Wrong argument values when calling constructor"
                );
            } catch (InvocationTargetException e) {
                Logger.getInstance().error("Class " + bd.getBeanClassName() + ": " +
                    "When constructor was called, error happened"
                );
            } catch (NoSuchMethodException e) {
                Logger.getInstance().error("Class " + bd.getBeanClassName() + ": " +
                    "Empty constructor is not present"
                );
            } catch (SecurityException e) {
                Logger.getInstance().error("Class " + bd.getBeanClassName() + ": " +
                    "Security error"
                );
            }
        }
        return result;
    }

    public Widget findWidgetFromFormItem(FormItem item) {
        for (Widget widget : this.getWidgets()) {
            if (widget.populateFromFormItem(SpringConfiguration.contextProvider(), item) != null) {
                return widget; 
            }
        }
        return null; 
    }


    
}
