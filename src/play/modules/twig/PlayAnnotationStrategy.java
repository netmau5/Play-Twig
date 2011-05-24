package play.modules.twig;

import com.google.code.twig.annotation.AnnotationConfiguration;

import java.lang.reflect.Type;
import java.lang.reflect.Field;

import play.Play;

/**
 * Created by IntelliJ IDEA.
 *
 * @author neteller
 * @created: Oct 3, 2010
 */
public class PlayAnnotationStrategy extends AnnotationConfiguration {

  public PlayAnnotationStrategy(boolean indexPropertiesDefault, int defaultVersion) {
    super(indexPropertiesDefault, defaultVersion);
  }

  @Override
  protected Type nameToType(String s) {
    try {
      return Play.classloader.loadClass(s);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public Type typeOf(Field field) {
    return super.typeOf(field);    //To change body of overridden methods use File | Settings | File Templates.
  }
}
