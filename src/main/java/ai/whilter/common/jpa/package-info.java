// @formatter:off
@GenericGenerators({
  @GenericGenerator(
      name = GLOBAL_SEQ_ID_GENERATOR,
      strategy = "enhanced-sequence",
      parameters = {
        @Parameter(name = "sequence_name", value = GLOBAL_SEQ_NAME),
        @Parameter(name = "initial_value", value = GLOBAL_SEQ_INITIAL_VALUE),
        @Parameter(name = "increment_size", value = "1")
      }),
  // Produces a unique 128-bit UUID in the application layer
  @GenericGenerator(
      name = UUID2_GENERATOR,
      strategy = "org.hibernate.id.UUIDGenerator",
      parameters = {
        @Parameter(
            name = "uuid_gen_strategy_class",
            value = "org.hibernate.id.uuid.CustomVersionOneStrategy")
      })
})
// @formatter:on

@ConverterRegistration(converter = YesNoConverter.class)
package ai.whilter.common.jpa;

import org.hibernate.annotations.ConverterRegistration;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;
import org.hibernate.annotations.Parameter;
import org.hibernate.type.YesNoConverter;

import static ai.whilter.common.CommonConstants.*;
