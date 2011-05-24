package play.modules.twig;

import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.StoreCommand;
import com.google.code.twig.FindCommand;
import com.google.code.twig.LoadCommand;
import com.google.appengine.api.datastore.*;

import java.util.Map;
import java.util.Collection;
import java.lang.reflect.Type;

/**
 * @author Dave Jafari (djafaricom@gmail.com)
 * @created: May 22, 2011
 */
public class Datastore {

  static ObjectDatastore datastore;

  public static ObjectDatastore getInstance() {
    return datastore;
  }

  public static StoreCommand store() {
    return datastore.store();
  }

  public static FindCommand find() {
    return datastore.find();
  }

  public static LoadCommand load() {
    return datastore.load();
  }

  public static Key store(Object instance) {
    return datastore.store(instance);
  }

  public static Key store(Object instance, String id) {
    return datastore.store(instance, id);
  }

  public static Key store(Object instance, long id) {
    return datastore.store(instance, id);
  }

  public static <T> Map<T, Key> storeAll(Collection<? extends T> instances) {
    return datastore.storeAll(instances);
  }

  public static void update(Object instance) {
    datastore.update(instance);
  }

  public static void updateAll(Collection<?> instances) {
    datastore.updateAll(instances);
  }

  public static void storeOrUpdate(Object instance) {
    datastore.storeOrUpdate(instance);
  }

  public static <T> T load(Key key) {
    return datastore.<T>load(key);
  }

  public static <T> T load(Class<? extends T> type, Object id) {
    return datastore.load(type, id);
  }

  public static <I, T> Map<I, T> loadAll(Class<? extends T> type, Collection<? extends I> ids) {
    return datastore.loadAll(type, ids);
  }

  public static <T> QueryResultIterator<T> find(Class<? extends T> type) {
    return datastore.find(type);
  }

  public static <T> QueryResultIterator<T> find(Class<? extends T> type, String field, Object value) {
    return datastore.find(type, field, value);
  }

  public static void delete(Object instance) {
    datastore.delete(instance);
  }

  public static void deleteAll(Type type) {
    datastore.deleteAll(type);
  }

  public static void deleteAll(Collection<?> instances) {
    datastore.deleteAll(instances);
  }

  public static int getActivationDepth() {
    return datastore.getActivationDepth();
  }

  public static void setActivationDepth(int depth) {
    datastore.setActivationDepth(depth);
  }

  public static void refresh(Object instance) {
    datastore.refresh(instance);
  }

  public static void refreshAll(Collection<?> instances) {
    datastore.refreshAll(instances);
  }

  public static void associate(Object instance) {
    datastore.associate(instance);
  }

  public static void associateAll(Collection<?> instances) {
    datastore.associateAll(instances);
  }

  public static void associate(Object instance, Key key) {
    datastore.associate(instance, key);
  }

  public static void disassociate(Object instance) {
    datastore.disassociate(instance);
  }

  public static void disassociateAll() {
    datastore.disassociateAll();
  }

  public static Key associatedKey(Object instance) {
    return datastore.associatedKey(instance);
  }

  public static void setConfiguration(DatastoreServiceConfig config) {
    datastore.setConfiguration(config);
  }

  public static Transaction beginTransaction() {
    return datastore.beginTransaction();
  }

  public static Transaction getTransaction() {
    return datastore.getTransaction();
  }

  public static DatastoreService getService() {
    return datastore.getService();
  }

  public static void activate(Object... instances) {
    datastore.activate(instances);
  }

  public static void activateAll(Collection<?> instances) {
    datastore.activateAll(instances);
  }
}
