package play.modules.twig;

import play.Play;
import play.PlayPlugin;
import play.modules.gae.GAEPlugin;
import play.mvc.Scope;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.annotation.AnnotationObjectDatastore;


/**
 * The {@link play.modules.twig.TwigPlugin} to support Twig on the Google App Engine/J platform.
 *
 * @author David Jafari
 */
public class TwigPlugin extends PlayPlugin {

    protected static Boolean prod;

    /**
     * Reads "objectify.models" for the list of Objectify managed entities.
     */
    protected void setup() {
//        String models = Play.configuration.getProperty("objectify.models");
//        if (models != null) {
//            String[] modelsArray = models.split(",");
//            for (String model : modelsArray) {
//                ObjectifyService.register("models." + model.trim());
//            }
//        }
    }

    /**
     * Checks if the application is running on the production Google App Engine/J platform or the dev server.
     *
     * @return true if production, false otherwise
     */
    protected boolean isProd() {
        if (prod == null) {
            List<PlayPlugin> plugins = Play.plugins;
            for (PlayPlugin plugin : plugins) {
                if (plugin instanceof GAEPlugin) {
                    prod = ((GAEPlugin) plugin).prodGAE;
                    return prod;
                }
            }
            throw new IllegalStateException("Unable to determine GAE environment as GAEPlugin was not detected");
        }
        else {
            return prod;
        }
    }

    /**
     * Setup the environment if production.
     */
    @Override
    public void onApplicationStart() {
        if (isProd()) {
            setup();
        }
    }

    /**
     * Invoked when binding HTTP parameters to Java instances. The actual binding is handled by
     * {@link ObjectifyBinder} or a subclass identified by "objectify.binder" in application.conf.
     *
     * @param name the param name
     * @param clazz the target class which should be ObjectifyModel
     * @param type the type
     * @param params the params map
     * @return the bound instance or null
     */
    @Override
    public Object bind(String name, Class clazz, Type type, Annotation[] annotations, Map<String, String[]> params) {
//        String binderClassName = Play.configuration.getProperty("objectify.binder", ObjectifyBinder.class.getName());
//        try {
//            Class<? extends ObjectifyBinder> binderClass = (Class<? extends ObjectifyBinder>) Play.classloader.loadClass(binderClassName);
//            ObjectifyBinder binder = binderClass.newInstance();
//            Object result = binder.bind(name, clazz, type, params);
//            return result == null ? super.bind(name, clazz, type, params) : result;
//        }
//        catch (Exception e) {
//            throw new UnexpectedException("Unable to bind via binder: " + binderClassName + "," + e.getMessage(), e);
//        }
      return null;
    }

    /**
     * Setup the environment if not production.
     */
    @Override
    public void beforeInvocation() {
        if (!isProd()) {
            setup();
        }
    }

    /**
     * Commits all opened transactions.
     */
    @Override
    public void afterInvocation() {

    }

    /**
     * Exposes the {@link AnnotationObjectDatastore} to templates under two keys, "Datastore" and "ofy".
     *
     * @param actionMethod the action method
     */
    @Override
    public void beforeActionInvocation(Method actionMethod) {
        Scope.RenderArgs renderArgs = Scope.RenderArgs.current();
        final ObjectDatastore datastore = new AnnotationObjectDatastore();
        renderArgs.put("Datastore", datastore);
        renderArgs.put("twig", datastore);
    }

    @Override
    public void afterActionInvocation() {
    }

    /**
     * Rolls back all opened transactions
     *
     * @param e the error thrown
     */
    @Override
    public void onInvocationException(Throwable e) {

    }

    @Override
    public void invocationFinally() {
    }

}
