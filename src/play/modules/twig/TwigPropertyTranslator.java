package play.modules.twig;

import com.google.code.twig.PropertyTranslator;
import com.google.code.twig.Path;
import com.google.code.twig.Property;
import com.google.code.twig.util.generic.GenericTypeReflector;
import com.google.code.twig.util.SimpleProperty;

import java.util.Set;
import java.util.Collections;
import java.lang.reflect.Type;

/**
 * Base class to specify a custom property type that cannot be natively bound
 * via Twig. Simple types such as String, Integer, etc as well as Collections
 * can be translated without a custom translator.
 *
 * This class is instantiated by the Play-Twig module using reflection and must
 * have a no-argument constructor.
 *
 * @author Dave Jafari (djafaricom@gmail.com)
 * @created: May 22, 2011
 */
public abstract class TwigPropertyTranslator<T> implements PropertyTranslator {

  public abstract Class<T> getTranslatorClass();

  /**
   * Translates the datastore representation of a property into its Java type.
   *
   * @param datastoreProperty property value from datastore
   * @return
   */
  public abstract T decode(Object datastoreProperty);

  /**
   * Encodes a type to it's corresponding datastore representation. This type
   * should be a Java primitive, String, or Collection.
   *
   * @param objectProperty
   * @return
   */
  public abstract Object encode(T objectProperty);

  @Override
  public Object decode(Set<Property> properties, Path path, Type type) {
    if (properties.isEmpty()) return null;
    if (this.getTranslatorClass().isAssignableFrom(GenericTypeReflector.erase(type))) {
      return this.decode(properties.iterator().next().getValue());
    } else {
      return null;
    }
  }

  @Override
  public Set<Property> encode(Object instance, Path path, boolean indexed) {
    if (null == instance) {
      return Collections.emptySet();
    }
    if (this.getTranslatorClass().isAssignableFrom(instance.getClass())) {
      Property property = new SimpleProperty(path, this.encode(this.getTranslatorClass().cast(instance)), indexed);
      return Collections.singleton(property);
    } else {
      return null;
    }
  }
}
