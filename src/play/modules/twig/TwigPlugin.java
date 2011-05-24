package play.modules.twig;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.modules.gae.GAEPlugin;
import play.mvc.Scope;

import java.lang.reflect.Method;
import java.util.List;

import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.annotation.AnnotationObjectDatastore;


/**
 * The {@link play.modules.twig.TwigPlugin} to support Twig on the Google App Engine/J platform.
 *
 * @author Dave Jafari (djafaricom@gmail.com)
 */
public class TwigPlugin extends PlayPlugin {

  protected static Boolean prod;
  protected static PlayDatastore.Builder builder;

  protected void setup() {

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
    } else {
      return prod;
    }
  }

  /**
   * Setup the environment if production.
   */
  @Override
  public void onApplicationStart() {
    builder = PlayDatastore.builder();
    List<Class> translators = Play.classloader.getAssignableClasses(TwigPropertyTranslator.class);
    for (Class<TwigPropertyTranslator> translator: translators) {
      try {
        builder.addTranslator(translator.newInstance());
      } catch (InstantiationException e) {
        throw new RuntimeException("Translator [" + translator.getClass() + "] could not be instantiated.");
      } catch (IllegalAccessException e) {
        throw new RuntimeException();
      }
    }
    Datastore.datastore = builder.build();
  }

  /**
   * Exposes the datastore under two template variables, datastore and twig.
   *
   * @param actionMethod the action method
   */
  @Override
  public void beforeActionInvocation(Method actionMethod) {
    Scope.RenderArgs renderArgs = Scope.RenderArgs.current();
    final ObjectDatastore datastore = new AnnotationObjectDatastore();
    renderArgs.put("datastore", datastore);
    renderArgs.put("twig", datastore);
  }

  @Override
  public void beforeInvocation() {
    Datastore.datastore = builder.build();
  }

}
