package ai.whilter.common.config;

import ai.whilter.common.mongo.MongoAuditProperties;
import ai.whilter.common.util.DateTimeUtils;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableConfigurationProperties({MongoProperties.class, MongoAuditProperties.class})
@Configuration // (proxyBeanMethods = false)
@RequiredArgsConstructor
public class MongoDBConfig extends AbstractMongoClientConfiguration {

  private final MongoProperties mongoProperties;

  private final MongoAuditProperties mongoAuditProperties;

  @Override
  protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
    final Set<Class<?>> initialEntitySet = super.getInitialEntitySet();

    // Add the common abstract class or interface name of polymorphic entities
    //    initialEntitySet.add(SampleEntity.class);

    return initialEntitySet;
  }

  @Override
  protected void configureClientSettings(final MongoClientSettings.Builder builder) {
    builder
        .applyConnectionString(new ConnectionString(this.mongoProperties.determineUri()))
        .uuidRepresentation(this.mongoProperties.getUuidRepresentation())
        .codecRegistry(
            CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new OffsetDateTimeCodec(), new ZonedDateTimeCodec()),
                MongoClientSettings.getDefaultCodecRegistry()));
  }

  @Override
  protected boolean autoIndexCreation() {
    return this.mongoProperties.isAutoIndexCreation();
  }

  @Override
  protected String getDatabaseName() {
    return this.mongoProperties.getDatabase();
  }

  @Override
  protected Collection<String> getMappingBasePackages() {
    return CollectionUtils.isNotEmpty(this.mongoAuditProperties.getEntityBasePackages())
        ? this.mongoAuditProperties.getEntityBasePackages()
        : Collections.singleton(this.getDefaultPackageName());
  }

  private String getDefaultPackageName() {
    String mainClassName = System.getProperty("sun.java.command");
    String defaultPackageName = mainClassName.substring(0, mainClassName.lastIndexOf('.'));
    return defaultPackageName; // Main class package name
  }

  @Bean
  ValidatingEntityCallback validatingEntityCallback(
      final LocalValidatorFactoryBean factory) {
    return new ValidatingEntityCallback(factory);
  }

  @Bean
  LocalValidatorFactoryBean validatorFactory() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();
    return validator;
  }

  @Bean
  MongoTransactionManager transactionManager(final MongoDatabaseFactory mongoDatabaseFactory) {
    return new MongoTransactionManager(mongoDatabaseFactory);
  }

  static class OffsetDateTimeCodec implements Codec<OffsetDateTime> {

    @Override
    public void encode(
        final BsonWriter writer, final OffsetDateTime value, final EncoderContext encoderContext) {
      if (value != null) {
        writer.writeDateTime(Date.from(value.toInstant()).getTime());
      } else {
        writer.writeNull();
      }
    }

    @Override
    public OffsetDateTime decode(final BsonReader reader, final DecoderContext decoderContext) {
      final BsonType type = reader.getCurrentBsonType();

      if (type == BsonType.NULL) {
        reader.readNull();
        return null;
      } else if (type == BsonType.DATE_TIME) {
        final long milliseconds = reader.readDateTime();
        final Instant instant = Instant.ofEpochMilli(milliseconds);
        return OffsetDateTime.ofInstant(instant, DateTimeUtils.SYSTEM_ZONE_ID);
      } else {
        throw new UnsupportedOperationException(
            "Unsupported BSON type for OffsetDateTime: " + type);
      }
    }

    @Override
    public Class<OffsetDateTime> getEncoderClass() {
      return OffsetDateTime.class;
    }
  }

  static class ZonedDateTimeCodec implements Codec<ZonedDateTime> {

    @Override
    public void encode(
        final BsonWriter writer, final ZonedDateTime value, final EncoderContext encoderContext) {
      if (value != null) {
        writer.writeDateTime(Date.from(value.toInstant()).getTime());
      } else {
        writer.writeNull();
      }
    }

    @Override
    public ZonedDateTime decode(final BsonReader reader, final DecoderContext decoderContext) {
      final BsonType type = reader.getCurrentBsonType();

      if (type == BsonType.NULL) {
        reader.readNull();
        return null;
      } else if (type == BsonType.DATE_TIME) {
        long milliseconds = reader.readDateTime();
        Instant instant = Instant.ofEpochMilli(milliseconds);
        return ZonedDateTime.ofInstant(instant, DateTimeUtils.SYSTEM_ZONE_ID);
      } else {
        throw new UnsupportedOperationException("Unsupported BSON type for ZonedDateTime: " + type);
      }
    }

    @Override
    public Class<ZonedDateTime> getEncoderClass() {
      return ZonedDateTime.class;
    }
  }
}
