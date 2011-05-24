package play.modules.twig;

import com.google.code.twig.standard.StandardObjectDatastore;
import com.google.code.twig.conversion.TypeConverter;
import com.google.code.twig.conversion.SpecificConverter;
import com.google.code.twig.conversion.CombinedConverter;
import com.google.code.twig.PropertyTranslator;
import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.util.generic.GenericTypeReflector;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.reflect.Field;

/**
 * @author neteller
 * @created: Dec 12, 2010
 */
public class PlayDatastore extends StandardObjectDatastore {

  private PlayDatastore() {
    super(new PlayAnnotationStrategy(true, 0));
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final Set<TypeConverter> typeConverters;
    private final Set<SpecificConverter> specificConverters;
    private final Map<Class, PropertyTranslator> translators;

    private Builder() {
      typeConverters = new HashSet();
      specificConverters = new HashSet();
      translators = new HashMap();
    }

    public Builder addConverter(final TypeConverter typeConverter) {
      typeConverters.add(typeConverter);
      return this;
    }

    public Builder addConverter(final SpecificConverter typeConverter) {
      specificConverters.add(typeConverter);
      return this;
    }

    public Builder addTranslator(final Class type, final PropertyTranslator translator) {
      translators.put(type, translator);
      return this;
    }

    public Builder addTranslator(final TwigPropertyTranslator translator) {
      translators.put(translator.getTranslatorClass(), translator);
      return this;
    }

    public ObjectDatastore build() {
      return new PlayDatastore() {
        @Override
        protected CombinedConverter createTypeConverter() {
          final CombinedConverter converter = super.createTypeConverter();
          for (TypeConverter typeConverter: typeConverters) converter.append(typeConverter);
          for (SpecificConverter<?, ?> specificConverter: specificConverters) {
            converter.append(specificConverter);
          }
          return converter;
        }

        @Override
        protected PropertyTranslator translator(Field field) {
          final PropertyTranslator t = translators.get(GenericTypeReflector.erase(field.getType()));
          return null != t ? t : super.translator(field);
        }
      };
    }

  }

}
